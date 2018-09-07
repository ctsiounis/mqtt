package mqttTest;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MyMqttClient implements MqttCallback{
	String topic = "Change";
	String content = "{Location: 'US', Speed: 30, Running: true}";
	int qos = 2;
	String broker = "tcp://0.0.0.0:1883";
	String clientId = "paho-java-client";
	String clientId2 = "subscriber";
	

	public void run() {
		try {
			MqttClient publisher = new MqttClient(broker, clientId);
			MqttClient subscriber = new MqttClient(broker, clientId2);
			
			subscriber.setCallback(this);
			
			publisher.connect();
			subscriber.connect();
			
			subscriber.subscribe(topic);
			
			MqttMessage message = new MqttMessage(content.getBytes());
			message.setQos(qos);
			publisher.publish(topic, message);
			publisher.disconnect();
			publisher.close();
			
			subscriber.disconnect();
			subscriber.close();
			
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}

	public static void main(String[] args) {
		MyMqttClient client = new MyMqttClient();
		client.run();

		
	}

	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Got message from "+topic+": "+message);
	}

}
