package gb.library.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class LibraryReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryReaderApplication.class, args);
	}

}
