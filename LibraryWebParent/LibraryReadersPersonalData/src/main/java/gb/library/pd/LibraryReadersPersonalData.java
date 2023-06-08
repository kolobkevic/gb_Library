package gb.library.pd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryReadersPersonalData {

    public static void main(String[] args) {
        SpringApplication.run(LibraryReadersPersonalData.class, args);
    }

//  Генерация кода из openapi.yaml:
//  mvn clean compile

//  OpenAPI микросервиса находится по адресу
//	http://<host>:<port>/swagger-ui/index.html

//	Например: http://localhost:48884/swagger-ui/index.html

//	Документация в формате JSON находится по адресу
//	http://<host>:<port>/v3/api-docs

//	Например: http://localhost:48884/v3/api-docs
}
