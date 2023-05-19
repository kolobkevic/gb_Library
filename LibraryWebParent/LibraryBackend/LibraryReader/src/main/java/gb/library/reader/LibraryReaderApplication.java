package gb.library.reader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan({"gb.library.common"})
@ComponentScan({"gb.library.backend","gb.library.reader"})
@EnableJpaRepositories("gb.library.backend.repositories")
public class LibraryReaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryReaderApplication.class, args);
	}

}
