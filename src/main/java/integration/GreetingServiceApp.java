package integration;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Simon on 7/1/2017.
 */
public class GreetingServiceApp {
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext( "applicationContext.xml" );

        GreeterService greeterService = applicationContext.getBean( "greeterServiceImpl", GreeterService.class );

        greeterService.greet( "Spring Integration!" );
    }
}
