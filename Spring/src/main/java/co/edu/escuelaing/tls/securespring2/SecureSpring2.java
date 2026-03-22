

package co.edu.escuelaing.tls.securespring2;

import java.util.Collections;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecureSpring2 {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SecureSpring2.class);
        app.setDefaultProperties(Collections.singletonMap("server.port", "5000"));
 app.run(args);
    }
}
