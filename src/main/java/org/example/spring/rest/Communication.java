package org.example.spring.rest;

import org.example.spring.rest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class Communication {

    @Autowired
    private RestTemplate restTemplate;

    private String sessionId;

    private final String URL = "http://94.198.50.185:7081/api/users";

    public String getAllUsersAndSaveSessionId(){

        ResponseEntity<List<User>> responseEntity =
                restTemplate.exchange(URL, HttpMethod.GET, null
                        , new ParameterizedTypeReference<List<User>>() {});
        List<User>allUsers = responseEntity.getBody();
        HttpHeaders headers = responseEntity.getHeaders();
        List<String> cookies = headers.get("Set-Cookie");
        if (cookies != null && !cookies.isEmpty()) {
            this.sessionId = cookies.get(0).split(";")[0];
        } else {
            throw new RuntimeException("Session ID not found");
        }
        return sessionId;
    }

    public String saveUser (User user){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);

        ResponseEntity<String> responseEntity=
                restTemplate.postForEntity(URL,request,String.class);
        return responseEntity.getBody();
    }
    public String updateUser(User user){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<User> request = new HttpEntity<User>(user, headers);

        String putUrl = URL + "/";
        ResponseEntity<String> response =
                restTemplate.exchange(putUrl, HttpMethod.PUT, request, String.class);
        return response.getBody();

    }
    public String deleteUser(Long id){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);
        HttpEntity<Void> requestEntity = new HttpEntity<Void>(null, headers);

        String deleteUrl = URL + "/" + id;
        ResponseEntity<String> response =
                restTemplate.exchange(deleteUrl, HttpMethod.DELETE, requestEntity, String.class);
        return response.getBody();
    }
}
