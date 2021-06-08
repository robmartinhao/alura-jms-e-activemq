package br.com.caelum.jms.logs;

import javax.jms.*;
import javax.naming.InitialContext;

public class TesteProdutorFilaLog {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("LOG");

        MessageProducer producer = session.createProducer(fila);

        Message message = session.createTextMessage("WARN | Apache ActiveMQ -------");
        producer.send(message, DeliveryMode.NON_PERSISTENT, 7, 80000);

//       for (int i = 0; i < 100; i++) {
//           Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
//           producer.send(message );
//       }

        session.close();
        connection.close();
        context.close();
    }
}
