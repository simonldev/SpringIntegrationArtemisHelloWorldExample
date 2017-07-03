package artemis;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class SpringIntegrationArtemisExample {
    private MessageChannel inChannel;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        try {
            System.out.println("*******************BEFORE MESSAGE SENT");
            SpringIntegrationArtemisExample springIntExample = (SpringIntegrationArtemisExample) context
                    .getBean("springIntegrationArtemisExample");
            springIntExample.post("This is spring integration ARTEMIS example.");
            System.out.println("*******************AFTER MESSAGE SENT");
            QueueChannel outChannel = (QueueChannel) context.getBean("artemisJmsOutputChannel");
            System.out.println(outChannel.receive());
        } finally {
            context.close();
        }
    }

    public void post(String payload) {
        Message<String> message = MessageBuilder.withPayload(payload).build();
        inChannel.send(message);
    }

    public void setInputChannel(MessageChannel in) {
        this.inChannel = in;
    }
}
