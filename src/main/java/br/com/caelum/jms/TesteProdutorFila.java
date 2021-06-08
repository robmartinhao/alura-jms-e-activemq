package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;

public class TesteProdutorFila {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination fila = (Destination) context.lookup("financeiro");

        MessageProducer producer = session.createProducer(fila);

        Message message = session.createTextMessage("<pedido><id>13</id></pedido>");
        producer.send(message );

//       for (int i = 0; i < 100; i++) {
//           Message message = session.createTextMessage("<pedido><id>" + i + "</id></pedido>");
//           producer.send(message );
//       }

        session.close();
        connection.close();
        context.close();
    }
}
