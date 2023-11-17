package com.example.maintenance;

import com.example.maintenance.controller.AdministratorController;
import com.example.maintenance.dto.AuthRequest;
import com.example.maintenance.entity.Administrator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest(classes = MaintenanceApplication.class)
class MaintenanceTests {
    public final HttpClient client = HttpClientBuilder.create().build();
    private final String BASE_URL = "http://localhost:8081/admin";
    private final String user = "user1";
    private final String password = "password";

    @Autowired
    AdministratorController administratorController;

    @Test
    @Priority(1)
    public void saveTest() throws IOException {
        HttpPost request = new HttpPost(this.BASE_URL + "/new");
        String jsonBody = "{\"name\": \"user\", \"password\": \"password\", \"roles\": \"ROLE_ADMIN\"}";

        request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        HttpResponse response = client.execute(request);
        String resultContent = getResultContent(response);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(resultContent, JsonObject.class);
        int id = jsonObject.get("id").getAsInt();
        System.out.println("ID: " + id);
        System.out.println("Response Content : " + resultContent);
    }

    @Test
    @Priority(2)
    public void deleteTest() throws IOException {
        int id = getId();

        AuthRequest authRequest = new AuthRequest(user, password);
        String token = administratorController.authenticateAndGetToken(authRequest);
        System.out.println("id: " + id);
        HttpDelete request = new HttpDelete(this.BASE_URL + "/" + id);
        request.addHeader("Authorization", "Bearer " + token);
        HttpResponse response = client.execute(request);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());
        String resultContent = getResultContent(response);
        boolean deleteResponse = Boolean.parseBoolean(resultContent);
        System.out.println("Response Content : " + resultContent);

        Assert.assertEquals(true, deleteResponse);
    }

    private int getId() throws IOException {
        HttpPost request = new HttpPost(this.BASE_URL + "/new");
        String jsonBody = "{\"name\": \"user1\", \"password\": \"password\", \"roles\": \"ROLE_ADMIN\"}";

        request.setEntity(new StringEntity(jsonBody, ContentType.APPLICATION_JSON));

        HttpResponse response = client.execute(request);
        String resultContent = getResultContent(response);

        System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(resultContent, JsonObject.class);
        int id = jsonObject.get("id").getAsInt();
        return id;
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

}