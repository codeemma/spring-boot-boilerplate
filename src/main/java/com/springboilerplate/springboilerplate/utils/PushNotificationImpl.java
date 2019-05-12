package com.springboilerplate.springboilerplate.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Component
public class PushNotificationImpl implements PushNotification {

    private Logger logger = LoggerFactory.getLogger(PushNotification.class);
    private RestOperations restOperations;
    private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/v1/projects//messages:send";

    public PushNotificationImpl(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public void send(String text) throws IOException {
        FirebaseNotification firebaseNotification = new FirebaseNotification();
        Notification notification = new Notification();

        notification.setBody(text);
        notification.setTitle("Driver Alert");

        Message message = new Message();
        message.setNotification(notification);
        message.setTopic("notify");

        firebaseNotification.setMessage(message);

        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Authorization", "Bearer " + getAccessToken());
        String notificationString = null;
        try {
            notificationString = new ObjectMapper().writeValueAsString(firebaseNotification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        logger.info("------" + notificationString);
        HttpEntity<String> request = new HttpEntity<>(notificationString, headers);
        restOperations.exchange(FIREBASE_API_URL, HttpMethod.POST, request, FirebaseNotificationResponse.class);
    }

    private String getAccessToken() throws IOException {
        InputStream inputStream = PushNotificationImpl.class.getResourceAsStream("/service-account.json");
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(inputStream)
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        return googleCredential.getAccessToken();
    }
}
