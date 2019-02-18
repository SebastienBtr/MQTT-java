package mqtt;

import com.google.gson.JsonObject;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebServlet(name = "PublishMessage", urlPatterns = {"/publish"})
public class PublisherAlert extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String value = request.getParameter("value");

        JsonObject jsonMessage = new JsonObject();

        jsonMessage.addProperty("name", "Data_example");
        jsonMessage.addProperty("value", value);
        jsonMessage.addProperty("date", new Date().getTime());


        try {

            MqttClient client = new MqttClient("tcp://localhost:1883", MqttClient.generateClientId());
            client.connect();

            MqttMessage message = new MqttMessage();
            message.setPayload(jsonMessage.toString().getBytes());
            client.publish("data", message);

            client.disconnect();
            client.close();

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("Message sent : " + jsonMessage.toString());


        } catch (MqttException me) {

            System.out.println("reason " + me.getReasonCode());
            System.out.println("msg " + me.getMessage());
            System.out.println("loc " + me.getLocalizedMessage());
            System.out.println("cause " + me.getCause());
            System.out.println("excep " + me);
            me.printStackTrace();
        }
    }
}
