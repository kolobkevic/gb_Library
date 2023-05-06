package gb.library.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan({"gb.library.common"})
@ComponentScan({"gb.library.official"})
@ComponentScan(basePackages = "gb.library.reader")
public class LibraryReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryReaderApplication.class, args);
	}

}
