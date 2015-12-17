package burp.eventStream.config;

import burp.eventStream.Config;
import burp.eventStream.EventStream;

public class EnvVarConfig implements Config
{
    private final EventStream.Type type;
    private final String staticScanId;
    private final boolean autoQuit;

    private final String elasticSearchHost;
    private final int elasticSearchPort;
    private final String elasticSearchPrefix;

    public EnvVarConfig(Output output)
    {
        final String envEventstreamType = getenv(Config.TYPE_KEY);
        final String envStaticScanId = getenv(Config.STATIC_SCAN_ID_KEY);
        final String envAutoQuit = getenv(Config.AUTO_QUIT_KEY);

        final String envEventstreamElasticsearchHost = getenv(Config.ELASTICSEARCH_HOST_KEY);
        final String envEventstreamElasticsearchPort = getenv(Config.ELASTICSEARCH_PORT_KEY);
        final String envEventstreamElasticsearchPrefix = getenv(Config.ELASTICSEARCH_PREFIX_KEY);

        EventStream.Type type = DEFAULT_TYPE;
        try
        {
            type = EventStream.Type.valueOf(envEventstreamType);
        }
        catch (IllegalArgumentException ignore)
        {
        }
        this.type = type;

        staticScanId = envEventstreamElasticsearchHost == null ? Config.DEFAULT_STATIC_SCAN_ID : envStaticScanId;

        boolean autoQuit = DEFAULT_AUTO_QUIT;
        try
        {
            autoQuit = Boolean.parseBoolean(envAutoQuit);
        }
        catch (IllegalArgumentException ignore)
        {
        }
        this.autoQuit = autoQuit;

        elasticSearchHost = envEventstreamElasticsearchHost == null ? Config.DEFAULT_ELASTICSEARCH_HOST : envEventstreamElasticsearchHost;
        elasticSearchPort = envEventstreamElasticsearchPort == null ? Config.DEFAULT_ELASTICSEARCH_PORT : Integer.parseInt(envEventstreamElasticsearchPort);
        elasticSearchPrefix = envEventstreamElasticsearchPrefix == null ? Config.DEFAULT_ELASTICSEARCH_PREFIX : envEventstreamElasticsearchPrefix;

        output.println("TRACE: loading config from environment");
    }

    private static String getenv(String name)
    {
        return System.getenv("BURP_R2E_" + name);
    }

    @Override
    public EventStream.Type type()
    {
        return type;
    }

    @Override
    public void type(EventStream.Type val)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean autoQuit()
    {
        return autoQuit;
    }

    @Override
    public String staticScanId()
    {
        return staticScanId;
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
