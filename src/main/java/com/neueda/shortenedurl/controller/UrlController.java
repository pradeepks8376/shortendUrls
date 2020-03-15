package com.neueda.shortenedurl.resources;

import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.neueda.shortenedurl.model.UrlRequest;
import com.neueda.shortenedurl.services.StatisticService;
import com.neueda.shortenedurl.services.UrlService;
import com.neueda.shortenedurl.util.Constants;
import com.neueda.shortenedurl.vo.Statistic;
import com.neueda.shortenedurl.vo.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/url")
public class UrlController {
	Logger logger = LoggerFactory.getLogger(UrlResources.class);

	@Autowired
	private UrlService service;

	@Autowired
	private StatisticService statisticService;

	@PostMapping(value = "/shortener")
	public ResponseEntity<Url> findOrCreate(@Valid @RequestBody UrlRequest request) {
		logger.info("Received url to shorten: " + request.getLongUrl());

		Url url = service.fromDTO(request);
		url = service.findOrCreate(url);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{code}").buildAndExpand(url.getCode())
				.toUri();

		return ResponseEntity.created(uri).build();
	}

	@GetMapping(path = "/{code}")
	public ResponseEntity<Url> findAndRedirect(@PathVariable String code,
			@RequestHeader Map<String, String> headersMap) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.FINDING_URL_FOR_REDIRECTING, code);

		Url url = service.find(code);

		Statistic statistic = statisticService.mapFrom(headersMap, url);
		statisticService.create(statistic);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(URI.create(url.getLongUrl()));

		return new ResponseEntity<Url>(headers, HttpStatus.MOVED_PERMANENTLY);
	}

	@GetMapping(path = "/{code}/longUrl")
	public ResponseEntity<Url> find(@PathVariable String code) {

		code = code.replaceAll(Constants.PATTERN_BREAKING_CHARACTERS, "_");

		logger.info(Constants.FINDING_LONG_URL, code);

		ResponseEntity<Url> responseEntity;

		Url url = service.find(code);
		responseEntity = ResponseEntity.ok().body(url);

		return responseEntity;
	}

}