package com.neueda.shortenedurl.controller;

import java.net.URI;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import com.neueda.shortenedurl.model.StatisticEntity;
import com.neueda.shortenedurl.model.UrlEntity;
import com.neueda.shortenedurl.services.StatisticService;
import com.neueda.shortenedurl.services.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/url")
public class UrlController {
    Logger logger = LoggerFactory.getLogger(UrlController.class);

    private static Pattern URL = Pattern.compile("https?://[^\\s]+");
    @Autowired
    private UrlService service;

    @Autowired
    private StatisticService statisticService;

    @PostMapping(value = "/shortener")
    public void create(@RequestParam("url") String url) throws Exception {
        logger.info("Received url to shorten: " + url);
        if (!URL.matcher(url).matches()) {
            ResponseEntity.badRequest().body("invalid url");
        }
        String code = service.save(url);
        //need to check this /code
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(code).toUri();
        ResponseEntity.created(uri).build();
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<UrlEntity> findAndRedirect(@PathVariable String code,
                                               @RequestHeader Map<String, String> headersMap, HttpServletRequest request) {
        logger.info("Redirecting code to URL ", code);

        UrlEntity url = service.find(code);

        String user = request.getUserPrincipal().getName();
        String userDetails = statisticService.getUserAgent(request);
        System.out.println("print user " + user + " $$userdetails " + userDetails);
		StatisticEntity urlstats = statisticService.buildUrlStatistics(user, userDetails, url);
        statisticService.create(urlstats);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getLongUrl()));

        return new ResponseEntity<UrlEntity>(headers, HttpStatus.OK);
    }
}