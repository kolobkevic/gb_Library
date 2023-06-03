package gb.library.reader.controllers;


import gb.library.common.entities.LibraryBook;
import gb.library.common.entities.ReservedBook;
import gb.library.common.entities.User;
import gb.library.reader.services.ReservedBooksService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({ReservedBookController.class})
class ReservedBookControllerTest {

    private static final String END_POINT_PATH = "/api/v1/reserved";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    @MockBean
    private ReservedBooksService service;


    /**
     *
     * ТЕСТЫ НЕ РАБОТАЮТ ИЗ-ЗА ОТСУТСВИЯ ПОСЛЕДОВАТЕЛЬНОЙ ЦЕПОЧКИ БИНОВ
     * ДЛЯ СОЗДАНИЯ ReservedBookConverter, КОТОРЫЙ ИСПОЛЬЗУЕТСЯ ПРИ ОТВЕТЕ
     * В МЕТОДАХ. ХЗ КАК ЭТО ОБОЙТИ КРОМЕ КАК СОЗДАВАТЬ В ТЕСТЕ ШТУК 10+
     * МЕТОДОВ ВОЗВРАЩАЮЩИХ ЭТИ БИНЫ. ТЕСТЫ СОЗДАНЫ ДЛЯ НАГЛЯДНОСТИ ПОКА
     *
     */

    /*
     * ПРИМЕЧАНИЕ:
     * сейчас в вызываемом сервисе нет проверки на наличие записей по ID
     * и в таком случае контроллер всё равно вернёт 200/204ый ответ,
     * т.е. не возвращается 404 ошибка - не есть хорошо, но пока что так.
     */

    @DisplayName("@GetMapping(\"/{userId}\" - 200 Ok")
    @Test
    public void testGetListByUserIDReturn200Ok() throws Exception {

        int pageNum = 1;
        int userId = 123;

        User user = new User();
        user.setId(userId);

        ReservedBook reservedBook = new ReservedBook();
        reservedBook.setLibraryBook(new LibraryBook());
        reservedBook.setUser(user);
        reservedBook.setLibraryBook(new LibraryBook());

        Page<ReservedBook> pageResponse = new PageImpl<>(List.of(reservedBook), PageRequest.of(pageNum-1, 1), 1);

        Mockito.when(service.getAllPageable(userId, pageNum)).thenReturn(pageResponse);

        String requestURI = END_POINT_PATH + "/" + userId;

        mockMvc.perform(put(requestURI).param("page", String.valueOf(pageNum)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andDo(print());

    }

    @DisplayName("@GetMapping(\"/{userId}\" - 404 Bad Request")
    @Test
    public void testGetListByUserIDReturn400BadRequest() throws Exception {

        int pageNum = 1;
    //  int userId = 123;   должен быть int
        String userId = "12c";  //ввели фигню

        String requestURI = END_POINT_PATH + "/" + userId;

        mockMvc.perform(put(requestURI).param("page", String.valueOf(pageNum)))
                .andExpect(status().isBadRequest())
                .andDo(print());

    }

    @DisplayName("@PostMapping(\"/create\" - 200 Ok")
    @Test
    public void testReserveBookReturn200OK() {
        //всё прошло ок
    }

    @DisplayName("@PostMapping(\"/create\" - 400 Bad Request")
    @Test
    public void testReserveBookReturn400BadRequest(){
        // некорректные данные в requestBody
    }

    @DisplayName("@DeleteMapping(\"/delete/{userId}\" - 204 No Content")
    @Test
    public void testDeleteReservedReturn204NoContent(){
        //удалили всё ок
    }

    @DisplayName("@DeleteMapping(\"/delete/{userId}\" - 400 Bad Request")
    @Test
    public void testDeleteReservedReturn400BadRequest(){
        //корявый id передали например
    }

}