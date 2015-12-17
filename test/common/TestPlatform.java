package common;

import burp.scanWatcher.ScanWatcher;

import java.util.concurrent.Executors;

import static org.junit.Assert.assertTrue;

public class TestPlatform implements ScanWatcher.Platform
{
    private int calls = 0;
    private final long[] currentTimeMs;

    public TestPlatform(long... currentTimeMs)
    {
        assertTrue(currentTimeMs.length > 0);

        this.currentTimeMs = currentTimeMs;
    }

    @Override
    public long currentTimeMs()
    {
        final long ms = currentTimeMs[calls];

        if (calls < (currentTimeMs.length - 1))
        {
            calls++;
        }

        return ms;
    }

    @Override
    public void startOnNewThread(String name, Runnable runnable)
    {
        final Thread thread = Executors.defaultThreadFactory().newThread(runnable);
        thread.setName(name);
        thread.start();
    }

    @Override
    public void sleep(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void exit(int code)
    {
    }
}
