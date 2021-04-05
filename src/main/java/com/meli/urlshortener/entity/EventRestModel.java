package com.meli.urlshortener.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "event_api")
public class EventRestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "date")
    LocalDateTime date;

    @Column(name = "http_method")
    @NotNull
    String method;

    @Column(name = "uri")
    @NotNull
    String uri;

    @Lob
    @Column(name = "request")
    @NotNull
    String requestPayload;

    @Column(name = "request_code")
    @NotNull
    String statusCode;

    @Column(name = "response")
    @NotNull
    String responsePayload;
}
