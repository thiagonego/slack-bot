package us.sampaio.slack;

import java.net.UnknownHostException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application{

     public static void main(String[] args) throws UnknownHostException {
          
          SpringApplication s = new SpringApplication(Application.class);
          s.run(args);
          
     }
}
