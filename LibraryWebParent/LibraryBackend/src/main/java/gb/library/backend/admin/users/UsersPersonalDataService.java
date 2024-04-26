package gb.library.backend.admin.users;

import gb.library.pd.openapi.client.pd.ApiException;
import gb.library.pd.openapi.client.pd.api.ReaderApi;
import gb.library.pd.openapi.client.pd.model.ReaderPatchRequest;
import gb.library.pd.openapi.client.pd.model.ReaderPostRequest;
import gb.library.pd.openapi.client.pd.model.ReaderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersPersonalDataService {

    private final ReaderApi readerApi;

    /**
     * Получить персональные данные пользователя.
     *
     * @param userId идентификатор пользователя
     * @return экземпляр класса ReaderResponse. Если какие-либо данные
     * отсутствуют, то соответствующее поле содержит null.
     * Если сервис, по каким-либо причинам не доступен, то
     * все поля класса содержат null.
     */
    public ReaderResponse getUserById(Long userId) {
        try {
            return readerApi.getReaderById(userId);
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
        }
        return new ReaderResponse();
    }


    public void newUser(ReaderPostRequest request) {
        try {
            readerApi.addNewReader(request);
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
        }
    }


    public void updateUser(Long readerId, ReaderPatchRequest request) {
        try {
            readerApi.updateReader(readerId, request);
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
        }
    }


    public void deleteUser(Long userId) {
        try {
            readerApi.removeReader(userId);
        } catch (ApiException e) {
            log.error(e.getMessage(), e);
        }
    }
}
