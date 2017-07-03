package artemis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.core.client.*;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQMessage;
import org.apache.activemq.artemis.jms.client.ActiveMQTextMessage;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

public class MessageSender {

    private final JmsTemplate jmsTemplate;

    public MessageSender(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(String message) {

        System.out.println("Sending Message to ARTEMIS xxx:" + message);
        jmsTemplate.convertAndSend(message);
        sendMessage();
//        System.out.println("Message to ARTEMIS SENT");

        consumeMessage();
    }

    private void consumeMessage() {
        try {
            Thread.sleep(1000);
            System.out.println("Waiting to consume message");
            Message receivedMessage = jmsTemplate.receive("queue.exampleQueue");
            System.out.println("Message received");
//            ObjectMessage msg = (ObjectMessage) receivedMessage;
            ActiveMQMessage msg = (ActiveMQMessage) receivedMessage;
//            System.out.println("Message Received from ARTEMIS :" + msg.getStringProperty("myprop"));
            System.out.println("Message Received from ARTEMIS :" + msg.getStringProperty("myMessage"));
        }
        catch(Exception e) {
            System.out.println("Error consuming message:" + e);
        }
    }

    public void sendMessage() {
        try
        {
            // Step 3. As we are not using a JNDI environment we instantiate the objects directly

            /**
             * this map with configuration values is not necessary (it configures the default values).
             * If you modify it to run the example in two different hosts, remember to also modify the
             * server's Acceptor at {@link EmbeddedServer}
             */
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("host", "localhost");
            map.put("port", 61616);
            // -------------------------------------------------------

            ServerLocator serverLocator = ActiveMQClient.createServerLocatorWithoutHA(new TransportConfiguration(NettyConnectorFactory.class.getName(), map));
            ClientSessionFactory sf = serverLocator.createSessionFactory();

            // Step 4. Create a core queue
            ClientSession coreSession = sf.createSession(false, false, false);

            final String queueName = "queue.exampleQueue";

//         coreSession.deleteQueue(queueName);

//            coreSession.createQueue(queueName, queueName, true);

//         coreSession.close();

            ClientSession session = null;

            try
            {

                // Step 5. Create the session, and producer
                session = sf.createSession();

                ClientProducer producer = session.createProducer(queueName);

                // Step 6. Create and send a message
                ClientMessage message = session.createMessage(false);

                final String propName = "myMessage";

                message.putStringProperty(propName, "Hello sent at " + new Date());

                System.out.println("Sending the message.");

                producer.send(message);

                // Step 7. Create the message consumer and start the connection
//                ClientConsumer messageConsumer = session.createConsumer(queueName);
//                session.start();

                // Step 8. Receive the message.
//                ClientMessage messageReceived = messageConsumer.receive(1000);
//                System.out.println("Received TextMessage:" + messageReceived.getStringProperty(propName));
            }
            finally
            {
                // Step 9. Be sure to close our resources!
                if (sf != null)
                {
                    sf.close();
                }
//            coreSession.deleteQueue(queueName);
                coreSession.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
