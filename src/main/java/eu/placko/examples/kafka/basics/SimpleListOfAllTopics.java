package eu.placko.examples.kafka.basics;

import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;

public class SimpleListOfAllTopics {
	public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {
		System.out.println("SimpleListOfAllTopics: started");
		Configuration.loadConfiguration();
		
		// Set up client Java properties
		Properties props = new Properties();
		props.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, Configuration.bootstrap_servers_config);
		props.put("security.protocol", Configuration.security_protocol);
		props.put("sasl.kerberos.service.name", Configuration.sasl_kerberos_service_name);
        System.setProperty("java.security.auth.login.config","jaas.conf");
        System.out.println("SimpleListOfAllTopics: set properties done");
        
        try (AdminClient adminClient = AdminClient.create(props)) {
            ListTopicsResult topics = adminClient.listTopics();
            Set<String> topicNames = topics.names().get();
            System.out.println("Topics in the Kafka cluster:");
            topicNames.forEach(System.out::println);
        }
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("SimpleListOfAllTopics: error");
			e.printStackTrace();
		}
	}
}