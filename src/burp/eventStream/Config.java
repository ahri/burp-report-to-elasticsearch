package burp.eventStream;

import burp.eventStream.config.EnvVarConfig;
import burp.eventStream.config.JavaPrefsConfig;

import java.awt.GraphicsEnvironment;

public interface Config
{
    String ELASTICSEARCH_HOST_KEY = "ELASTICSEARCH_HOST";
    String ELASTICSEARCH_PORT_KEY = "ELASTICSEARCH_PORT";
    String ELASTICSEARCH_PREFIX_KEY = "ELASTICSEARCH_PREFIX";

    String DEFAULT_ELASTICSEARCH_HOST = "elasticsearch";
    int DEFAULT_ELASTICSEARCH_PORT = 9200;
    String DEFAULT_ELASTICSEARCH_PREFIX = "/burp/issue";

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
            return GraphicsEnvironment.isHeadless()
                    ? new EnvVarConfig(output)
                    : new JavaPrefsConfig(output);
        }
    }

    interface Output
    {
        void println(String str);
    }
}
