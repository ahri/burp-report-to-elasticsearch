package burp.ui;

import java.util.UUID;

public class ScanState
{
    private String currentScanId;
    private long startTimeMs;

    public ScanState()
    {
        currentScanId = generateNewId();
    }

    public String currentScanId()
    {
        return currentScanId;
    }

    public long startTimeMs()
    {
        return startTimeMs;
    }

    public void onScanStart(long startTimeMs)
    {
        this.startTimeMs = startTimeMs;
        currentScanId = generateNewId();
    }

    private static String generateNewId()
    {
        return UUID.randomUUID().toString();
    }
}
