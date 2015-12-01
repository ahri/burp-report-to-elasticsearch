package burp.eventStream;

import burp.IScanIssue;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class EventStreamTest
{
    private final Config config = new TestConfig()
    {
        @Override
        public String elasticSearchHost()
        {
            return "elasticsearch";
        }

        @Override
        public int elasticSearchPort()
        {
            return 1234;
        }

        @Override
        public String elasticSearchPrefix()
        {
            return "/foo";
        }
    };

    private final IScanIssue scanIssue = new TestScanIssue()
    {
        @Override
        public URL getUrl()
        {
            try
            {
                return new URL("http", "foo.com", 80, "/bar");
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String getIssueName()
        {
            return "Issue Name";
        }

        @Override
        public int getIssueType()
        {
            return 789;
        }

        @Override
        public String getSeverity()
        {
            return "High";
        }

        @Override
        public String getConfidence()
        {
            return "Firm";
        }

        @Override
        public String getIssueDetail()
        {
            return "Some helpful detail";
        }
    };

    @Test
    public void givenAValidConfig_whenAnIssueIsFound_thenTransmitToElasticSearch() throws Exception
    {
        new EventStream(config, new Http()
        {
            @Override
            public void post(String host, int port, String location, String body)
            {
                assertEquals(config.elasticSearchHost(), host);
                assertEquals(config.elasticSearchPort(), port);
                assertEquals(config.elasticSearchPrefix(), location);

                assertEquals("{\"url\": \"http://foo.com:80/bar\",\"name\": \"Issue Name\",\"type\": 789,\"severity\": \"High\",\"confidence\": \"Firm\",\"detail\": \"Some helpful detail\"}", body);
            }
        }).issueDiscovered(scanIssue);
    }
}