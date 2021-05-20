package br.com.caelum.jms;

import javax.jms.*;
import javax.naming.InitialContext;

public class TesteProdutorTopico {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");

        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination topico = (Destination) context.lookup("loja");

        MessageProducer producer = session.createProducer(topico);

        Message message = session.createTextMessage("<pedido><id>1011</id></pedido>");
        producer.send(message);

        session.close();
        connection.close();
        context.close();
    }
}
