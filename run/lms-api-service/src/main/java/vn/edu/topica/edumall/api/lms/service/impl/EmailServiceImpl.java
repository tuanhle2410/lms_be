package vn.edu.topica.edumall.api.lms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.edu.topica.edumall.api.lms.dto.UploadS3FinishedEmailDTO;
import vn.edu.topica.edumall.api.lms.service.EmailService;
import vn.edu.topica.edumall.data.enumtype.StatusSendExternalServiceEnum;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${furion.token}")
    String furionToken;

    @Value("${furion.url}")
    String furionUrl;

    @Override
    public void sendEmail(UploadS3FinishedEmailDTO emailDTO) {
        try {
            HttpClient httpClient = HttpClientBuilder.create().build();

            URIBuilder uriBuilder = new URIBuilder(furionUrl);

            HttpPost postRequest = new HttpPost(uriBuilder.build());
            ObjectMapper objectMapper = new ObjectMapper();
            String payLoad = objectMapper.writeValueAsString(emailDTO);
            StringEntity entity = new StringEntity(payLoad, "UTF-8");
            postRequest.setEntity(entity);
            postRequest.setHeader("Content-Type", "application/json");
            postRequest.setHeader("Authorization", "Token token=" + furionToken);

            HttpResponse httpResponse = httpClient.execute(postRequest);

            if (httpResponse.getStatusLine().getStatusCode() == 422 || httpResponse.getStatusLine().getStatusCode() == 500) {
                log.info("Error from kelley system: " + "status: " + httpResponse.getStatusLine().getStatusCode() +
                        "message: " + EntityUtils.toString(httpResponse.getEntity()));
                throw new RuntimeException("Error in sending email process: " + EntityUtils.toString(httpResponse.getEntity()));
            }
        } catch (URISyntaxException e) {
            log.error("Error build url send email: " + e.toString());
            throw new RuntimeException("URISyntaxException: " + e);
        } catch (IOException e) {
            log.error("Error build url send email: " + e.toString());
            throw new RuntimeException("IOException: " + e);
        }
    }
}
