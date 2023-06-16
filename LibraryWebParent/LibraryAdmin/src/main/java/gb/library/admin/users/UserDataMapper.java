package gb.library.admin.users;

import gb.library.common.dtos.UserDataDTO;
import gb.library.common.entities.User;
import gb.library.pd.openapi.client.pd.model.ReaderPatchRequest;
import gb.library.pd.openapi.client.pd.model.ReaderPostRequest;
import gb.library.pd.openapi.client.pd.model.ReaderResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;


@Component
public class UserDataMapper {

    private final ModelMapper mapper;

    public UserDataMapper() {
        mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        mapper.typeMap(UserDataDTO.class, ReaderPostRequest.class)
                .addMapping(UserDataDTO::getFirstName, ReaderPostRequest::setName)
                .addMapping(UserDataDTO::getLastName, ReaderPostRequest::setSurname);

        mapper.typeMap(UserDataDTO.class, ReaderPatchRequest.class)
                .addMapping(UserDataDTO::getFirstName, ReaderPatchRequest::setName)
                .addMapping(UserDataDTO::getLastName, ReaderPatchRequest::setSurname);
    }


    public UserDataDTO toDto(User user, ReaderResponse reader) {
        UserDataDTO userDataDTO = mapper.map(reader, UserDataDTO.class);
        mapper.map(user, userDataDTO);
        return userDataDTO;
    }


    public User dtoToUser(UserDataDTO userDataDTO) {
        return mapper.map(userDataDTO, User.class);
    }


    public ReaderPostRequest dtoToPostRequest(UserDataDTO userDataDTO) {
        return mapper.map(userDataDTO, ReaderPostRequest.class);
    }


    public ReaderPatchRequest dtoToPatchRequest(UserDataDTO userDataDTO) {
        return mapper.map(userDataDTO, ReaderPatchRequest.class);
    }
}
