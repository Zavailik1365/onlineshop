package onlineshop;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.web.client.RestTemplate;

@PropertySources({
        @PropertySource(value = {
                "classpath:datasource.properties",
                "file:/C:/JavaProperties/onlineshoptest/datasource.properties"
        }, ignoreResourceNotFound = true)
})
@SpringBootApplication(scanBasePackages = {"com.onlineshop"})
public class TestApplication {
//    @Bean(name = "classifierRepositoryRestTemplate")
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

}