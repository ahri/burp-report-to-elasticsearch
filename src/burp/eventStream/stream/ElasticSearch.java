package burp.eventStream.stream;

import burp.IScanIssue;
import burp.eventStream.Config;
import burp.eventStream.EventStream;
import burp.eventStream.Http;
import burp.eventStream.Util;

public class ElasticSearch implements EventStream
{
    private final Config config;
    private final Http http;

    public ElasticSearch(Config config, Http http)
    {
        this.config = config;
        this.http = http;
    }

    @Override
    public void onIssueDiscovered(String scanId, IScanIssue issue)
    {
        http.request(config.elasticSearchHost(), config.elasticSearchPort(), "POST", config.elasticSearchPrefix() + "/issue",
                "{" +
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
        http.request(config.elasticSearchHost(), config.elasticSearchPort(), "PUT", config.elasticSearchPrefix() + "/scan/" + scanId,
                "{" +
                    "\"start\":\"" + Util.isoTime(startTimeMs) + "\"" +
                "}"
        );
    }

    @Override
    public void onScanEnd(String scanId, long startTimeMs, long endTimeMs, long durationS)
    {
        http.request(config.elasticSearchHost(), config.elasticSearchPort(), "PUT", config.elasticSearchPrefix() + "/scan/" + scanId,
                "{" +
                        "\"start\":\"" + Util.isoTime(startTimeMs) + "\"" +
                        "," +
                        "\"end\":\"" + Util.isoTime(endTimeMs) + "\"" +
                        "," +
                        "\"durationSeconds\":" + durationS +
                "}"
        );
    }
}