package eu.placko.examples.kafka.basics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class SimpleConsumer {	
    public static void main(String[] args) throws IOException {
    	System.out.println("SimpleConsumer: started");
    	Configuration.loadConfiguration();
    	
        // Set up client Java properties
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Configuration.bootstrap_servers_config);
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "mp");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put("security.protocol", Configuration.security_protocol);
        props.put("sasl.kerberos.service.name", Configuration.sasl_kerberos_service_name);
        System.setProperty("java.security.auth.login.config","jaas.conf");
        System.out.println("SimpleConsumer: set properties done");
        
        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList("mp_temperature"));
            System.out.println("SimpleConsumer: subscribe done");
            while (true) {
                try {
                	ConsumerRecords<String, String> records = consumer.poll(100);
                	System.out.println("DEBUG INFO: waiting for data...");
                	if (records.count() > 0) {
                		for (ConsumerRecord<String, String> record : records) {
                			System.out.printf("DEBUG INFO: Offset = %d\n", record.offset());
                			System.out.printf("DEBUG INFO: Key    = %s\n", record.key());
                			System.out.printf("DEBUG INFO: Value  = %s\n", record.value());
                			writeToCsv(record);
                		}
                		System.out.println("SimpleConsumer: received data done");
                	}
                } catch (Exception e) {
                	System.out.println("SimpleConsumer: error");
                    e.printStackTrace();
                }
            }
        }
    }
    
    public static void writeToCsv(ConsumerRecord<String, String> record) throws IOException{
    	FileWriter pw = null;
    	try {
	    	pw = new FileWriter("data.csv",true); 
	    	pw.append(record.value());
	    	pw.append("\n");
	    } catch (Exception e) {
	    	System.out.println("writeToCsv: error");
	        e.printStackTrace();
	    } finally {
	    	pw.flush();
	    	pw.close();
	    }
    }
}