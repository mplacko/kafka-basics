package eu.placko.examples.kafka.basics;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SimpleProducer {
    public static void main(String[] args) throws IOException {
    	System.out.println("SimpleProducer: started");
    	Configuration.loadConfiguration();
    	
    	// Set up client Java properties
        Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, Configuration.bootstrap_servers_config);
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class.getName());
        props.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        props.put("security.protocol", Configuration.security_protocol);
        props.put("sasl.kerberos.service.name", Configuration.sasl_kerberos_service_name);
        System.setProperty("java.security.auth.login.config","jaas.conf");
        System.out.println("SimpleProducer: set properties done");
        
        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            for (long i = 0; i < 10; i++) {
                String key = Long.toString(i + 1);
                String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                Double temperature = (double) Math.round(Math.random() * 100.0) / 100.0;
                String msg = datetime + ";" + temperature;
                try {
                    ProducerRecord<String, String> data = new ProducerRecord<String, String>("mp_temperature", key, msg);
                    producer.send(data);
                    System.out.println("DEBUG INFO topic: mp_temperature key: " + key + " value: " + msg);
                    System.out.println("SimpleProducer: sent data done");
                    long wait = 5000;
                    Thread.sleep(wait);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("SimpleProducer: error");
                    e.printStackTrace();
                }
            }
        }
    }
}