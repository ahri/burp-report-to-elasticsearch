package burp.eventStream.config;

import burp.eventStream.Config;

public class EnvVarConfig implements Config
{
    public final String elasticSearchHost;
    public final int elasticSearchPort;
    public final String elasticSearchPrefix;

    public EnvVarConfig(Output output)
    {
        final String envEventstreamElasticsearchHost = System.getenv("EVENTSTREAM_" + Config.ELASTICSEARCH_HOST_KEY);
        final String envEventstreamElasticsearchPort = System.getenv("EVENTSTREAM_" + Config.ELASTICSEARCH_PORT_KEY);
        final String envEventstreamElasticsearchPrefix = System.getenv("EVENTSTREAM_" + Config.ELASTICSEARCH_PREFIX_KEY);

        elasticSearchHost = envEventstreamElasticsearchHost == null ? Config.DEFAULT_ELASTICSEARCH_HOST : envEventstreamElasticsearchHost;
        elasticSearchPort = envEventstreamElasticsearchPort == null ? Config.DEFAULT_ELASTICSEARCH_PORT : Integer.parseInt(envEventstreamElasticsearchPort);
        elasticSearchPrefix = envEventstreamElasticsearchPrefix == null ? Config.DEFAULT_ELASTICSEARCH_PREFIX : envEventstreamElasticsearchPrefix;

        output.println("TRACE: loading config from environment");
    }

    @Override
    public String elasticSearchHost()
    {
        return elasticSearchHost;
    }

    @Override
    public void elasticSearchHost(String val)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public int elasticSearchPort()
    {
        return elasticSearchPort;
    }

    @Override
    public void elasticSearchPort(int val)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public String elasticSearchPrefix()
    {
        return elasticSearchPrefix;
    }

    @Override
    public void elasticSearchPrefix(String val)
    {
        throw new UnsupportedOperationException();
    }
}
