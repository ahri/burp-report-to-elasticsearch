package burp.eventStream;

import burp.IScanIssue;

public interface EventStream
{
    enum Type
    {
        STDOUT,
        ELASTICSEARCH,
    }

    void onIssueDiscovered(String scanId, IScanIssue issue);

    void onScanStart(String scanId, long startTimeMs);

    void onScanEnd(String scanId, long startTimeMs, long endTimeMs, long durationS);
}
