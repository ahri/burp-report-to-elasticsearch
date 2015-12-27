package scanWatcher;

import burp.scanWatcher.ScanWatcher;

import common.TestPlatform;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ScanWatcherTest
{
    private class TrackableEvents implements ScanWatcher.Events
    {
        public boolean started = false;
        public boolean ended = false;

        @Override
        public void onScanStart()
        {
            started = true;
        }

        @Override
        public void onScanEnd(long cooldownMs)
        {
            ended = true;
        }
    }

    @Test
    public void givenAScanWatcher_whenNoRequestSent_thenNoEvents() throws Exception
    {
        final TrackableEvents events = new TrackableEvents();

        new ScanWatcher(
                new TestPlatform(0),
                new ScanWatcher.CooldownCalculator()
                {
                    @Override
                    public long milliseconds()
                    {
                        return 0;
                    }
                },
                events,
                1
        );

        Thread.sleep(100);

        assertFalse(events.started);
        assertFalse(events.ended);
    }

    @Test
    public void givenAScanWatcherWithUnhittableCooldown_whenRequestSent_thenStartEventOccurs_andEndEventDoesNot() throws Exception
    {
        final TrackableEvents events = new TrackableEvents();

        new ScanWatcher(
                new TestPlatform(0),
                new ScanWatcher.CooldownCalculator()
                {
                    @Override
                    public long milliseconds()
                    {
                        return 1000;
                    }
                },
                events,
                1
        ).onScannerActivity();

        while (!events.started);

        Thread.sleep(100);

        assertFalse(events.ended);
    }

    @Test
    public void givenAScanWatcherWithHittableCooldown_whenRequestSent_thenStartEventOccurs_andEndEventDoesNot() throws Exception
    {
        final int firstTime = 1000;
        final int cooldown = 500;
        final int secondTime = firstTime + cooldown + 1;

        final TrackableEvents events = new TrackableEvents();

        new ScanWatcher(
                new TestPlatform(firstTime, secondTime),
                new ScanWatcher.CooldownCalculator()
                {
                    @Override
                    public long milliseconds()
                    {
                        return cooldown;
                    }
                },
                events,
                1
        ).onScannerActivity();

        while (!events.ended);
    }
}
