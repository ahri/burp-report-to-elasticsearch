package burp.eventStream;

import sun.plugin.dom.exception.InvalidStateException;

import java.util.UUID;

public class ScanState
{
    private final Config config;
    private String currentScanId;
    private long startTimeMs;

    public ScanState(Config config)
    {
        this.config = config;
    }

    public String currentScanId()
    {
        if (currentScanId == null)
        {
            throw new InvalidStateException("No scan start event has been received");
        }

        return currentScanId;
    }

    public long startTimeMs()
    {
        return startTimeMs;
    }

    public void onScanStart(long startTimeMs)
    {
        this.startTimeMs = startTimeMs;

        if (config.staticScanId() != null)
        {
            currentScanId = config.staticScanId();
        }
        else
        {
            currentScanId = generateNewId();
        }
    }

    private static String generateNewId()
    {
        return UUID.randomUUID().toString();
    }
}
