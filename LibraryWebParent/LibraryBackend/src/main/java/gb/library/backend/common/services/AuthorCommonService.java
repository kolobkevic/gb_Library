package gb.library.backend.common.services;

import gb.library.backend.common.repositories.AuthorRepository;
import gb.library.common.entities.Author;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorCommonService {

    private final AuthorRepository authorRepository;

    public Author findById(Integer id) {
        return authorRepository.findById(id).orElseThrow(() ->
                new ObjectInDBNotFoundException("Автор не найден в базе", "Author"));
    }

    public Page<Author> findAll(int pageIndex, int pageSize){
        return authorRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));
    }

    public Author update(Author author) {
        Author updatedAuthor = authorRepository.findById(author.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Автор не найден в базе, id: " + author.getId(), "Author"));
        updatedAuthor.setFirstName(author.getFirstName());
        updatedAuthor.setLastName(author.getLastName());
        return authorRepository.save(updatedAuthor);
    }
}
