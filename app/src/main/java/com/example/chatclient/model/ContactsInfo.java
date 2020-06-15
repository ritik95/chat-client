package com.example.chatclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContactsInfo {
    private String contactId;
    private String displayName;
    private String phoneNumber;
}
