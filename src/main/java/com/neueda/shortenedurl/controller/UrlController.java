package com.neueda.shortenedurl.controller;

import java.net.URI;
import java.security.Security;
import java.security.Principal;
import java.util.Map;
import java.util.regex.Pattern;

import com.neueda.shortenedurl.model.StatisticEntity;
import com.neueda.shortenedurl.model.UrlEntity;
import com.neueda.shortenedurl.services.StatisticService;
import com.neueda.shortenedurl.services.UrlService;
import com.neueda.shortenedurl.utils.GsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<String> create(@RequestParam("url") String url) throws Exception {
        logger.info("Received url to shorten: " + url);
        if (!URL.matcher(url).matches()) {
            ResponseEntity.badRequest().body("invalid url");
        }
       // String code = service.save(url);
        //need to check this /code
        //URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(code).toUri();
      //  ResponseEntity.created(uri).build();

        return ResponseEntity.ok(GsonUtils.DEFAULT_GSON.toJson(service.save(url)));
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<UrlEntity> findAndRedirect(@PathVariable String code, HttpServletRequest request) {
        logger.info("Redirecting code to URL ", code);
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();
        UrlEntity url = service.find(code);
      //  String user = "ksp";
    //    String userDetails = statisticService.getUserAgent(request);
        String userDetails = "tester";
                System.out.println("print user " + userName);
		StatisticEntity urlstats = statisticService.buildUrlStatistics(userName, userDetails, url);
        statisticService.create(urlstats);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(url.getLongUrl()));

        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}