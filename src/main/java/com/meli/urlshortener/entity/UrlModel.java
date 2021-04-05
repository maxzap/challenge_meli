package com.meli.urlshortener.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "urls")
public class UrlModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name = "short_url")
    @NotNull
    String shortUrl;

    @Column(nullable = false, name = "long_url")
    @NotNull
    String longUrl;

    @Column(nullable = false, name = "code")
    @NotNull
    String code;

}




