package gb.library.reader.services;

import gb.library.common.entities.BookOnHands;
import gb.library.backend.repositories.BookHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service("readerBookHistoryService")
@RequiredArgsConstructor
public class BookHistoryService {
    private final BookHistoryRepository repository;

    public final static long DAYS_TO_RETURN = 30;

    public Page<BookOnHands> getBooksOnHands(int userId, int pageIndex, int pageSize, boolean unReturned) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        if (unReturned) {
            return repository.findUnreturnedByUserId(userId, PageRequest.of(pageIndex - 1, pageSize));
        } else {
            return repository.findBooksByUserId(userId, PageRequest.of(pageIndex - 1, pageSize));
        }
    }

}
