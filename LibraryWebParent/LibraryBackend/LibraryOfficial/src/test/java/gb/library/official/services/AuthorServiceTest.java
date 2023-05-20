package gb.library.official.services;

import gb.library.common.entities.Author;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    private final List<Author> authors = new ArrayList<>();
    private final List<Author> savedAuthors = new ArrayList<>();

    @BeforeEach
    public void init() {
        Author author = new Author();
        author.setFullName("Пётр Семёнов");
        authors.add(author);

        author = new Author();
        author.setFullName("Иван Фролов");
        authors.add(author);

        author = new Author();
        author.setFullName("Ильдар Запашный");
        authors.add(author);

        author = new Author();
        author.setFullName("Максим Федотов");
        authors.add(author);
    }


    @Test
    public void testSaveDelete() {
        savedAuthors.clear();

        Assertions.assertDoesNotThrow(() ->
                authors.forEach(author -> savedAuthors.add(authorService.save(author)))
        );

        for (int i = 0; i < authors.size(); i++) {
            Author author = authors.get(i);
            Author savedAuthor = savedAuthors.get(i);

            Assertions.assertNotNull(savedAuthor.getId());
            Assertions.assertNotNull(savedAuthor.getCreatedAt());
            Assertions.assertNotNull(savedAuthor.getUpdatedAt());

            Assertions.assertEquals(author.getFirstName(), savedAuthor.getFirstName());
            Assertions.assertEquals(author.getLastName(), savedAuthor.getLastName());
            Assertions.assertEquals(author.getFullName(), savedAuthor.getFullName());
        }

        Assertions.assertDoesNotThrow(() ->
                savedAuthors.forEach(author -> authorService.deleteById(author.getId()))
        );

        for (Author savedAuthor : savedAuthors) {
            Assertions.assertThrows(
                    ObjectInDBNotFoundException.class,
                    () -> authorService.findById(savedAuthor.getId()));
        }
    }


    @Test
    public void testFind() {
        savedAuthors.clear();

        Assertions.assertDoesNotThrow(() ->
                authors.forEach(author -> savedAuthors.add(authorService.save(author)))
        );

        /////////////////////////////////////////////////////////
        // findById
        /////////////////////////////////////////////////////////
        for (Author savedAuthor : savedAuthors) {
            Author findAuthor = authorService.findById(savedAuthor.getId());

            Assertions.assertEquals(savedAuthor.getId(), findAuthor.getId());
            Assertions.assertNotNull(findAuthor.getCreatedAt());
            Assertions.assertNotNull(findAuthor.getUpdatedAt());
            Assertions.assertEquals(savedAuthor.getFirstName(), findAuthor.getFirstName());
            Assertions.assertEquals(savedAuthor.getLastName(), findAuthor.getLastName());
            Assertions.assertEquals(savedAuthor.getFullName(), findAuthor.getFullName());
        }

        /////////////////////////////////////////////////////////
        // findAll
        /////////////////////////////////////////////////////////
        Page<Author> authorPage = authorService.findAll(1);
        Assertions.assertEquals(authorPage.getTotalPages(), 1);
        Assertions.assertEquals(authorPage.getTotalElements(), 4);
        Assertions.assertEquals(authorPage.stream().count(), 4);

        authorPage = authorService.findAll(2);
        Assertions.assertFalse(authorPage.hasContent());

        /////////////////////////////////////////////////////////
        // findByFirstName
        /////////////////////////////////////////////////////////
        for (Author author : savedAuthors) {
            authorPage = authorService.findByFirstName(1, author.getFirstName());
            Optional<Author> authorOptional = authorPage.stream().findFirst();
            Assertions.assertTrue(authorOptional.isPresent());
            Assertions.assertEquals(author.getId(), authorOptional.get().getId());
        }

        /////////////////////////////////////////////////////////
        // findByLastName
        /////////////////////////////////////////////////////////
        for (Author author : savedAuthors) {
            authorPage = authorService.findByLastName(1, author.getLastName());
            Optional<Author> authorOptional = authorPage.stream().findFirst();
            Assertions.assertTrue(authorOptional.isPresent());
            Assertions.assertEquals(author.getId(), authorOptional.get().getId());
        }

        /////////////////////////////////////////////////////////
        // searchAuthors
        /////////////////////////////////////////////////////////
        for (Author author : savedAuthors) {
            List<Author> authorList = authorService.searchAuthors(author.getFullName());
            Assertions.assertFalse(authorList.isEmpty());
            Assertions.assertEquals(author.getId(), authorList.get(0).getId());
        }


        Assertions.assertDoesNotThrow(() ->
                savedAuthors.forEach(author -> authorService.deleteById(author.getId()))
        );
    }

    @Test
    public void testUpdate() {
        Author author1 = authors.get(0);
        Author author2 = authors.get(1);

        Author savedAuthor = authorService.save(author1);
        Assertions.assertEquals(author1.getFullName(), savedAuthor.getFullName());
        Assertions.assertNotEquals(author2.getFullName(), savedAuthor.getFullName());

        savedAuthor.setFullName(author2.getFullName());
        Assertions.assertEquals(author2.getFullName(), savedAuthor.getFullName());
        Author updatedAuthor = authorService.update(savedAuthor);

        Assertions.assertEquals(savedAuthor.getId(), updatedAuthor.getId());
        Assertions.assertNotNull(updatedAuthor.getFullName());
        Assertions.assertEquals(author2.getFullName(), updatedAuthor.getFullName());

        authorService.deleteById(savedAuthor.getId());
    }

}
