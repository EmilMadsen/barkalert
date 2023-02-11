package dk.emilmadsen.barkalert.service;

import com.google.gson.Gson;
import dk.emilmadsen.barkalert.model.discord.RequestObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.io.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscordService {

    @Value("${discord.webhook.url}")
    private String webhookUrl;

    private final RestTemplate restTemplate;
    private final Gson gson;

    public void sendRequest(String message) {
        RequestObject request = buildPayload(message);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(gson.toJson(request), headers);
        restTemplate.postForObject(webhookUrl, entity, String.class);

    }

    public void sendFileRequest(File file1, File file2, String message) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("file1", new MultipartInputStreamFileResource(new FileInputStream(file1), file1.getName()));
            body.add("file2", new MultipartInputStreamFileResource(new FileInputStream(file2), file2.getName()));
            body.add("payload_json", gson.toJson(buildPayload(message)));
            HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
            restTemplate.postForObject(webhookUrl, entity, String.class);
        } catch (FileNotFoundException e) {
            log.error("file not found: {}", e.getMessage(), e);
        }
    }

    private RequestObject buildPayload(String message) {

        RequestObject request = new RequestObject();
        if (ObjectUtils.isEmpty(message)) {
            throw new IllegalArgumentException("Empty message");
        }

        request.setContent(message);
        request.setTts(true);
        request.setUsername("Peter Barker");

        return request;
    }

    private static class MultipartInputStreamFileResource extends InputStreamResource {

        private final String filename;

        MultipartInputStreamFileResource(InputStream inputStream, String filename) {
            super(inputStream);
            this.filename = filename;
        }

        @Override
        public String getFilename() {
            return this.filename;
        }

        @Override
        public long contentLength() {
            return -1;
        }
    }
}
