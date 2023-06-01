package gb.library.admin.users;

import gb.library.common.entities.User;
import gb.library.pd.openapi.client.pd.ApiException;
import gb.library.pd.openapi.client.pd.api.ReaderApi;
import gb.library.pd.openapi.client.pd.model.ReaderPatchRequest;
import gb.library.pd.openapi.client.pd.model.ReaderPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsersPersonalDataService {

    private final ReaderApi readerApi;

    public void newUser(User user) {
        ReaderPostRequest request = new ReaderPostRequest();

        request.setReaderId(Long.valueOf(user.getId()));
        request.setName(user.getFirstName());
        request.setSurname(user.getLastName());
        request.setEmail(user.getEmail());

        try {
            readerApi.addNewReader(request);
        } catch (ApiException e) {
            // TODO: Добавить логику обработки исключения
        }
    }


    public void updateUser(User user) {
        ReaderPatchRequest request = new ReaderPatchRequest();

        request.setName(user.getFirstName());
        request.setSurname(user.getLastName());
        request.setEmail(user.getEmail());

        try {
            readerApi.updateReader(Long.valueOf(user.getId()), request);
        } catch (ApiException e) {
            // TODO: Добавить логику обработки исключения
        }
    }


    public void deleteUser(Long userId) {
        try {
            readerApi.removeReader(userId);
        } catch (ApiException e) {
            // TODO: Добавить логику обработки исключения
        }
    }
}
