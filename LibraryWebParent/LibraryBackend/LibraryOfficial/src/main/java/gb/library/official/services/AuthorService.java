package gb.library.official.services;

import gb.library.common.entities.Author;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.official.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public static final int PAGE_SIZE = 10;

    public Author findById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new ObjectInDBNotFoundException("Автор не найден в базе", "Author"));
    }

    public Page<Author> findAll(int pageIndex, int pageSize) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return authorRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));
    }

    public Page<Author> findByFirstName(int pageIndex, int pageSize, String firstName) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return authorRepository.findAllByFirstName(PageRequest.of(pageIndex - 1, pageSize), firstName);
    }

    public Page<Author> findByLastName(int pageIndex, int pageSize, String lastName) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return authorRepository.findAllByLastName(PageRequest.of(pageIndex - 1, pageSize), lastName);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public Author update(Author author) {
        Author updatedAuthor = authorRepository.findById(author.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Автор не найден в базе, id: " + author.getId(), "Author"));
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());
        return authorRepository.save(updatedAuthor);
    }

    public List<Author> searchAuthors(String searchString) {
        searchString = searchString.toUpperCase();
        List<Author> result = new ArrayList<>();
        String[] splitString = searchString.split(" ");
        if (splitString.length == 2) {
            result.addAll(authorRepository.findByFirstNameAndLastNameLike(splitString[0], splitString[1]));
            result.addAll(authorRepository.findByFirstNameAndLastNameLike(splitString[1], splitString[0]));
        } else {
            result.addAll(authorRepository.findByLastNameLike(splitString[0]));
            result.addAll(authorRepository.findByFirstNameLike(splitString[0]));
        }
        return result;
    }
}
