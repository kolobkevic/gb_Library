package gb.library.official.services;

import gb.library.backend.services.GenreCommonService;
import gb.library.common.dtos.GenreDTO;
import gb.library.common.entities.Genre;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.backend.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    private final GenreCommonService genreCommonService;

    public Genre findById(Integer id) {
        return genreCommonService.findById(id);
    }

    public List<Genre> findAll(String genre, String description) {
        return genreCommonService.findAll(genre, description);
    }

    public Genre save(Genre genre) {

        return genreRepository.save(genre);
    }

    public void deleteById(Integer id) {
        genreRepository.deleteById(id);
    }


    public Genre update(GenreDTO genreDTO) {
        Genre updatedGenre = genreRepository.findById(genreDTO.getId()).orElseThrow(
                () -> new ObjectInDBNotFoundException("Жанр не найде базе, id: " + genreDTO.getId(), "Genre"));
        updatedGenre.setName(genreDTO.getName());
        updatedGenre.setDescription(genreDTO.getDescription());
        return genreRepository.save(updatedGenre);
    }
}
