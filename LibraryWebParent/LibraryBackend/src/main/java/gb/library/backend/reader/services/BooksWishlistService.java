package gb.library.backend.reader.services;


import gb.library.backend.common.repositories.BooksWishlistRepository;
import gb.library.common.entities.BooksWishlist;
import gb.library.common.entities.User;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BooksWishlistService {
    private final BooksWishlistRepository booksWishlistRepository;
    private final UserService userService;
    private final BooksCatalogService booksCatalogService;

    @Transactional
    public Page<BooksWishlist> findAll(String userLogin, Integer pageIndex, Integer pageSize) {
        User user = userService.findByEmail(userLogin);
        return booksWishlistRepository.findAllByUserId(user.getId(), PageRequest.of(pageIndex, pageSize));
    }
    public BooksWishlist findById(Integer id) {
        return booksWishlistRepository.findById(id).orElseThrow(() -> new ObjectInDBNotFoundException(String.format("Список с id(%d) не найден", id), "BooksWishlist"));
    }

    @Transactional
    public void addToWishlist(String userLogin, Integer worldBookId) {
        BooksWishlist booksWishlist = new BooksWishlist();
        User user = userService.findByEmail(userLogin);
        WorldBook worldBook = booksCatalogService.findById(worldBookId);
        booksWishlist.setUser(user);
        booksWishlist.setBook(worldBook);
        booksWishlistRepository.save(booksWishlist);
    }

    public void delete(Integer recordId) {
        booksWishlistRepository.deleteById(recordId);
    }
}
