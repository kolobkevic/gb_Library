package gb.library.reader.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import gb.library.common.dtos.*;
import gb.library.common.entities.*;
import gb.library.common.enums.AgeRating;
import gb.library.reader.converters.ReservedBookConverter;
import gb.library.reader.dtos.ReservedBookReaderDto;
import gb.library.reader.dtos.UserReaderDto;
import gb.library.reader.services.ReservedBooksService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservedBookControllerTest {

    private static final String END_POINT_PATH = "/api/v1/reserved/";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ReservedBooksService service;
    @MockBean
    private ReservedBookConverter converter;



    @DisplayName("@GetMapping(\"/{userId}\") - 200 Ok")
    @Test
    public void testGetListByUserIDReturn200Ok() throws Exception {

        int pageNum = 1;
        int userId = 123;

        User user = new User();
        user.setId(userId);

        LibraryBook libBook = generateLibraryBook();

        ReservedBook reservedBook = new ReservedBook();
        reservedBook.setId(1);
        reservedBook.setLibraryBook(libBook);
        reservedBook.setUser(user);
        reservedBook.setLibraryBook(libBook);

        ReservedBookReaderDto dto = generateReservedBookReaderDto(userId);

        Page<ReservedBook> pageResponse = new PageImpl<>(List.of(reservedBook), PageRequest.of(pageNum-1, 1), 1);

        Mockito.when(service.getAllPageable(userId, pageNum)).thenReturn(pageResponse);
        Mockito.when(converter.entityToDto(Mockito.any())).thenReturn(dto);

        String requestURI = END_POINT_PATH + userId;

        mockMvc.perform(get(requestURI).param("page", String.valueOf(pageNum)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());

    }

    private static ReservedBookReaderDto generateReservedBookReaderDto(int userId) {
        UserReaderDto userDto = new UserReaderDto(userId, "email", "firstName", "lastName", "pass");

        WorldBookDTO worldBookDto = new WorldBookDTO();
        worldBookDto.setGenreDTO(new GenreDTO(1, "Genre", "Genre description"));
        worldBookDto.setAuthorDTO(new AuthorDTO(1, "FirstName", "SecondName"));
        worldBookDto.setId(1);
        worldBookDto.setTitle("Test");
        worldBookDto.setDescription("Test dto world book");
        worldBookDto.setAgeRating(String.valueOf(AgeRating.PG13));

        LibraryBookDTO libBookDto = new LibraryBookDTO();
        libBookDto.setWorldBookDTO(worldBookDto);
        libBookDto.setId(1);
        libBookDto.setIsbn("isbn123456789");
        libBookDto.setAvailable(true);
        libBookDto.setPublisher("Publisher");
        libBookDto.setInventoryNumber("N00000000000000000089");
        libBookDto.setPlacedAt(new StorageDTO(1, "zone-1", "sector-B", true));

        return new ReservedBookReaderDto(-1, userDto, libBookDto, worldBookDto);
    }

    private static LibraryBook generateLibraryBook() {
        Author author = new Author();
        author.setId(1);
        WorldBook worldBook = new WorldBook();
        worldBook.setAuthor(author);
        LibraryBook libBook = new LibraryBook();
        libBook.setId(1);
        libBook.setWorldBook(worldBook);
        return libBook;
    }

    @DisplayName("@GetMapping(\"/{userId}\") - 404 Bad Request")
    @Test
    public void testGetListByUserIDReturn400BadRequest() throws Exception {

        int pageNum = 1;
    //  int userId = 123;   должен быть int
        String userId = "12c";  //ввели фигню

        String requestURI = END_POINT_PATH + userId;

        mockMvc.perform(get(requestURI).param("page", String.valueOf(pageNum)))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @DisplayName("@PostMapping(\"/create\") - 200 Ok")
    @Test
    public void testReserveBookReturn200OK() throws Exception {
        //всё прошло ок
        ReservedBookReaderDto inputDto = generateReservedBookReaderDto(1);

        Mockito.when(converter.DtoToEntity(inputDto)).thenReturn(new ReservedBook());
        Mockito.when(service.create(Mockito.any(ReservedBook.class))).thenReturn(new ReservedBook());
        Mockito.when(converter.entityToDto(Mockito.any(ReservedBook.class))).thenReturn(inputDto);

        String bodyContent = objectMapper.writeValueAsString(inputDto);
        String requestURI = END_POINT_PATH + "create";

        mockMvc.perform(post(requestURI).contentType("application/json").content(bodyContent))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andDo(print());
    }

    @DisplayName("@PostMapping(\"/create\") - 400 Bad Request")
    @Test
    public void testReserveBookReturn400BadRequest() throws Exception {
        // некорректные данные в requestBody
        ReservedBookReaderDto inputDto = generateReservedBookReaderDto(-3); // кривой ID

        String bodyContent = objectMapper.writeValueAsString(inputDto);
        String requestURI = END_POINT_PATH + "create";

        mockMvc.perform(post(requestURI).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @DisplayName("@DeleteMapping(\"/delete/{userId}\") - 204 No Content")
    @Test
    public void testDeleteReservedReturn204NoContent() throws Exception{
        //удалили всё ок
        String userID = "3";

        String requestURI = END_POINT_PATH + "delete/" + userID;
        Mockito.doNothing().when(service).delete(Mockito.any());

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @DisplayName("@DeleteMapping(\"/delete/{userId}\") - 400 Bad Request")
    @Test
    public void testDeleteReservedReturn400BadRequest() throws Exception{
        //корявый id передали например
        String userId = "-12";  //ввели фигню

        String requestURI = END_POINT_PATH + "delete/" + userId;

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

}