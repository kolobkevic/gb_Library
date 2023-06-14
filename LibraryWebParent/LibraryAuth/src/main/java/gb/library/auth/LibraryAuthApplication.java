package gb.library.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"gb.library.common"})
public class LibraryAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAuthApplication.class, args);
	}

}
