package burp.eventStream.config;

import burp.eventStream.Config;
import burp.eventStream.EventStream;

import java.util.prefs.Preferences;

public class JavaPrefsConfig implements Config
{
    public JavaPrefsConfig(Output output)
    {
        output.println("TRACE: loading config from Java Preferences store");
    }

    private final Preferences prefs = Preferences.userRoot().node("BurpEventStreamExtension");

    @Override
    public EventStream.Type type()
    {
        EventStream.Type type = null;

        try
        {
            type = EventStream.Type.valueOf(prefs.get(Config.TYPE_KEY, Config.DEFAULT_TYPE.name()));
        }
        catch (IllegalArgumentException ignore)
        {
        }

        if (type == null)
        {
            prefs.put(Config.TYPE_KEY, Config.DEFAULT_TYPE.name());
            return Config.DEFAULT_TYPE;
        }

        return type;
    }

    @Override
    public void type(EventStream.Type val)
    {
        prefs.put(Config.TYPE_KEY, val.name());
    }

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
