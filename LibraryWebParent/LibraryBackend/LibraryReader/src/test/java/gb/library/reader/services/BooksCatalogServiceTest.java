package gb.library.reader.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@SpringBootTest
@Rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BooksCatalogServiceTest {

    @Autowired
    private BooksCatalogService service;

    @Test
    void testFindBooksAllAgeRating() {
        Assertions.assertNotNull(service.findAll(1, 10, "",
                List.of("Роман", "Комедия") , List.of("R", "PG", "PG13", "G")));
        Assertions.assertNotNull(service.findAll(1, 10, "",
                List.of("1243142", "3213") , List.of("R", "PG", "PG13", "G")));
        Assertions.assertEquals(3, service.findAll(1, 10, "",
                List.of("Роман") , List.of("R", "PG", "PG13", "G")).getTotalElements());
    }

    @Test
    void testFindAllWithoutAgeRating() {
        Assertions.assertNotNull(service.findAll(1, 10, "",
                List.of("Роман", "Комедия") , null ));
    }

    @Test
    void testFindAllWithoutAgeAndGenre() {
        Assertions.assertNotNull(service.findAll(1, 10, "",
                null , null ));
        Assertions.assertEquals(5, service.findAll(1, 10, "",
                null , null ).getTotalElements());
    }

}
