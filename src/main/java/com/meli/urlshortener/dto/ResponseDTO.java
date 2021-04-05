package com.meli.urlshortener.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ResponseDTO {

    @NonNull
    String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    Object data;

}