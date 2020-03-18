package com.neueda.shortenedurl.controller;

import com.neueda.shortenedurl.model.UrlStatisticsResponse;
import com.neueda.shortenedurl.services.statistic.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/urlstats")
public class UrlStatisticsController {
	Logger logger = LoggerFactory.getLogger(UrlStatisticsController.class);

	@Autowired
	private StatisticService service;

	@GetMapping(path = "/summary")
	public ResponseEntity<UrlStatisticsResponse> getSummary() {
		logger.info("Entered url statistics data ");

		UrlStatisticsResponse summary = service.getStatisticsSummary();

		return ResponseEntity.ok().body(summary);
	}
}