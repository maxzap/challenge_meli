package com.meli.urlshortener.service;

import com.meli.urlshortener.entity.UrlModel;
import javassist.NotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UrlService {
    Optional<UrlModel> findByLongUrl(String longUrl);

    CompletableFuture<UrlModel> saveUrl(UrlModel urlEntity, String longUrl);

    Optional<UrlModel> findByShortUrl(String longUrl) throws NotFoundException;

    boolean deleteUrl(Long idUrl);

    Optional<UrlModel> findByCode(String code) throws NotFoundException;

    List<UrlModel> getAll();

    UrlModel getAliasPath();

}
