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


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TestApplication.class)
public class NomenclatureControllerTest {

    @Value("${testdata.username}")
    private String username;
    @Value("${testdata.password}")
    private String password;
    private HttpHeaders auth;
    private String nomenclatureControllerUrl;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    static class TestData {

        static final String NOMENCLATURE_N1_NAME = "Тестовя номенклатура 3";
        static final String NOMENCLATURE_N1_DESCRIPTION = "Тестовя номенклатура 1";
        static final long NOMENCLATURE_N2_UUID = 2;
        static final String NOMENCLATURE_N2_DESCRIPTION = "Тестовя номенклатура 2";

    }

    @Before
    public void initData() {
        nomenclatureControllerUrl = "http://localhost:" + port + "/rest-api/admin/nomenclature";
        auth = new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.encodeBase64(
                    auth.getBytes(Charset.forName("US-ASCII")) );
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    @Test
    public void createNewNomenclature() {

        Nomenclature request = new Nomenclature();
        request.setId(1);
        request.setDescription(TestData.NOMENCLATURE_N1_NAME);
        request.setName(TestData.NOMENCLATURE_N1_DESCRIPTION);

        ResponseEntity<Void> response = restTemplate.exchange(
                nomenclatureControllerUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, auth),
                Void.class);
        Assert.assertEquals(
                HttpStatus.INTERNAL_SERVER_ERROR,
                response.getStatusCode());
    }

}