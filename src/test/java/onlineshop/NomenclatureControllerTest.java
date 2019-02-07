package onlineshop;

import com.onlineshop.dao.entitys.Nomenclature;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.Charset;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = TestApplication.class)
public class NomenclatureControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    private String nomenclatureControllerUrl;
    private HttpHeaders authHeader;

    static class TestData {
        static final long NOMENCLATURE_N1_UUID = 0;
        static final String NOMENCLATURE_N1_NAME = "Тестовя номенклатура 1";
        static final String NOMENCLATURE_N1_DESCRIPTION = "Тестовя номенклатура 1";
        static final long NOMENCLATURE_N2_UUID = 2;
        static final String NOMENCLATURE_N2_DESCRIPTION = "Тестовя номенклатура 2";
    }

    @Before
    public void initData() {
        nomenclatureControllerUrl = "http://localhost:" + port + "/rest-api/admin/nomenclature";
        authHeader = new HttpHeaders() {{
            String auth = "Admin:123";
                byte[] encodedAuth = Base64.encodeBase64(
                        auth.getBytes(Charset.forName("US-ASCII")) );
                String authHeader = "Basic " + new String(encodedAuth);
                set( "Authorization", authHeader );
        }};
    }

    @Test
    public void createNewNomenclatureWithEmptyIdRequest() {

        Nomenclature request = new Nomenclature();
        request.setId(TestData.NOMENCLATURE_N1_UUID);
        request.setName(TestData.NOMENCLATURE_N1_NAME);
        request.setDescription(TestData.NOMENCLATURE_N1_DESCRIPTION);

        ResponseEntity<Void> response = restTemplate.exchange(
                nomenclatureControllerUrl,
                HttpMethod.POST,
                new HttpEntity<>(request, authHeader),
                Void.class);
        Assert.assertEquals(
                HttpStatus.BAD_REQUEST,
                response.getStatusCode());
    }

}