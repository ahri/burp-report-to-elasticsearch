package scanWatcher;

import burp.scanWatcher.ScanWatcher;

import common.TestConfig;
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
                new TestConfig()
                {},
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
                new TestConfig()
                {},
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
                new TestConfig()
                {
                    @Override
                    public boolean autoQuit()
                    {
                        return false;
                    }
                },
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

    class ExitTrackingTestPlatform extends TestPlatform
    {
        public boolean exited = false;

        public ExitTrackingTestPlatform(long... currentTimeMs)
        {
            super(currentTimeMs);
        }

        @Override
        public void exit(int code)
        {
            exited = true;
        }
    }

    @Test
    public void givenAConfigurationWithAutoQuitSet_whenCooldownHit_thenPlatformExit() throws Exception
    {
        final int firstTime = 1000;
        final int cooldown = 500;
        final int secondTime = firstTime + cooldown + 1;

        final TrackableEvents events = new TrackableEvents();

        final ExitTrackingTestPlatform platform = new ExitTrackingTestPlatform(firstTime, secondTime);
        new ScanWatcher(
                platform,
                new TestConfig()
                {
                    @Override
                    public boolean autoQuit()
                    {
                        return true;
                    }
                },
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

        while (!platform.exited);
    }
}
