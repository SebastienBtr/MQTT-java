package mqtt.launcher;


import mqtt.subscriber.MqttSubscriber;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Launcher {


    public static void main(String[] args) {

        try {

            MqttClient mqttClient = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());

            mqttClient.setCallback(new MqttSubscriber());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);

            mqttClient.connect(options);

            mqttClient.subscribe("data", 0);

        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
