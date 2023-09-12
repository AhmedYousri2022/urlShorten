package com.movinx.urlshortening.service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.movinx.urlshortening.dto.RequestDto;
import com.movinx.urlshortening.dto.ResponseDto;
import com.movinx.urlshortening.exception.BadRequestException;
import com.movinx.urlshortening.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.movinx.urlshortening.validation.Validation.isValidUrl;

@Slf4j
@Service
@RequiredArgsConstructor
public class UrlService {

    static Map<String, String> repo = new HashMap<>();

    public ResponseDto encode(RequestDto requestDto) {
        if (requestDto == null || !isValidUrl(requestDto.getUrl()) || !StringUtils.hasText(requestDto.getUrl())) {
            throw new BadRequestException("Invalid URL");
        }
        String shortenUrl = Base64.getUrlEncoder().encodeToString(requestDto.getUrl().getBytes());

        if (repo.containsKey(shortenUrl)) {
            return new ResponseDto(shortenUrl);
        }
        repo.put(shortenUrl, requestDto.getUrl());
        return new ResponseDto(shortenUrl);
    }

    public ResponseDto decode(String url) {
        if (!StringUtils.hasText(url)) {
            throw new BadRequestException("Invalid URL");
        }

        if (repo.containsKey(url)) {
            return new ResponseDto(repo.get(url));
        } else {
            log.error("could not found URl for {}", url);
            throw new NotFoundException("Short URL not found");
        }
    }
}
