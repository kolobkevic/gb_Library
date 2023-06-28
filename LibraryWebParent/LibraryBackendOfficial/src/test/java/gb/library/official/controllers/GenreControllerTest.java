package gb.library.official.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import gb.library.common.dtos.GenreDTO;
import gb.library.common.entities.Genre;
import gb.library.common.exceptions.ObjectInDBNotFoundException;
import gb.library.official.services.GenreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.*;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class GenreControllerTest {
    private static final String END_POINT_PATH = "/api/v1/genres";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private GenreService service;

    @Test
    @DisplayName("Validate DTO Error - ID некорректное значение")
    public void testValidateRequestBodyIdNotPositive() throws Exception {
        GenreDTO dto = new GenreDTO(-1, "Genre name", "Genre description");

        String requestBodyContent = mapper.writeValueAsString(dto);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("ID не может быть отрицательным")))
                .andDo(print());
    }

    @Test
    @DisplayName("Validate DTO Error - название жанра is NULL")
    public void testValidateRequestBodyNameIsNull() throws Exception {
        GenreDTO dto = new GenreDTO(23, null, "Genre description");

        String requestBodyContent = mapper.writeValueAsString(dto);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]", is("Название жанра должно быть заполнено")))
                .andDo(print());
    }

    @Test
    @DisplayName("Validate DTO Error - Все поля некорректны")
    public void testValidateRequestBodyAllFieldsInvalid() throws Exception {
        GenreDTO dto = new GenreDTO(-1, "ХЗ",
                "Очень длинное описание жанра.... Очень-очень-очень-очень-очень-очень-очень-очень-очень длинное." +
                                "Повторю, очень-очень-очень-очень-очень-очень-очень-очень длинное! Ну прям оооооооочень! " +
                                "В последний раз повторяю, ооооооооооооооооооооооооооооооооооооооочень длиииииинное!");

        String requestBodyContent = mapper.writeValueAsString(dto);

        MvcResult mvcResult = mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(requestBodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andDo(print())
                .andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertThat(responseBody).contains("Название жанра должно состоять из 3-40 символов");
        assertThat(responseBody).contains("Максимальная длина описания не должна превышать 200 символов");
        assertThat(responseBody).contains("ID не может быть отрицательным");
    }


    @Test
    @DisplayName("@GetMapping(\"\") - 200 Ok")
    public void testGetGenreListReturn200Ok() throws Exception{

        List<Genre> listGenres = generateGenresList();

        Mockito.when(service.findAll(null, null)).thenReturn(listGenres);

        mockMvc.perform(get(END_POINT_PATH))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    private List<Genre> generateGenresList() {
        Genre genre1 = new Genre();
        genre1.setId(1);
        genre1.setName("Name 1");
        genre1.setDescription("Description 1");

        Genre genre2 = new Genre();
        genre2.setId(2);
        genre2.setName("Genre without description");

        return List.of(genre1, genre2);
    }

    @Test
    @DisplayName("@PostMapping(\"/{code}\") - 405 Not Allowed")
    public void testPostShouldReturn405MethodNotAllowed() throws Exception{
        String requestURI = END_POINT_PATH + "/ABCDEF";

        mockMvc.perform(post(requestURI))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());
    }

    @Test
    @DisplayName("@GetMapping(\"/{id}\") - 404 Not Found")
    public void testGetByIdShouldReturn404NotFound() throws Exception{
        String genreCode = "543";
        String requestURI = END_POINT_PATH + "/" + genreCode;

        ObjectInDBNotFoundException ex = new ObjectInDBNotFoundException("Жанр не найден в базе, id: " + genreCode, "Genre");

        Mockito.when(service.findById(Integer.valueOf(genreCode))).thenThrow(ex);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    @DisplayName("@GetMapping(\"/{id}\") - 200 Ok")
    public void testGetByIdShouldReturn200Ok() throws Exception{
        String genreCode = "543";
        String requestURI = END_POINT_PATH + "/" + genreCode;

        Genre genre = new Genre();
        genre.setId(Integer.valueOf(genreCode));
        genre.setName("Название жанра");
        genre.setDescription("Описание жанра");


        Mockito.when(service.findById(Integer.valueOf(genreCode))).thenReturn(genre);

        mockMvc.perform(get(requestURI))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", is(Integer.valueOf(genreCode))))
                .andDo(print());
    }

    @Test
    @DisplayName("@DeleteMapping(\"/{id}\") - 204 No content")
    public void testDeleteShouldReturn204NoContent() throws Exception{
        String genreCode = "543";
        String requestURI = END_POINT_PATH + "/" + genreCode;

        Mockito.doNothing().when(service).deleteById(Integer.valueOf(genreCode));

        mockMvc.perform(delete(requestURI))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    @Test
    @DisplayName("@DeleteMapping(\"/{id}\") - 400 Bad Request")
    public void testDeleteShouldReturn400BadRequest() throws Exception{
        String genreCode = "-543";
        String requestURI = END_POINT_PATH + "/" + genreCode;


        mockMvc.perform(delete(requestURI))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("@PutMapping(\"\") - 400 Bad Request")
    public void testUpdateShouldReturn400BadRequest() throws Exception {
        GenreDTO dto = new GenreDTO(-123, "Name", "Description");

        String bodyContent = mapper.writeValueAsString(dto);

        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    @DisplayName("@PutMapping(\"\") - 404 Not Found")
    public void testUpdateShouldReturn404NotFound() throws Exception {
        int genreCode = 123;
        GenreDTO dto = new GenreDTO(genreCode, "Name", "Description");

        String bodyContent = mapper.writeValueAsString(dto);
        ObjectInDBNotFoundException ex = new ObjectInDBNotFoundException("Жанр не найден в базе, id: " + genreCode, "Genre");

        Mockito.when(service.update(dto)).thenThrow(ex);

        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.message", is("Жанр не найден в базе, id: " + genreCode)))
                .andDo(print());
    }

    @Test
    @DisplayName("@PutMapping(\"\") - 202 Accepted")
    public void testUpdateShouldReturn202Accepted() throws Exception {
        int genreCode = 123;
        GenreDTO dto = new GenreDTO(genreCode, "Name", "Description");
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());

        String bodyContent = mapper.writeValueAsString(dto);

        Mockito.when(service.update(dto)).thenReturn(genre);

        mockMvc.perform(put(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andDo(print());
    }

    @Test
    @DisplayName("@PostMapping(\"\") - 400 Bad Request")
    public void testCreateShouldReturn400BadRequest() throws Exception {
        GenreDTO dto = new GenreDTO(0, "X", "Description");

        String bodyContent = mapper.writeValueAsString(dto);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json"))
                .andDo(print());
    }

    @Test
    @DisplayName("@PostMapping(\"\") - 201 Accepted")
    public void testCreateShouldReturn201Created() throws Exception {
        GenreDTO dto = new GenreDTO(0, "Name", "Description");
        Genre genre = new Genre();
        genre.setId(666);
        genre.setName(dto.getName());
        genre.setDescription(dto.getDescription());

        String bodyContent = mapper.writeValueAsString(dto);

        Mockito.when(service.save(Mockito.any())).thenReturn(genre);

        mockMvc.perform(post(END_POINT_PATH).contentType("application/json").content(bodyContent))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.id", greaterThan(0)))
                .andDo(print());
    }
}