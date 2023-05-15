package ru.vsu.cs.musiczoneserver.service;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class CloudService {
    public void main(String fileName) throws IOException {
        String url = "https://storage.yandexcloud.net/musik/" + fileName + ".mp3";

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);

        HttpResponse response;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputStream stream;
        try {
            stream = response.getEntity().getContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = stream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        try (FileOutputStream fos = new FileOutputStream(
                new File("src\\main\\resources\\music\\" + fileName + ".mp3").getAbsolutePath())) {
            fos.write(buffer.toByteArray());
            fos.close();
        }
    }
}
