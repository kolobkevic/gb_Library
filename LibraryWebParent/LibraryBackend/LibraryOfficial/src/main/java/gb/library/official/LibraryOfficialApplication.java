package gb.library.official;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EntityScan({"gb.library.common"})
@ComponentScan({"gb.library.backend", "gb.library.official"})
@EnableJpaRepositories("gb.library.backend.repositories")
public class LibraryOfficialApplication {

	@Bean
	public ModelMapper getModelMapper(){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		return mapper;
	}

	public static void main(String[] args) {
		SpringApplication.run(LibraryOfficialApplication.class, args);
	}

}
