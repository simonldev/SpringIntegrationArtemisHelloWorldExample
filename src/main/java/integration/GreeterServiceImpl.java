package integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Configuration
@Service
public class GreeterServiceImpl implements GreeterService {
    @Autowired
    private MessageChannel helloWorldChannel;

    public void greet(String name) {
        helloWorldChannel.send(MessageBuilder.withPayload(name).build());
    }
}
