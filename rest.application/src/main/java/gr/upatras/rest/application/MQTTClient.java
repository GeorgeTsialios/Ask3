package gr.upatras.rest.application;

import java.util.UUID;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MQTTClient implements MqttCallback {

	MqttClient myClient;
	MqttConnectOptions connOpt;
	
// IMqttClient publisher = new MqttClient("tcp://iot.eclipse.org:1883",publisherId);
	
	static final String M2MIO_THING = UUID.randomUUID().toString();
	static final String BROKER_URL = "tcp://test.mosquitto.org:1883";
// static final String M2MIO_DOMAIN = "<Insert m2m.io domain here>";
// static final String M2MIO_STUFF = "things";
// static final String M2MIO_USERNAME = "<m2m.io username>";
// static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of password)>";

// the following two flags control whether this example is a publisher, a
// subscriber or both
	
	private static final Logger log =LoggerFactory.getLogger(MQTTClient.class);
	public static final String TOPIC = "grupatras/Προηγμένες_Τεχνικές_Προγραμματισμού/Ask3";
 
/**
*
* connectionLost This callback is invoked upon losing the MQTT connection.
*
*/
	public void connectionLost(Throwable t) {
		log.info("Connection lost");
		// code to reconnect to the broker would go here if desired
	}
	
/**
*
* deliveryComplete This callback is invoked when a message published by this
* client is successfully received by the broker.
*
*/
	 public void deliveryComplete(IMqttDeliveryToken token) {
	
	 }
	 
 /**
 *
 * messageArrived This callback is invoked when a message is received on a
 * subscribed topic.
 *
 */	 
	 public void messageArrived(String topic, MqttMessage message) throws Exception {
		 log.info("\n");
		 log.info("-------------------------------------------------");
		 log.info("| Topic:" + topic);
		 log.info("| Message: " + new String(message.getPayload()));
		 log.info("-------------------------------------------------");
		 log.info("\n");
	 }
	 
 /**
 *
 * runClient The main functionality of this simple example. Create a MQTT
 * client, connect to broker, pub/sub, disconnect.
 *
 */
	 public void runClient(UserText text) {
		 // Setup MQTT client
		 String clientID = M2MIO_THING;
		 connOpt = new MqttConnectOptions();
		 
		 connOpt.setCleanSession(true);
		 connOpt.setKeepAliveInterval(30);
// 		 connOpt.setUserName(M2MIO_USERNAME);
//		 connOpt.setPassword(M2MIO_PASSWORD_MD5.toCharArray());
		
		 // Connect to broker
		 try {
			 myClient = new MqttClient(BROKER_URL, clientID);
			 myClient.setCallback(this);
			 myClient.connect(connOpt);
		 }
		 catch (MqttException e) {
			 e.printStackTrace();
			 System.exit(-1);
		 }
		
		 log.info("Connected to" + BROKER_URL);
		
		 String myTopic = TOPIC;
		 MqttTopic topic = myClient.getTopic(myTopic);
		 
		 int pubQoS = 0;
		 MqttMessage newMessage = new MqttMessage(text.message.getBytes());
		 newMessage.setQos(pubQoS);
		 newMessage.setRetained(false);
		 
		// Publish the message
		 log.info("Publishing to topic \"" + topic + "\" qos " + pubQoS + "\"text " + text.message);
		
		 MqttDeliveryToken token = null;
		 try {
			 // publish message to broker
			 token = topic.publish(newMessage);
			 // Wait until the message has been delivered to the broker
			 token.waitForCompletion();
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
		
		 // Disconnect
		 try {
			 myClient.disconnect();
		 }
		 catch (Exception e) {
			 e.printStackTrace();
		 }
	 }
}