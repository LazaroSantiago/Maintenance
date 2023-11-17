package com.example.maintenance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Priority;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

/** Todo
 * Antes de testear se recomienda comentar el
 * "@GeneratedValue(strategy = GenerationType.IDENTITY)" ubicado en la clase Administrator
 * y que el id a usar no este en uso
 */

@SpringBootTest(classes = MaintenanceApplication.class)
class MaintenanceTests {

    public final HttpClient client =  HttpClientBuilder.create().build();

    private final String BASE_URL = "http://localhost:8081/admin";
    private final int id = 1;

    @Test
    @Priority(1)
    public void saveTest() throws IOException {
        int initialCount = countElementsInJson(this.findAll());

        HttpPost request = new HttpPost(this.BASE_URL);

        String jsonBody = "{\n" +
                "  \"id\": "+ id +",\n" +
                "  \"name\": \"Nombre del Administrador\"\n" +
                "}";

        request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        String resultContent = getResultContent(response);
        System.out.println("Response Content : " + resultContent);

        int finalCount = countElementsInJson(this.findAll());

        Assert.assertEquals(initialCount + 1, finalCount);
    }

    @Test
    @Priority(2)
    public void deleteTest() throws IOException {
        int initialCount = countElementsInJson(this.findAll());

        HttpDelete request = new HttpDelete(this.BASE_URL + "/" + id);

        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        String resultContent = getResultContent(response);
        System.out.println("Response Content : " + resultContent);

        int finalCount = countElementsInJson(this.findAll());

        Assert.assertEquals(initialCount - 1, finalCount);
    }

    private String getResultContent(HttpResponse response) throws IOException {
        // Verificar si la respuesta es exitosa (código 200)
        if (response.getStatusLine().getStatusCode() == 200) {
            // Obtener el contenido de la respuesta como una cadena
            return EntityUtils.toString(response.getEntity());
        } else {
            throw new RuntimeException("Error en la solicitud. Código de respuesta: " + response.getStatusLine().getStatusCode());
        }
    }

    private String findAll() throws IOException {
        HttpGet request = new HttpGet(this.BASE_URL);

        HttpResponse response = client.execute(request);

        return getResultContent(response);
    }

    private int countElementsInJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonString);

            if (jsonNode.isArray()) {
                return jsonNode.size();
            } else {
                return -1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}