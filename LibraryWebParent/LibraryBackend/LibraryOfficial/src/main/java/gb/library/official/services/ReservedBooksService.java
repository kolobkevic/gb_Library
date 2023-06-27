package gb.library.official.services;


import gb.library.backend.repositories.ReservedBooksRepository;
import gb.library.common.entities.ReservedBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@RequiredArgsConstructor
public class ReservedBooksService {
    private final ReservedBooksRepository reservedBooksRepository;

    public Page<ReservedBook> findAll(Integer pageIndex, Integer pages, String searchText) {
        if (pageIndex < 1) pageIndex = 1;
        return reservedBooksRepository.findAllLikeSearchText(searchText, PageRequest.of(pageIndex - 1, pages));
    }

    public ReservedBook createNewOrder(ReservedBook reservedBook) {
        return reservedBooksRepository.save(reservedBook);
    }

    public ReservedBook updateOrder(ReservedBook updateReservedBook) {
        ReservedBook reservedBookForUpdate = reservedBooksRepository.findById(updateReservedBook.getId()).
                orElseThrow(() -> new ObjectInDBNotFoundException("Забронированная книга не найдена!", "ReservedBook"));
        reservedBookForUpdate.setWorldBook(updateReservedBook.getWorldBook());
        reservedBookForUpdate.setLibraryBook(updateReservedBook.getLibraryBook());
        return reservedBooksRepository.save(reservedBookForUpdate);
    }

    public ReservedBook findById(@PathVariable Integer id) {
        return reservedBooksRepository.findById(id).orElseThrow(() -> new ObjectInDBNotFoundException("Забронированная книга не найдена!", "ReservedBooks"));
    }

    public void deleteById(Integer id) {
        reservedBooksRepository.deleteById(id);
    }
}
