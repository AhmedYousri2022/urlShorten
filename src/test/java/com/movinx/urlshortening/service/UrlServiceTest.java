package com.movinx.urlshortening.service;

import com.movinx.urlshortening.dto.RequestDto;
import com.movinx.urlshortening.dto.ResponseDto;
import com.movinx.urlshortening.exception.BadRequestException;
import com.movinx.urlshortening.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.movinx.urlshortening.service.UrlService.repo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @BeforeEach
    void cleanup() {
        repo.clear();
    }

    @InjectMocks
    private UrlService service;

    @Test
    void shouldEncodeUrl() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("https://www.amazon.com");

        ResponseDto encodeUrl = service.encode(requestDto);

        assertThat(encodeUrl.getUrl(), is("aHR0cHM6Ly93d3cuYW1hem9uLmNvbQ=="));
    }

    @Test
    void shouldThrowBadRequestException() {
        RequestDto requestDto = new RequestDto();
        requestDto.setUrl("www.amazon.com");
        Exception exception = assertThrows(
                BadRequestException.class,
                () -> service.encode(requestDto));

        assertThat(exception.getMessage(), is("Invalid URL"));
    }

    @Test
    void shouldDecodeUrl() {

        repo.put("aHR0cHM6Ly93d3cuYW1hem9uLmNvbQ==", "https://www.amazon.com");

        ResponseDto decodedUrl = service.decode("aHR0cHM6Ly93d3cuYW1hem9uLmNvbQ==");

        assertThat(decodedUrl.getUrl(), is(repo.get("aHR0cHM6Ly93d3cuYW1hem9uLmNvbQ==")));
    }

    @Test
    void shouldThrowNotFoundRequestException() {
        Exception exception = assertThrows(
                NotFoundException.class,
                () -> service.decode("aHR0cHM6Ly93d3cuYW1hem9uLmNvbQ=="));

        assertThat(exception.getMessage(), is("Short URL not found"));
    }
}
