package burp.eventStream;

import burp.eventStream.config.EnvVarConfig;
import burp.eventStream.config.JavaPrefsConfig;

import java.awt.GraphicsEnvironment;

public interface Config
{
    String TYPE_KEY = "TYPE";
    String STATIC_SCAN_ID_KEY = "STATIC_SCAN_ID";
    String AUTO_QUIT_KEY = "AUTO_QUIT";

    String ELASTICSEARCH_HOST_KEY = "ELASTICSEARCH_HOST";
    String ELASTICSEARCH_PORT_KEY = "ELASTICSEARCH_PORT";
    String ELASTICSEARCH_PREFIX_KEY = "ELASTICSEARCH_PREFIX";

    EventStream.Type DEFAULT_TYPE = EventStream.Type.STDOUT;
    String DEFAULT_STATIC_SCAN_ID = null;
    boolean DEFAULT_AUTO_QUIT = false;

    String DEFAULT_ELASTICSEARCH_HOST = "elasticsearch";
    int DEFAULT_ELASTICSEARCH_PORT = 9200;
    String DEFAULT_ELASTICSEARCH_PREFIX = "/burp";


    EventStream.Type type();
    void type(EventStream.Type val);

    boolean autoQuit();

    String staticScanId();

    String elasticSearchHost();
    void elasticSearchHost(String val);

    int elasticSearchPort();
    void elasticSearchPort(int val);

    String elasticSearchPrefix();
    void elasticSearchPrefix(String val);

    class ConfigFactory
    {
        public static Config Build(Output output)
        {
            Config config = GraphicsEnvironment.isHeadless()
                    ? new EnvVarConfig(output)
                    : new JavaPrefsConfig(output);

            output.println("TRACE: " + TYPE_KEY + ": " + config.type());
            output.println("TRACE: " + STATIC_SCAN_ID_KEY + ": " + config.staticScanId());
            output.println("TRACE: " + AUTO_QUIT_KEY + ": " + config.autoQuit());
            output.println("TRACE: " + ELASTICSEARCH_HOST_KEY + ": " + config.elasticSearchHost());
            output.println("TRACE: " + ELASTICSEARCH_PORT_KEY + ": " + config.elasticSearchPort());
            output.println("TRACE: " + ELASTICSEARCH_PREFIX_KEY + ": " + config.elasticSearchPrefix());

            return config;
        }
    }

    interface Output
    {
        void println(String str);
    }

    interface OnEventStreamTypeChange
    {
        void eventStreamTypeChanged();
    }
}
