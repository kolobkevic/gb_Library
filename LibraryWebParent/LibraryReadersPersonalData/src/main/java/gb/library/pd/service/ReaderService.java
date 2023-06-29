package gb.library.pd.service;

import gb.library.pd.entity.embedded.ContactInfo;
import gb.library.pd.entity.embedded.MainInfo;
import gb.library.pd.entity.ReaderEntity;
import gb.library.pd.repository.ReaderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;


    @Transactional
    public ReaderEntity newReader(ReaderEntity readerEntity) {
        try {
            readerEntity.setId(null);
            return readerRepository.save(readerEntity);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Ошибка регистрации нового пользователя");
        }
    }


    public ReaderEntity getReaderById(Long readerId) {
        ReaderEntity readerEntity = readerRepository.findByReaderId(readerId);
        if (readerEntity == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("ID %d пользователя не найден", readerId));
        }
        return readerEntity;
    }


    public List<ReaderEntity> listOgReaders(Long offset, Integer limit) {
        return readerRepository.findAllOffsetLimit(offset, limit);
    }


    @Transactional
    public void removeByReaderId(Long readerId) {
        readerRepository.removeReaderByReaderId(readerId);
    }


    @Transactional
    public ReaderEntity patchReader(ReaderEntity patchReader) {
        ReaderEntity readerEntity = getReaderById(patchReader.getReaderId());

        MainInfo mainInfo = readerEntity.getMainInfo();
        MainInfo patchMainInfo = patchReader.getMainInfo();
        if (patchMainInfo.getName() != null && !patchMainInfo.getName().isBlank()) {
            mainInfo.setName(patchMainInfo.getName());
        }
        if (patchMainInfo.getSurname() != null && !patchMainInfo.getSurname().isBlank()) {
            mainInfo.setSurname(patchMainInfo.getSurname());
        }
        if (patchMainInfo.getBirthday() != null) {
            mainInfo.setBirthday(patchMainInfo.getBirthday());
        }
        if (patchMainInfo.getAddress() != null && !patchMainInfo.getAddress().isBlank()) {
            mainInfo.setAddress(patchMainInfo.getAddress());
        }
        if (patchMainInfo.getPassport() != null && !patchMainInfo.getPassport().isBlank()) {
            mainInfo.setPassport(patchMainInfo.getPassport());
        }

        readerEntity.setMainInfo(mainInfo);


        ContactInfo contactInfo = readerEntity.getContactInfo() != null ?
                readerEntity.getContactInfo() : new ContactInfo();
        ContactInfo patchContactInfo = patchReader.getContactInfo();

        if (patchContactInfo.getEmail() != null && !patchContactInfo.getEmail().isBlank()) {
            contactInfo.setEmail(patchContactInfo.getEmail());
        }
        if (patchContactInfo.getPhone1() != null && !patchContactInfo.getPhone1().isBlank()) {
            contactInfo.setPhone1(patchContactInfo.getPhone1());
        }
        if (patchContactInfo.getPhone2() != null && !patchContactInfo.getPhone2().isBlank()) {
            contactInfo.setPhone2(patchContactInfo.getPhone2());
        }
        readerEntity.setContactInfo(contactInfo);


        return readerRepository.save(readerEntity);
    }
}
