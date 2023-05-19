package gb.library.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication()
@EntityScan({"gb.library.common"})
public class LibraryAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryAdminApplication.class, args);
	}

}
