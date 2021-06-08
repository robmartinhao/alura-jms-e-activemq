package br.com.caelum.jms;

import br.com.caelum.modelo.Pedido;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import java.io.Serializable;
import java.util.Scanner;

public class TesteConsumidorTopicoComercial {

    public static void main(String[] args) throws Exception {

        InitialContext context = new InitialContext();
        //ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
        ActiveMQConnectionFactory factory = (ActiveMQConnectionFactory) context.lookup("ConnectionFactory");
        factory.setTrustAllPackages(true);

        Connection connection = factory.createConnection();
        connection.setClientID("comercial");

        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Topic topico = (Topic) context.lookup("loja");
        MessageConsumer consumer = session.createDurableSubscriber(topico, "assinatura");

        consumer.setMessageListener(message -> {
            ObjectMessage objectMessage = (ObjectMessage) message;
            try {
                Pedido pedido = (Pedido) objectMessage.getObject();
                System.out.println(pedido.getCodigo());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });

        new Scanner(System.in).nextLine();

        session.close();
        connection.close();
        context.close();
    }
}
