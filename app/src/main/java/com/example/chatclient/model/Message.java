package com.example.chatclient.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Message {

    private String flag;
    private String message;

    @JsonProperty("sender")
    private User sender;

    private long createdAt;
    private int onlineCount;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User{
        @JsonProperty("name")
        private String name;

        @JsonProperty("profileUrl")
        private String profileUrl;
    }
}
