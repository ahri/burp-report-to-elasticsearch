package common;

import burp.eventStream.Config;
import burp.eventStream.EventStream;

public abstract class TestConfig implements Config
{
    @Override
    public EventStream.Type type()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void type(EventStream.Type val)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean autoQuit()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String staticScanId()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String elasticSearchHost()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void elasticSearchHost(String val)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int elasticSearchPort()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void elasticSearchPort(int val)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String elasticSearchPrefix()
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void elasticSearchPrefix(String val)
    {
        throw new UnsupportedOperationException();
    }
}
