package interview.rest.api.jobs;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * Created by paul on 26.10.18.
 * <p/>
 * Downloads MAX_RECORDS_PER_DOWNLOAD records per call from the Open Data API.
 * Saves MAX_SIMULTANEOUS_DOWNLOADS * MAX_BATCH FILES files to the file sytem.
 * <p/>
 * Downloads roughly 500,000 records in 2 minutes.
 * <p/>
 * Total # of Records = MAX_RECORDS_PER_DOWNLOAD * MAX_SIMULTANEOUS_DOWNLOADS * MAX_BATCH
 */
@Component
public class PubliCityTrafficAPI {

    private static final String URL = "https://services1.arcgis.com/UWYHeuuJISiGmgXx/arcgis/rest/services/Parking_and_Moving_Citations__view/FeatureServer/2/query?where=1%3D1&outFields=*&outSR=4326&f=json";

    public String downloadParkingData() throws IOException, URISyntaxException {

        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = buildUrl(URL, "0", "1000", "importdate");
        HttpResponse response = client.execute(request);
        if (response.getStatusLine().getStatusCode() != 200) {
            System.err.printf("\n Received status code %d. \n See response \n %s",
                    response.getStatusLine().getStatusCode(),
                    EntityUtils.toString(response.getEntity()));
            return "{}";
        }
        String responseStr = response == null ? "" : EntityUtils.toString(response.getEntity());
        EntityUtils.consume(response.getEntity());
        return responseStr;
    }

    private static HttpGet buildUrl(String resource, String offset, String limit, String orderBy) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(resource);

        if (Optional.ofNullable(orderBy).isPresent())
            builder.setParameter("$order", orderBy + " DESC");

        if (Optional.ofNullable(offset).isPresent())
            builder.setParameter("$offset", offset);

        if (Optional.ofNullable(limit).isPresent())
            builder.setParameter("$limit", limit);

        return new HttpGet(builder.build());
    }

}
