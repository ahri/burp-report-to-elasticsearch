package burp.ui;

import burp.IBurpExtenderCallbacks;
import burp.IScanIssue;
import burp.eventStream.Config;
import burp.eventStream.EventStream;
import burp.eventStream.Http;
import burp.eventStream.stream.ElasticSearch;
import burp.eventStream.stream.Stdout;

import java.nio.charset.Charset;

public class ReconfigurableEventStream implements EventStream, Config.OnEventStreamTypeChange
{
    private final Config config;
    private final IBurpExtenderCallbacks callbacks;
    private EventStream eventStream;

    public ReconfigurableEventStream(Config config, IBurpExtenderCallbacks callbacks)
    {
        this.config = config;
        this.callbacks = callbacks;

        eventStream = buildEventStream(config, callbacks);
    }

    private static EventStream buildEventStream(Config config, final IBurpExtenderCallbacks callbacks)
    {
        switch (config.type())
        {
            case ELASTICSEARCH:
                return new ElasticSearch(config, new Http.SocketBased(new Http.SocketBased.Output()
                {
                    @Override
                    public void printError(String str)
                    {
                        callbacks.printError(str);
                    }
                }));

            default:
                return new Stdout();
        }
    }

    @Override
    public void onIssueDiscovered(String scanId, IScanIssue issue)
    {
        eventStream.onIssueDiscovered(scanId, issue);
    }

    @Override
    public void onScanStart(String scanId, long startTimeMs)
    {
        eventStream.onScanStart(scanId, startTimeMs);
    }

    @Override
    public void onScanEnd(String scanId, long startTimeMs, long endTimeMs, long durationS)
    {
        eventStream.onScanEnd(scanId, startTimeMs, endTimeMs, durationS);
    }

    @Override
    public void eventStreamTypeChanged()
    {
        eventStream = buildEventStream(config, callbacks);
        callbacks.printOutput("TRACE: switched to " + config.type() + " EventStream type");
    }

    private static class BurpHttp implements Http
    {
        private final IBurpExtenderCallbacks callbacks;

        public BurpHttp(IBurpExtenderCallbacks callbacks)
        {
            this.callbacks = callbacks;
        }

        @Override
        public void request(String host, int port, String method, String location, String body)
        {
            callbacks.makeHttpRequest(host, port, false, (method + " " + location + " HTTP/1.1\r\nHost: " + host + "\r\nConnection: close\r\nContent-Type: application/json; charset=utf-8\r\nContent-length: " + body.getBytes().length + "\r\n\r\n" + body).getBytes(Charset.forName("UTF-8")));
        }
    }
}
