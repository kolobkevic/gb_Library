package gb.library.official.services;

import gb.lib.common.entities.Author;
import gb.lib.common.exceptions.ObjectInDBNotFoundException;
import gb.library.official.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MyAuthorService implements AuthorService {
    private final AuthorRepository authorRepository;

    public static final int PAGE_SIZE = 10;

    @Override
    public Author findById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new ObjectInDBNotFoundException("Автор не найден в базе"));
    }

    @Override
    public Page<Author> findAll(int pageIndex, int pageSize) {
        return authorRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    @Override
    public Page<Author> findByFirstName(int pageIndex, int pageSize, String firstName) {
        return authorRepository.findAllByFirstName(PageRequest.of(pageIndex, pageSize), firstName);
    }

    @Override
    public Page<Author> findByLastName(int pageIndex, int pageSize, String lastName) {
        return authorRepository.findAllByLastName(PageRequest.of(pageIndex, pageSize), lastName);
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }

    @Override
    public Author update(Author author) {
        Author updatedAuthor = authorRepository.findById(author.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Автор не найден в базе, id: " + author.getId()));
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());
        return updatedAuthor;
    }

}
