package gb.library.reader.services;

import gb.library.backend.services.LibraryBookCommonService;
import gb.library.common.entities.LibraryBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LibraryBookService {
    private final LibraryBookCommonService commonService;

    public LibraryBook findById(int id){
        return commonService.findById(id);
    }
}
