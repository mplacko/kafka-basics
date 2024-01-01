package eu.placko.examples.kafka.basics;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
	private static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers.config";
	private static final String SECURITY_PROTOCOL = "security.protocol";
	private static final String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
	
	public static String bootstrap_servers_config;
	public static String security_protocol;
	public static String sasl_kerberos_service_name;
	
	public static void loadConfiguration() throws IOException {
        InputStream input = null;
        try {
            input = new FileInputStream("client.properties");
            Properties prop = new Properties();
            prop.load(input);
            bootstrap_servers_config = prop.getProperty(BOOTSTRAP_SERVERS_CONFIG);
            security_protocol = prop.getProperty(SECURITY_PROTOCOL);
            sasl_kerberos_service_name = prop.getProperty(SASL_KERBEROS_SERVICE_NAME);
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
            	System.out.println("Configuration: error");
            	e.printStackTrace();
            }
        }
    }
}