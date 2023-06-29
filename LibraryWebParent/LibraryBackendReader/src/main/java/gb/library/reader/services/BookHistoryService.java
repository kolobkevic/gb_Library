package gb.library.reader.services;

import gb.library.common.entities.BookOnHands;
import gb.library.backend.repositories.BookHistoryRepository;
import gb.library.common.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service("readerBookHistoryService")
@RequiredArgsConstructor
public class BookHistoryService {
    private final BookHistoryRepository repository;
    private final UserService userService;

    public final static long DAYS_TO_RETURN = 30;

    @Transactional
    public Page<BookOnHands> getBooksOnHands(String userLogin, int pageIndex, int pageSize, boolean unReturned) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        User user = userService.findByEmail(userLogin);
        if (unReturned) {
            return repository.findUnreturnedByUserId(user.getId(), PageRequest.of(pageIndex - 1, pageSize));
        } else {
            return repository.findBooksByUserId(user.getId(), PageRequest.of(pageIndex - 1, pageSize));
        }
    }

}
