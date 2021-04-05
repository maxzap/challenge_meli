package com.meli.urlshortener.controller;

import com.meli.urlshortener.dto.ResponseDTO;
import com.meli.urlshortener.dto.ResponseEventDTO;
import com.meli.urlshortener.dto.UrlRequestDto;
import com.meli.urlshortener.dto.UrlResponseDto;
import com.meli.urlshortener.entity.UrlModel;
import com.meli.urlshortener.service.UrlService;
import com.meli.urlshortener.service.repository.EventRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ShortenUrlController {

    @Autowired
    UrlService urlService;

    @Autowired
    EventRepository eventRepository;

    @GetMapping("/url/acortar")
    public ResponseEntity<UrlResponseDto> handleLongUrl(@Valid @RequestBody UrlRequestDto urlRequestDto) throws URISyntaxException, NotFoundException, ExecutionException, InterruptedException {
        UrlResponseDto response = new UrlResponseDto();
        UrlModel alias = urlService.getAliasPath();

        response.setResponseUrl(alias.getShortUrl());
        urlService.saveUrl(alias, urlRequestDto.getUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/url/original")
    public ResponseEntity<?> handleShortUrl(@RequestBody @Valid UrlRequestDto urlRequestDto) throws URISyntaxException, NotFoundException, ExecutionException, InterruptedException {
        UrlResponseDto response = new UrlResponseDto();

        Optional<UrlModel> url = Optional.empty();
        try {
            url = urlService.findByShortUrl(urlRequestDto.getUrl());
        } catch (Exception e) {}
        if (url.isPresent()){
            response.setResponseUrl(url.get().getShortUrl());
        } else {
            return ResponseEntity.unprocessableEntity().body((new ResponseDTO("Url corta inexistente")));
        }

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/borrar/{id}")
    public ResponseEntity<ResponseDTO> deleteUrl(@PathVariable("id") Long idUrl) {
        if (urlService.deleteUrl(idUrl)){
            return ResponseEntity.ok(new ResponseDTO("Url eliminada correctamente"));
        } else {
            return new ResponseEntity<>(new ResponseDTO("Url inexistente"), NO_CONTENT);
        }
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> handleRedirect(@PathVariable("code") String urlCode) throws NotFoundException, URISyntaxException {
        Optional<UrlModel> url = urlService.findByCode(urlCode);
        return (url.isPresent()) ? new ResponseEntity<>(headers(url.get().getLongUrl()), MOVED_PERMANENTLY) :
                new ResponseEntity<>(OK);
    }

    @GetMapping("/listado_url")
    public ResponseEntity<List<UrlModel>> getUrlList() throws NotFoundException, URISyntaxException {
        List<UrlModel> urlList = urlService.getAll();
        return (!urlList.isEmpty()) ? new ResponseEntity<>(urlList, OK) : new ResponseEntity<>(NO_CONTENT);
    }

    @GetMapping("/eventos")
    public ResponseEntity<List<ResponseEventDTO>> getEventList(@RequestParam("fecha_desde") String dateFrom, @RequestParam("fecha_hasta") String dateTo) throws NotFoundException, URISyntaxException, ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        List<ResponseEventDTO> eventList = eventRepository.findEventRestModelsBetweenDates(
                LocalDateTime.parse(dateFrom, formatter),
                LocalDateTime.parse(dateTo, formatter)
        );
        return (!eventList.isEmpty()) ? new ResponseEntity<>(eventList, OK) : new ResponseEntity<>(NO_CONTENT);
    }


    private HttpHeaders headers(String url) throws URISyntaxException {
        URI uri = new URI(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return httpHeaders;
    }
}
