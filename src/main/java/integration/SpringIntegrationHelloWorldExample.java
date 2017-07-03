package integration;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;

public class SpringIntegrationHelloWorldExample {
    private MessageChannel inChannel;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        try {
            SpringIntegrationHelloWorldExample springIntExample = (SpringIntegrationHelloWorldExample) context
                    .getBean("springIntHelloWorldExample");
            springIntExample.post("This is spring integration example.");
            QueueChannel outChannel = (QueueChannel) context.getBean("outputChannel");
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
