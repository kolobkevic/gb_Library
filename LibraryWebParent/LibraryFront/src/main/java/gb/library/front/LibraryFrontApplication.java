package gb.library.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LibraryFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryFrontApplication.class, args);
    }

}
