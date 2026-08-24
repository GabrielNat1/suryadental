package br.com.suryadental.application.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document
public class Client {
    @Id
    private UUID id;

    private String name;
    private String lastName;
    private String address;
    private String phone;


}
