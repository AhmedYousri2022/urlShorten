package com.movinx.urlshortening.controller;

import com.movinx.urlshortening.dto.RequestDto;
import com.movinx.urlshortening.dto.ResponseDto;
import com.movinx.urlshortening.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/urls")
public class UrlController {

    private final UrlService service;

    @PostMapping(path = "/encode", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDto encode(@RequestBody RequestDto requestDto) {
        return service.encode(requestDto);
    }

    @GetMapping(path = "/decode/{url}", produces = "application/json")
    public ResponseDto decode(@PathVariable String url) {
        return service.decode(url);
    }
}
