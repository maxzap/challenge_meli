package com.meli.urlshortener.service.repository;

import com.meli.urlshortener.entity.UrlModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UrlRepository extends CrudRepository<UrlModel, Long> {

    Optional<UrlModel> findByLongUrl(String longUrl);

    Optional<UrlModel> findByShortUrl(String shortUrl);

    Optional<UrlModel> findByCode(String code);
}
