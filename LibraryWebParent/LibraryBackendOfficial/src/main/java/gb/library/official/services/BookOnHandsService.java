package gb.library.official.services;


import gb.library.backend.repositories.BookHistoryRepository;
import gb.library.common.entities.BookOnHands;
import gb.library.common.entities.User;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class BookOnHandsService {
    private final BookHistoryRepository bookHistoryRepository;

    public Page<BookOnHands> findAll(Integer pageIndex, Integer pages, String searchText, Integer returnStatus) {
        if (pageIndex < 1) pageIndex = 1;
        if (returnStatus == 0)
            return bookHistoryRepository.findAllActiveWithFilter(searchText, PageRequest.of(pageIndex - 1, pages));
        else
            return bookHistoryRepository.findAllByFilter(searchText, PageRequest.of(pageIndex - 1, pages));
    }

    public BookOnHands lendOutBook(BookOnHands bookOnHands) {
        return bookHistoryRepository.save(bookOnHands);
    }

    @Transactional
    public BookOnHands updateOrder(BookOnHands bookOnHandsForUpdate) {
        BookOnHands bookOnHands = findById(bookOnHandsForUpdate.getId());
        bookHistoryRepository.updateWithRecordId(bookOnHands.getId());
        return bookOnHandsForUpdate;
    }

    public BookOnHands findByUser(User user) {
        return bookHistoryRepository.findAllByUser(user);
    }

    public BookOnHands findById(Integer id) {
        return bookHistoryRepository.findById(id)
                .orElseThrow(() -> new ObjectInDBNotFoundException("Выданный заказ не найден", "BookOnHands"));
    }

    public void deleteById(Integer id) {
        bookHistoryRepository.deleteById(id);
    }
}
