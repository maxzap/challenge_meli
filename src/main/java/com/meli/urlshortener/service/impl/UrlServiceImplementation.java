package com.meli.urlshortener.service.impl;

import com.meli.urlshortener.entity.UrlModel;
import com.meli.urlshortener.service.UrlService;
import com.meli.urlshortener.service.repository.UrlRepository;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class UrlServiceImplementation implements UrlService {
    @Autowired
    UrlRepository urlRepository;

    private String DOMAIN = "https://me.li:8443/";

    @Override
    public Optional<UrlModel> findByLongUrl(String longUrl) {
        Optional<UrlModel> url = urlRepository.findByLongUrl(longUrl);
        return url;
    }

    @Override
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<UrlModel> saveUrl(UrlModel urlEntity, String longUrl) {
        urlEntity.setLongUrl(longUrl);
        UrlModel url = urlRepository.save(urlEntity);
        return CompletableFuture.completedFuture(url);
    }


    @Override
    public Optional<UrlModel> findByShortUrl(String shortUrl) throws NotFoundException {
        Optional<UrlModel> url = Optional.ofNullable(urlRepository
                .findByShortUrl(shortUrl)
                .orElseThrow(() -> new NotFoundException("Url No encontrada")));
        return url;
    }

    @Override
    public boolean deleteUrl(Long idUrl) {
        try {
            urlRepository.deleteById(idUrl);
            return true;
        } catch (Exception e) {}
        return false;
    }

    @Override
    public Optional<UrlModel> findByCode(String code) throws NotFoundException {
        Optional<UrlModel> url = Optional.ofNullable(urlRepository
                .findByCode(code)
                .orElseThrow(() -> new NotFoundException("Url No encontrada")));
        return url;
    }

    @Override
    public List<UrlModel> getAll() {
        List<UrlModel> response =  new ArrayList<>();
        urlRepository.findAll().iterator().forEachRemaining(response::add);

        return response;
    }

    @Override
    public UrlModel getAliasPath() {
        UrlModel url = new UrlModel();
        int leftLimit = 65; // letter 'A'
        int rightLimit = 90; // letter 'Z'
        int targetStringLength = 6;
        Random random = new Random();

        String code = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        url.setShortUrl(DOMAIN + code);
        url.setCode(code);

        return url;

    }

}
