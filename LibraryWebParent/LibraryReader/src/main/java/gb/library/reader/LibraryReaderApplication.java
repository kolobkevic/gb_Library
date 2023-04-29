package gb.library.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"gb.library.common"})
public class LibraryReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryReaderApplication.class, args);
	}

}
