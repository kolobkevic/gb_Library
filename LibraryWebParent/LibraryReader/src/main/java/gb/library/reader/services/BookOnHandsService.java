package gb.library.reader.services;

import gb.library.common.entities.BookOnHands;
import gb.library.reader.repositories.BookOnHandsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service("readerBookOnHandsService")
@RequiredArgsConstructor
public class BookOnHandsService {
    private final BookOnHandsRepository repository;

    public final static long DAYS_TO_RETURN = 30;

    public Page<BookOnHands> getAllPageable(int userId, int pageIndex, int pageSize){
        if (pageIndex < 1) {
            pageIndex = 1;
        }
        return repository.findAllByUserId(userId, PageRequest.of(pageIndex - 1, pageSize));
    }

}
