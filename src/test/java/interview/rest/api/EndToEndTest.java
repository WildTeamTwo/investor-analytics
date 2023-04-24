package interview.rest.api;

import interview.rest.api.model.AnalyticsResponse;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

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
