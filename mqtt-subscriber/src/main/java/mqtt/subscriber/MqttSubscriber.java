package mqtt.subscriber;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.*;

public class MqttSubscriber implements MqttCallback {

    public void connectionLost(Throwable throwable) {
        System.out.println("Connection to MQTT broker lost!");
    }

    public void messageArrived(String topic, MqttMessage mqttMessage) {

        try {

            JsonObject jsonMessage = new JsonParser().parse(mqttMessage.toString().replace('\"', '\'')).getAsJsonObject();

            System.out.println("name : " + jsonMessage.get("name"));
            System.out.println("value : " + jsonMessage.get("value"));
            System.out.println("date : " + jsonMessage.get("date"));


            BufferedWriter writer = new BufferedWriter(new FileWriter("mqtt-subscriber/logs/log.txt", true));

            writer.append("============= Message =============\n");
            writer.append("name : " + jsonMessage.get("name") + "\n");
            writer.append("value : " + jsonMessage.get("value") + "\n");
            writer.append("date : " + jsonMessage.get("date") + "\n");
            writer.append("\n\n");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

}