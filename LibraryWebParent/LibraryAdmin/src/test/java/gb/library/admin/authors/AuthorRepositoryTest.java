package gb.library.admin.authors;

import gb.library.common.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository repository;

    @Test
    void testSave() {
        Author author_1 = new Author();
        author_1.setFirstName("Александр");
        author_1.setLastName("Пушкин");
        Author author_2 = new Author();
        author_2.setFirstName("Лев");
        author_2.setLastName("Толстой");
        Author author_3 = new Author();
        author_3.setFirstName("Чарльз");
        author_3.setLastName("Дикенс");
        repository.save(author_1);
        repository.save(author_2);
        repository.save(author_3);

        List<Author> authorList = repository.findAll();
        assertThat(authorList.size()).isGreaterThan(0);
    }
}