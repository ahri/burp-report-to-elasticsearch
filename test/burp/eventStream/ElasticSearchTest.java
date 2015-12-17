package burp.eventStream;

import burp.IScanIssue;
import burp.eventStream.stream.ElasticSearch;

import common.TestConfig;
import common.TestScanIssue;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.assertEquals;

public class ElasticSearchTest
{
    private static final Config config = new TestConfig()
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

    private static final IScanIssue scanIssue = new TestScanIssue()
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
    public void givenAnEventStream_whenAnIssueIsFound_thenTransmitToElasticSearch() throws Exception
    {
        new ElasticSearch(config, new Http()
        {
            @Override
            public void request(String host, int port, String method, String location, String body)
            {
                assertEquals(config.elasticSearchHost(), host);
                assertEquals(config.elasticSearchPort(), port);
                assertEquals("POST", method);
                assertEquals(config.elasticSearchPrefix() + "/issue", location);

                assertEquals("{\"scanId\":\"scanid\",\"url\":\"http://foo.com:80/bar\",\"name\":\"Issue Name\",\"type\":789,\"severity\":\"High\",\"confidence\":\"Firm\",\"detail\":\"Some helpful detail\"}", body);
            }
        }).onIssueDiscovered("scanid", scanIssue);
    }

    @Test
    public void givenAnEventStream_whenAnIssueIsFoundContainingJsonControlCharacters_thenTransmitEscapedVersionToElasticSearch() throws Exception
    {
        new ElasticSearch(config, new Http()
        {
            @Override
            public void request(String host, int port, String method, String location, String body)
            {
                assertEquals(config.elasticSearchHost(), host);
                assertEquals(config.elasticSearchPort(), port);
                assertEquals("POST", method);
                assertEquals(config.elasticSearchPrefix() + "/issue", location);

                assertEquals("{\"scanId\":\"scanid\",\"url\":\"http://foo.com:80/bar\",\"name\":\"Issue \\\" Name\",\"type\":789,\"severity\":\"High\",\"confidence\":\"Firm\",\"detail\":\"Some \\n helpful detail\"}", body);
            }
        }).onIssueDiscovered("scanid", new TestScanIssue()
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
                return "Issue \" Name";
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
                return "Some \n helpful detail";
            }
        });
    }

    @Test
    public void givenAnEventStream_whenAScanStarts_thenTransmitToElasticSearch() throws Exception
    {
        final String scanid = "scanid";
        final int startTimeMs = 60000;

        new ElasticSearch(config, new Http()
        {
            @Override
            public void request(String host, int port, String method, String location, String body)
            {
                assertEquals(config.elasticSearchHost(), host);
                assertEquals(config.elasticSearchPort(), port);
                assertEquals("PUT", method);
                assertEquals(config.elasticSearchPrefix() + "/scan/" + scanid, location);

                assertEquals("{\"start\":\"1970-01-01T00:01Z\"}", body);
            }
        }).onScanStart(scanid, startTimeMs);
    }

    @Test
    public void givenAnEventStream_whenAScanEnds_thenTransmitToElasticSearch() throws Exception
    {
        final String scanid = "scanid";
        final int startTimeMs = 60000;
        final int endTimeMs = 120000;
        final int durationS = (endTimeMs - startTimeMs) / 1000;


        new ElasticSearch(config, new Http()
        {
            @Override
            public void request(String host, int port, String method, String location, String body)
            {
                assertEquals(config.elasticSearchHost(), host);
                assertEquals(config.elasticSearchPort(), port);
                assertEquals("PUT", method);
                assertEquals(config.elasticSearchPrefix() + "/scan/" + scanid, location);

                assertEquals("{\"start\":\"1970-01-01T00:01Z\",\"end\":\"1970-01-01T00:02Z\",\"durationSeconds\":60}", body);
            }
        }).onScanEnd(scanid, startTimeMs, endTimeMs, durationS);
    }
}