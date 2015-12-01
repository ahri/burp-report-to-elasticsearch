package burp.eventStream.config;

import burp.eventStream.Config;

import java.util.prefs.Preferences;

public class JavaPrefsConfig implements Config
{
    public JavaPrefsConfig(Output output)
    {
        output.println("TRACE: loading config from Java Preferences store");
    }

    private final Preferences prefs = Preferences.userRoot().node("BurpEventStreamExtension");

    @Override
    public String elasticSearchHost()
    {
        return prefs.get(Config.ELASTICSEARCH_HOST_KEY, Config.DEFAULT_ELASTICSEARCH_HOST);
    }

    @Override
    public void elasticSearchHost(String val)
    {
        prefs.put(Config.ELASTICSEARCH_HOST_KEY, val);
    }

    @Override
    public int elasticSearchPort()
    {
        return prefs.getInt(Config.ELASTICSEARCH_PORT_KEY, Config.DEFAULT_ELASTICSEARCH_PORT);
    }

    @Override
    public void elasticSearchPort(int val)
    {
        prefs.putInt(Config.ELASTICSEARCH_PORT_KEY, val);
    }

    @Override
    public String elasticSearchPrefix()
    {
        return prefs.get(Config.ELASTICSEARCH_PREFIX_KEY, Config.DEFAULT_ELASTICSEARCH_PREFIX);
    }

    @Override
    public void elasticSearchPrefix(String val)
    {
        prefs.put(Config.ELASTICSEARCH_PREFIX_KEY, val);
    }
}
