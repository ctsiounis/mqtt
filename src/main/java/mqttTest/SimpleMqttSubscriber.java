package mqttTest;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SimpleMqttSubscriber implements MqttCallback{
	
	String topic = "TestFolder/TestSubfolder1/TestVariable_1_0";
	int qos = 2;
	String broker = "tcp://0.0.0.0:1883";
	String clientId = "TestVariable_1_0_Subscriber";
	
	public void run() throws InterruptedException {
		try {
			MqttClient client = new MqttClient(broker, clientId);
			client.setCallback(this);
			
			client.connect();
			
			client.subscribe(topic);
			System.out.println("Waiting...");
			Thread.sleep(150000);
			client.disconnect();
			client.close();
			
		} catch (MqttException me) {
			me.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		SimpleMqttSubscriber subscriber = new SimpleMqttSubscriber();
		subscriber.run();
	}

	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		
	}

	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("Got new value for TestVariable_1_0: "+message);
	}

}
