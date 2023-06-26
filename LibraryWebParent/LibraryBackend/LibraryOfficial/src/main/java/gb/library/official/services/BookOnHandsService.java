package gb.library.official.services;


import gb.library.backend.repositories.BookHistoryRepository;
import gb.library.common.entities.BookOnHands;
import gb.library.common.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookOnHandsService {
    private final BookHistoryRepository bookHistoryRepository;

    public List<BookOnHands> findAll() {
        return Collections.unmodifiableList(bookHistoryRepository.findAll());
    }

    public BookOnHands lendOutBook(BookOnHands bookOnHands) {
        return bookHistoryRepository.save(bookOnHands);
    }

    public BookOnHands findByUser(User user) {
        return bookHistoryRepository.findAllByUser(user);
    }

    public void deleteById(Integer id) {
        bookHistoryRepository.deleteById(id);
    }
}
