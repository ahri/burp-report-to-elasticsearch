package burp.eventStream.stream;

import burp.IScanIssue;
import burp.eventStream.EventStream;
import burp.eventStream.Util;

public class Stdout implements EventStream
{
    @Override
    public void onIssueDiscovered(String scanId, IScanIssue issue)
    {
        System.out.println(
                "ISSUE {" +
                        "\"scanId\":" + Util.escapeJsonString(scanId) +
                        "," +
                        "\"url\":" + Util.escapeJsonString(issue.getUrl().toString()) +
                        "," +
                        "\"name\":" + Util.escapeJsonString(issue.getIssueName()) +
                        "," +
                        "\"type\":" + issue.getIssueType() +
                        "," +
                        "\"severity\":" + Util.escapeJsonString(issue.getSeverity()) +
                        "," +
                        "\"confidence\":" + Util.escapeJsonString(issue.getConfidence()) +
                        "," +
                        "\"detail\":" + Util.escapeJsonString(issue.getIssueDetail()) +
                "}"
        );
    }

    @Override
    public void onScanStart(String scanId, long startTimeMs)
    {
        System.out.println(
                "SCAN " + scanId + " {" +
                    "\"start\":\"" + Util.isoTime(startTimeMs) + "\"" +
                "}"
        );
    }

    @Override
    public void onScanEnd(String scanId, long startTimeMs, long endTimeMs, long durationS)
    {
        System.out.println(
                "SCAN " + scanId + " {" +
                        "\"start\":\"" + Util.isoTime(startTimeMs) + "\"" +
                        "," +
                        "\"end\":\"" + Util.isoTime(endTimeMs) + "\"" +
                        "," +
                        "\"durationSeconds\":" + durationS +
                "}"
        );
    }
}