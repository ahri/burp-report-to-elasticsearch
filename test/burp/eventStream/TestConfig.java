package burp.eventStream;

public abstract class TestConfig implements Config
{
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
