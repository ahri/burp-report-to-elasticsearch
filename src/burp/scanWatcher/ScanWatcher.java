package burp.scanWatcher;

import burp.eventStream.Config;

public class ScanWatcher
{
    private static long NO_REQUEST_SENT = -1L;
    private final Platform platform;
    private final Events events;

    private Long lastRequestTime = NO_REQUEST_SENT;

    public ScanWatcher(final Platform platform, final Config config, final CooldownCalculator cooldownCalculator, final Events events, final int pollingIntervalMs)
    {
        this.platform = platform;
        this.events = events;

        platform.startOnNewThread("scanWatcher", new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    synchronized (lastRequestTime)
                    {
                        if (lastRequestTime != NO_REQUEST_SENT)
                        {
                            final long cooldownMs = cooldownCalculator.milliseconds();
                            if (lastRequestTime + cooldownMs < platform.currentTimeMs())
                            {
                                events.onScanEnd(cooldownMs);

                                if (config.autoQuit())
                                {
                                    platform.exit(0);
                                    break;
                                }

                                lastRequestTime = NO_REQUEST_SENT;
                            }
                        }
                    }

                    platform.sleep(pollingIntervalMs);
                }
            }
        });
    }

    public void onScannerActivity()
    {
        synchronized (lastRequestTime)
        {
            if (lastRequestTime == NO_REQUEST_SENT)
            {
                events.onScanStart();
            }

            lastRequestTime = platform.currentTimeMs();
        }
    }

    public interface Platform
    {
        long currentTimeMs();

        void startOnNewThread(String name, Runnable runnable);

        void sleep(int ms);

        void exit(int code);
    }

    public interface CooldownCalculator
    {
        long milliseconds();
    }

    public interface Events
    {
        void onScanStart();

        void onScanEnd(long cooldownMs);
    }
}
