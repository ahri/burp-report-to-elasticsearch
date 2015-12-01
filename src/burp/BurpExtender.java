package burp;

import burp.eventStream.Config;
import burp.eventStream.EventStream;
import burp.eventStream.Http;

import java.nio.charset.Charset;

public class BurpExtender implements IBurpExtender, IScannerListener
{
    private EventStream eventStream;

    public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks)
    {
        eventStream = new EventStream(
                Config.ConfigFactory.Build(new Config.Output()
                {
                    @Override
                    public void println(String str)
                    {
                        callbacks.printOutput(str);
                    }
                }),
                new Http()
                {
                    @Override
                    public void post(String host, int port, String location, String body)
                    {
                        callbacks.makeHttpRequest(host, port, false, ("POST " + location + " HTTP/1.1\r\nHost: " + host + "\r\nConnection: close\r\nContent-Type: application/json; charset=utf-8\r\nContent-length: " + body.getBytes().length + "\r\n\r\n" + body).getBytes(Charset.forName("UTF-8")));
                    }
                }
        );

        callbacks.registerScannerListener(this);
    }

    @Override
    public void newScanIssue(IScanIssue issue)
    {
        eventStream.issueDiscovered(issue);
    }
}