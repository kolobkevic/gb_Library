package gb.library.pd.controller;

import gb.library.pd.config.security.AccessGroupType;
import gb.library.pd.config.security.JwtTokenUtil;
import gb.library.pd.entity.ReaderEntity;
import gb.library.pd.entity.embedded.MainInfo;
import gb.library.pd.openapi.model.ReaderInfoResponse;
import gb.library.pd.openapi.model.ReaderPostRequest;
import gb.library.pd.openapi.model.ReaderResponse;
import gb.library.pd.repository.ReaderRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.Month;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReaderControllerIntegrationTest {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private ReaderRepository readerRepository;

    @Value("${server.port}")
    int serverPort;

    private RestTemplateBuilder administratorTemplateBuilder;
    private RestTemplateBuilder employeeTemplateBuilder;
    private RestTemplateBuilder userTemplateBuilder;

    private long countOfEntities;

    @PostConstruct
    public void initialize() {
        countOfEntities = 10;

        String rootUri = "http://localhost:" + serverPort + "/api/v1";

        System.out.println(rootUri);

//        jwtTokenUtil.setLifetime(Duration.ZERO);

        String administratorToken = jwtTokenUtil.generateToken(5L, AccessGroupType.ADMINISTRATOR);
        administratorTemplateBuilder = new RestTemplateBuilder()
                .rootUri(rootUri)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + administratorToken);

        String employeeToken = jwtTokenUtil.generateToken(7L, AccessGroupType.EMPLOYEE);
        employeeTemplateBuilder = new RestTemplateBuilder()
                .rootUri(rootUri)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + employeeToken);

        String userToken = jwtTokenUtil.generateToken(9L, AccessGroupType.USER);
        userTemplateBuilder = new RestTemplateBuilder()
                .rootUri(rootUri)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + userToken);
    }


    @Test
    public void testReaderGetRequest() {
        testReaderGetRequest(administratorTemplateBuilder, HttpStatus.OK);
        testReaderGetRequest(employeeTemplateBuilder, HttpStatus.OK);
        testReaderGetRequest(userTemplateBuilder, HttpStatus.FORBIDDEN);
    }

    public void testReaderGetRequest(RestTemplateBuilder templateBuilder,
                                     HttpStatusCode httpStatusCode) {
        System.out.println("====testReaderGetRequest=============\n");

        TestRestTemplate restTemplate = new TestRestTemplate(templateBuilder);
        ResponseEntity<ReaderInfoResponse[]> entity =
                restTemplate.getForEntity("/reader", ReaderInfoResponse[].class);

        Assertions.assertEquals(entity.getStatusCode(), httpStatusCode);

        if (entity.getStatusCode().is2xxSuccessful()) {
            ReaderInfoResponse[] response = entity.getBody();

            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.length, countOfEntities);
        }
    }


    @Test
    public void testReaderPostRequest() {
        ReaderPostRequest postRequest = new ReaderPostRequest(195487L, "Фёдор");
        postRequest.setSurname("Емельянов");
        postRequest.setBirthday(LocalDate.of(1999, Month.AUGUST, 15));
        postRequest.setEmail("emelianov@mail.ru");
        postRequest.setPhone1("8-954-321-45-78");
        postRequest.setPhone2("8-918-451-50-41");
        postRequest.setAddress("г. Москва, ул. Дыбенко, д.16, кв.54");
        postRequest.setPassport("11 04 123456");

        testReaderPostRequest(administratorTemplateBuilder, postRequest, HttpStatus.CREATED);
        testReaderPostRequest(employeeTemplateBuilder, postRequest, HttpStatus.CREATED);
        testReaderPostRequest(userTemplateBuilder, postRequest, HttpStatus.FORBIDDEN);
    }

    public void testReaderPostRequest(RestTemplateBuilder templateBuilder,
                                      ReaderPostRequest postRequest,
                                      HttpStatusCode httpStatusCode) {
        System.out.println("====testReaderPostRequest=============\n");

        TestRestTemplate restTemplate = new TestRestTemplate(templateBuilder);
        ResponseEntity<ReaderResponse> entity =
                restTemplate.postForEntity("/reader", postRequest, ReaderResponse.class);

        Assertions.assertEquals(entity.getStatusCode(), httpStatusCode);

        if (entity.getStatusCode().is2xxSuccessful()) {
            ReaderResponse response = entity.getBody();

            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.getReaderId(), postRequest.getReaderId());
            Assertions.assertEquals(response.getName(), postRequest.getName());
            Assertions.assertEquals(response.getSurname(), postRequest.getSurname());
            Assertions.assertEquals(response.getBirthday(), postRequest.getBirthday());
            Assertions.assertEquals(response.getEmail(), postRequest.getEmail());
            Assertions.assertEquals(response.getPhone1(), postRequest.getPhone1());
            Assertions.assertEquals(response.getPhone2(), postRequest.getPhone2());
            Assertions.assertEquals(response.getAddress(), postRequest.getAddress());
            Assertions.assertEquals(response.getPassport(), postRequest.getPassport());
        }

        readerRepository.deleteAll();
    }


    @Test
    public void testReaderGetFromIDRequest() {
        testReaderGetFromIDRequest(administratorTemplateBuilder, 5L, HttpStatus.OK);
        testReaderGetFromIDRequest(administratorTemplateBuilder, 7L, HttpStatus.OK);
        testReaderGetFromIDRequest(administratorTemplateBuilder, 9L, HttpStatus.OK);

        testReaderGetFromIDRequest(employeeTemplateBuilder, 5L, HttpStatus.OK);
        testReaderGetFromIDRequest(employeeTemplateBuilder, 7L, HttpStatus.OK);
        testReaderGetFromIDRequest(employeeTemplateBuilder, 9L, HttpStatus.OK);

        testReaderGetFromIDRequest(userTemplateBuilder, 5L, HttpStatus.FORBIDDEN);
        testReaderGetFromIDRequest(userTemplateBuilder, 7L, HttpStatus.FORBIDDEN);
        testReaderGetFromIDRequest(userTemplateBuilder, 9L, HttpStatus.OK);
    }

    public void testReaderGetFromIDRequest(RestTemplateBuilder templateBuilder,
                                           Long readerId,
                                           HttpStatusCode httpStatusCode) {
        System.out.println("====testReaderGetFromIDRequest=============\n");

        String url = String.format("/reader/%d", readerId);
        TestRestTemplate restTemplate = new TestRestTemplate(templateBuilder);
        ResponseEntity<ReaderResponse> entity =
                restTemplate.getForEntity(url, ReaderResponse.class);

        Assertions.assertEquals(entity.getStatusCode(), httpStatusCode);

        if (entity.getStatusCode().is2xxSuccessful()) {
            ReaderResponse response = entity.getBody();

            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.getReaderId(), readerId);
        }
    }


    @Test
    public void testReaderDeleteRequest() {
        testCountReaders(administratorTemplateBuilder, countOfEntities);

        testReaderDeleteRequest(administratorTemplateBuilder, 4L, countOfEntities - 1);
        testReaderDeleteRequest(employeeTemplateBuilder, 5L, countOfEntities - 1);
        testReaderDeleteRequest(userTemplateBuilder, 6L, countOfEntities - 1);
    }

    public void testReaderDeleteRequest(RestTemplateBuilder templateBuilder, long readerID, long countAfterDelete) {
        System.out.println("====testReaderDeleteRequest=============\n");

        String url = String.format("/reader/%d", readerID);
        TestRestTemplate restTemplate = new TestRestTemplate(templateBuilder);
        restTemplate.delete(url);

        testCountReaders(administratorTemplateBuilder, countAfterDelete);
    }

    public void testCountReaders(RestTemplateBuilder templateBuilder, long count) {

        TestRestTemplate restTemplate = new TestRestTemplate(templateBuilder);
        ResponseEntity<ReaderInfoResponse[]> entity =
                restTemplate.getForEntity("/reader", ReaderInfoResponse[].class);

        if (entity.getStatusCode().is2xxSuccessful()) {
            ReaderInfoResponse[] response = entity.getBody();

            Assertions.assertNotNull(response);
            Assertions.assertEquals(response.length, count);
        }
    }


    @BeforeEach
    public void loadDB() {
        System.out.println("====BeforeEach==BEGIN=============\n");
        for (long i = 1; i <= countOfEntities; i++) {
            MainInfo info = new MainInfo();
            info.setName(String.format("Имя %d", i));
            ReaderEntity entity = new ReaderEntity();
            entity.setReaderId(i);
            entity.setMainInfo(info);
            readerRepository.save(entity);
        }
        System.out.println("====BeforeEach==END==============\n");
    }

    @AfterEach
    public void clearDb() {
        System.out.println("====AfterEach==BEGIN=============\n");
        readerRepository.deleteAll();
        System.out.println("====AfterEach==END==============\n");
    }
}
