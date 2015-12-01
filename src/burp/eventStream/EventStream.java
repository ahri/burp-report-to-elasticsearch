package burp.eventStream;

import burp.IScanIssue;

public class EventStream
{
    private final Config config;
    private final Http http;

    public EventStream(Config config, Http http)
    {
        this.config = config;
        this.http = http;
    }

    public void issueDiscovered(IScanIssue issue)
    {
        http.post(config.elasticSearchHost(), config.elasticSearchPort(), config.elasticSearchPrefix(),
                "{" +
                        "\"url\": \"" + issue.getUrl() + "\"" +
                        "," +
                        "\"name\": \"" + issue.getIssueName() + "\"" +
                        "," +
                        "\"type\": " + issue.getIssueType() +
                        "," +
                        "\"severity\": \"" + issue.getSeverity() + "\"" +
                        "," +
                        "\"confidence\": \"" + issue.getConfidence() + "\"" +
                        "," +
                        "\"detail\": \"" + issue.getIssueDetail() + "\"" +
                "}"
        );
    }
}
