package gb.library.backend.services;

import gb.library.backend.repositories.WorldBookRepository;
import gb.library.common.entities.WorldBook;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorldBookCommonService {
    private final WorldBookRepository worldBookRepository;

    public WorldBook findById(Integer id) {
        return worldBookRepository.findById(id).orElseThrow(
                () -> new ObjectInDBNotFoundException("Книга не найде базе, id: " + id, "World book"));
    }

    public WorldBook update(WorldBook worldBook){
        WorldBook updatedBook = worldBookRepository.findById(worldBook.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Книга не найде базе, id: " + worldBook.getId(), "World book"));
        updatedBook.setTitle(worldBook.getTitle());
        updatedBook.setAuthor(worldBook.getAuthor());
        updatedBook.setGenre(worldBook.getGenre());
        updatedBook.setAgeRating(worldBook.getAgeRating());
        updatedBook.setDescription(worldBook.getDescription());
        return worldBookRepository.save(updatedBook);
    }
}
