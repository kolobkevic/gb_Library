package gb.library.admin.genres;

import gb.library.common.entities.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class GenreRepositoryTest {

    @Autowired
    private GenreRepository repository;

    @Test
    public void testCreation(){
        Genre genre = new Genre();
        genre.setName("Фольклор");
        genre.setDescription("Народные произведения: эпос, легенды, мифы и сказки");
        Genre savedGenre = repository.save(genre);
        assertThat(savedGenre.getId()).isGreaterThan(0);
    }

}