package integration;
/**
 * Created by Simon on 7/1/2017.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;


@Configuration
@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public void hello(String name) {
        System.out.println("Hello, " + name);
    }
}