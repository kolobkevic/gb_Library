package gb.library.reader.services;


import gb.library.common.entities.WorldBook;
import gb.library.reader.repositories.BooksCatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BooksCatalogService {
    private final BooksCatalogRepository booksCatalogRepository;

    public Page<WorldBook> findAll(Integer pageIndex, Integer pageSize) {
        return booksCatalogRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }
}