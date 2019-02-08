package com.onlineshop;

import com.onlineshop.dao.entitys.Nomenclature;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Random;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TestApplication.class)
public class NomenclatureControllerTest {

    @Value("${testdata.username}")
    private String username;
    @Value("${testdata.password}")
    private String password;

    private HttpHeaders auth;
    private String nomenclatureControllerUrl;
    private String nomenclaturesControllerUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static class TestData {
        static final String NOMENCLATURE_N1_NAME = "Тестовя номенклатура 1";
        static final String NOMENCLATURE_N1_DESCRIPTION = "Тестовя номенклатура 1";
        static final String NOMENCLATURE_N2_NAME = "Тестовя номенклатура 2";
        static final String NOMENCLATURE_N2_DESCRIPTION = "Тестовя номенклатура 2";
    }

    @Before
    public void initSettings() {
        nomenclatureControllerUrl = "http://localhost:" + port + "/rest-api/admin/nomenclature";
        nomenclaturesControllerUrl = "http://localhost:" + port + "/rest-api/nomenclatures";
        auth = new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @Test
    public void createNewAndUpdateNomenclature() {

        // Создание новой номенклатуры
        Nomenclature request = new Nomenclature();
        request.setDescription(TestData.NOMENCLATURE_N1_NAME);
        request.setName(TestData.NOMENCLATURE_N1_DESCRIPTION);

        ResponseEntity<Nomenclature> responseCreate = restTemplate.exchange(
                nomenclatureControllerUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, auth),
                Nomenclature.class);

        Nomenclature nomenclatureResponse = responseCreate.getBody();

        // Проверка результата создания номенклатуры.
        Assert.assertEquals(
                HttpStatus.OK,
                responseCreate.getStatusCode());

        assert nomenclatureResponse != null;

        Assert.assertEquals(
                TestData.NOMENCLATURE_N1_NAME,
                nomenclatureResponse.getName());

        Assert.assertEquals(
                TestData.NOMENCLATURE_N1_DESCRIPTION,
                nomenclatureResponse.getDescription());

        // Обновление существующей номенклатуры.
        nomenclatureResponse.setDescription(TestData.NOMENCLATURE_N2_NAME);
        nomenclatureResponse.setName(TestData.NOMENCLATURE_N2_DESCRIPTION);

        ResponseEntity<Nomenclature> responseUpdate = restTemplate.exchange(
                nomenclatureControllerUrl
                        + "/" + String.valueOf(nomenclatureResponse.getId()),
                HttpMethod.PUT,
                new HttpEntity<>(nomenclatureResponse, auth),
                Nomenclature.class);

        // Проверка результата обновления.
        Assert.assertEquals(
                HttpStatus.OK,
                responseUpdate.getStatusCode());

        assert  responseUpdate.getBody() != null;

        Assert.assertEquals(
                TestData.NOMENCLATURE_N2_NAME,
                responseUpdate.getBody().getName());

        Assert.assertEquals(
                TestData.NOMENCLATURE_N2_DESCRIPTION,
                responseUpdate.getBody().getDescription());
    }

    @Test
    public void getAllNomenclature() {

        ResponseEntity<List> responseList = restTemplate.exchange(
                nomenclaturesControllerUrl,
                HttpMethod.GET,
                new HttpEntity<>(auth),
                List.class);

        Assert.assertEquals(
                HttpStatus.OK,
                responseList.getStatusCode());
    }

    @Test
    public void updateNonexistentNomenclature() {

         long id = new Random().nextLong();

        Nomenclature request = new Nomenclature();
        request.setId(id);
        request.setDescription(TestData.NOMENCLATURE_N1_NAME);
        request.setName(TestData.NOMENCLATURE_N1_DESCRIPTION);

        ResponseEntity<Void> responseUpdate = restTemplate.exchange(
                nomenclatureControllerUrl + String.valueOf(id),
                HttpMethod.PUT,
                new HttpEntity<>(request, auth),
                Void.class);

        Assert.assertEquals(
                HttpStatus.NOT_FOUND,
                responseUpdate.getStatusCode());

    }



}