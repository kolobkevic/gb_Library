package gb.library.official.services;

import gb.library.backend.services.AuthorCommonService;
import gb.library.common.entities.Author;
import gb.library.backend.repositories.AuthorRepository;
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
    private final AuthorCommonService authorCommonService;

    public static final int PAGE_SIZE = 10;

    public Author findById(Integer id) {
        return authorCommonService.findById(id);
    }

    public Page<Author> findAll(int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        //return authorRepository.findAll(PageRequest.of(pageIndex - 1, pageSize));
        return authorCommonService.findAll(pageIndex, PAGE_SIZE);
    }

    public Page<Author> findByFirstName(int pageIndex, String firstName) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return authorRepository.findAllByFirstName(PageRequest.of(pageIndex - 1, PAGE_SIZE), firstName);
    }

    public Page<Author> findByLastName(int pageIndex, String lastName) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return authorRepository.findAllByLastName(PageRequest.of(pageIndex - 1, PAGE_SIZE), lastName);
    }

    public Author save(Author author) {
        return authorRepository.save(author);
    }

    public void deleteById(Integer id) {
        authorRepository.deleteById(id);
    }

    @Transactional
    public Author update(Author author) {
        return authorCommonService.update(author);
    }

    public List<Author> searchAuthors(String searchString) {
        if (searchString.isBlank()){
            return authorRepository.findAll();
        }
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
        //todo: удаление дубликатов id
        return result;
    }
}
