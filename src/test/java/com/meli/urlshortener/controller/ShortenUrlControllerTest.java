package com.meli.urlshortener.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meli.urlshortener.dto.UrlRequestDto;
import com.meli.urlshortener.dto.UrlResponseDto;
import com.meli.urlshortener.entity.UrlModel;
import com.meli.urlshortener.service.UrlService;
import com.meli.urlshortener.service.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ShortenUrlControllerTest {

    @MockBean
    UrlService urlService;

    @MockBean
    EventRepository eventRepository;

    @Autowired
    MockMvc mvc;

    @Test
    public void getShortUrlTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        UrlRequestDto request = new UrlRequestDto();
        request.setUrl("https://articulo.mercadolibre.com.ar/MLA-854852461-correa-para-rodillo-de-entrenamiento-envio-gratis-_JM#reco_item_pos=19&reco_backend=machinalis-v2p-pdp-boost-v2&reco_backend_type=low_level&reco_client=vip-v2p&reco_id=20cd90d6-c99f-4ba9-ac9b-08cd853d90f9");

        UrlModel url = new UrlModel();
        url.setShortUrl("https://me.li:8443/WWWYYY");
        url.setCode("WWWYYY");

        UrlResponseDto response = new UrlResponseDto();
        response.setResponseUrl(url.getShortUrl());

        String requestString = mapper.writeValueAsString(request);
        String resultString = mapper.writeValueAsString(response);

        when(urlService.getAliasPath()).thenReturn(url);

        mvc.perform(
                get("/url/acortar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestString)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(resultString));
    }

}
