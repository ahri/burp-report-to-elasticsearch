package common;

import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IScanIssue;

import java.net.URL;

public abstract class TestScanIssue implements IScanIssue
{
    @Override
    public URL getUrl()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIssueName()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getIssueType()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSeverity()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getConfidence()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIssueBackground()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRemediationBackground()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getIssueDetail()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getRemediationDetail()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public IHttpRequestResponse[] getHttpMessages()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public IHttpService getHttpService()
    {
        throw new UnsupportedOperationException();
    }
}
