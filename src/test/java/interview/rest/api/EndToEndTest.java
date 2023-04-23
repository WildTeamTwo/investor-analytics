package interview.rest.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import interview.rest.api.model.AnalyticsResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

import java.net.URI;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/analytics?serverTimezone=UTC"
})
public class EndToEndTest {

    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void contextLoads() {

    }
    @Test
    public void createAnalyticsJob() throws  Exception {
        final String baseUrl = "http://localhost:"+8080+"/analytics/job";
        final Integer expectedResponse = 200;
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>("{\n" +
                "    \"reports\": [\n" +
                "        \"Report Name A\",\n" +
                "        \"Report Name B\",\n" +
                "        \"Report Name C\"\n" +
                "    ]\n" +
                "}",headers);

        ResponseEntity<String> actual = this.restTemplate.postForEntity(uri, request, String.class);
        Assert.assertEquals(expectedResponse.intValue(), actual.getStatusCodeValue());
    }

    @Test
    public void getAnalyticsJobSatus() throws Exception {
        final String baseUrl = "http://localhost:"+8080+"/analytics/job/09b4b3f8-b6f4-40c8-9e2b-204b0655609c";
        final Integer expectedResponse = 200;
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> request = new HttpEntity<>(null,headers);
        ResponseEntity<AnalyticsResponse> actual = this.restTemplate.getForEntity(baseUrl, AnalyticsResponse.class);
        Assert.assertEquals(expectedResponse.intValue(), actual.getStatusCodeValue());
    }

}
