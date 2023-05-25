package gb.library.pd.controller;

import gb.library.pd.entity.ReaderEntity;
import gb.library.pd.mapper.ReaderMapper;
import gb.library.pd.openapi.api.ReaderApi;
import gb.library.pd.openapi.model.ReaderInfoResponse;
import gb.library.pd.openapi.model.ReaderPatchRequest;
import gb.library.pd.openapi.model.ReaderPostRequest;
import gb.library.pd.openapi.model.ReaderResponse;
import gb.library.pd.servace.ReaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ReaderController implements ReaderApi {

    private final ReaderService readerService;
    private final ReaderMapper readerMapper;

    @Override
    public ResponseEntity<ReaderResponse> addNewReader(ReaderPostRequest readerPostRequest) {
        ReaderEntity readerEntity = readerMapper.postRequestToEntity(readerPostRequest);
        readerEntity = readerService.newReader(readerEntity);
        ReaderResponse readerResponse = readerMapper.entityToReader(readerEntity);
        return new ResponseEntity<>(readerResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ReaderResponse> getReaderById(Long readerId) {
        ReaderEntity readerEntity = readerService.getReaderById(readerId);
        ReaderResponse readerResponse = readerMapper.entityToReader(readerEntity);
        return new ResponseEntity<>(readerResponse, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ReaderInfoResponse>> listOfReaders(Long offset, Integer limit) {
        // TODO: Добавить фильтр для выборки пользователей

        List<ReaderEntity> readerEntities = readerService.listOgReaders(offset, limit);
        List<ReaderInfoResponse> readerInfoResponses = readerEntities.stream()
                .map(readerMapper::entityToReaderInfo).toList();
        return new ResponseEntity<>(readerInfoResponses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> removeReader(Long readerId) {
        readerService.removeByReaderId(readerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<ReaderResponse> updateReader(Long readerId, ReaderPatchRequest readerPatchRequest) {
        ReaderEntity patchReader = readerMapper.patchRequestToEntity(readerPatchRequest);
        patchReader.setReaderId(readerId);
        ReaderEntity readerEntity = readerService.patchReader(patchReader);
        ReaderResponse readerResponse = readerMapper.entityToReader(readerEntity);
        return new ResponseEntity<>(readerResponse, HttpStatus.OK);
    }
}
