package com.movinx.urlshortening.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.movinx.urlshortening.dto.RequestDto;
import com.movinx.urlshortening.service.UrlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlController.class)
class UrlControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UrlService service;

    @Test
    void shouldEncodeUrl() throws Exception {
        RequestDto dto = new RequestDto();
        dto.setUrl("www.google.com");

        mvc.perform(post("/urls/encode")
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(dto)))

                .andExpect(status().isCreated());
    }

    @Test
    void shouldDecodeUrl() throws Exception {
        String shorten = "ewogICAgInVybCI6ICJ3d3cuYW1hem9uZy5jb20iCn0=";
        mvc.perform(get("/urls/decode/" + shorten)
                            .accept(APPLICATION_JSON)
                            .contentType(APPLICATION_JSON))

                .andExpect(status().isOk());
    }
}
