package gb.library.pd.mapper;

import gb.library.pd.entity.embedded.ContactInfo;
import gb.library.pd.entity.embedded.MainInfo;
import gb.library.pd.entity.ReaderEntity;
import gb.library.pd.openapi.model.ReaderInfoResponse;
import gb.library.pd.openapi.model.ReaderPatchRequest;
import gb.library.pd.openapi.model.ReaderPostRequest;
import gb.library.pd.openapi.model.ReaderResponse;
import org.springframework.stereotype.Component;


@Component
public class ReaderMapper {

    public ReaderEntity postRequestToEntity(ReaderPostRequest readerPostRequest) {
        ReaderEntity readerEntity = new ReaderEntity();
        MainInfo mainInfo = new MainInfo();
        ContactInfo contactInfo = new ContactInfo();

        mainInfo.setName(readerPostRequest.getName());
        mainInfo.setSurname(readerPostRequest.getSurname());
        mainInfo.setFamily(readerPostRequest.getFamily());
        mainInfo.setBirthday(readerPostRequest.getBirthday());

        contactInfo.setEmail(readerPostRequest.getEmail());
        contactInfo.setPhone1(readerPostRequest.getPhone1());
        contactInfo.setPhone2(readerPostRequest.getPhone2());

        readerEntity.setReaderId(readerPostRequest.getReaderId());
        readerEntity.setMainInfo(mainInfo);
        readerEntity.setContactInfo(contactInfo);
        readerEntity.setAddress(readerPostRequest.getAddress());

        return readerEntity;
    }


    public ReaderEntity patchRequestToEntity(ReaderPatchRequest readerPatchRequest) {
        ReaderEntity readerEntity = new ReaderEntity();
        MainInfo mainInfo = new MainInfo();
        ContactInfo contactInfo = new ContactInfo();

        mainInfo.setName(readerPatchRequest.getName());
        mainInfo.setSurname(readerPatchRequest.getSurname());
        mainInfo.setFamily(readerPatchRequest.getFamily());
        mainInfo.setBirthday(readerPatchRequest.getBirthday());

        contactInfo.setEmail(readerPatchRequest.getEmail());
        contactInfo.setPhone1(readerPatchRequest.getPhone1());
        contactInfo.setPhone2(readerPatchRequest.getPhone2());

        readerEntity.setMainInfo(mainInfo);
        readerEntity.setContactInfo(contactInfo);
        readerEntity.setAddress(readerPatchRequest.getAddress());

        return readerEntity;
    }


    public ReaderResponse entityToReader(ReaderEntity readerEntity) {
        ReaderResponse readerResponse = new ReaderResponse();

        MainInfo mainInfo = readerEntity.getMainInfo();
        ContactInfo contactInfo = readerEntity.getContactInfo();

        readerResponse.setReaderId(readerEntity.getReaderId());

        readerResponse.setName(mainInfo.getName());
        readerResponse.setSurname(mainInfo.getSurname());
        readerResponse.setFamily(mainInfo.getFamily());
        readerResponse.setBirthday(mainInfo.getBirthday());

        if (contactInfo != null) {
            readerResponse.setEmail(contactInfo.getEmail());
            readerResponse.setPhone1(contactInfo.getPhone1());
            readerResponse.setPhone2(contactInfo.getPhone2());
        }

        readerResponse.setAddress(readerEntity.getAddress());

        return readerResponse;
    }

    public ReaderInfoResponse entityToReaderInfo(ReaderEntity readerEntity) {
        ReaderInfoResponse readerInfoResponse = new ReaderInfoResponse();
        MainInfo mainInfo = readerEntity.getMainInfo();

        readerInfoResponse.setReaderId(readerEntity.getReaderId());
        readerInfoResponse.setName(mainInfo.getName());
        readerInfoResponse.setSurname(mainInfo.getSurname());
        readerInfoResponse.setFamily(mainInfo.getFamily());
        readerInfoResponse.setBirthday(mainInfo.getBirthday());

        return readerInfoResponse;
    }
}
