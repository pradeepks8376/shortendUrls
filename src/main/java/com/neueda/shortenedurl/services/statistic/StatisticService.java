package com.neueda.shortenedurl.services.statistic;

import com.neueda.shortenedurl.dao.statistic.StatisticRepository;
import com.neueda.shortenedurl.model.StatisticEntity;
import com.neueda.shortenedurl.model.UrlEntity;
import com.neueda.shortenedurl.model.UrlStatisticsResponse;
import com.neueda.shortenedurl.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;

@Service
public class StatisticService {

    Logger logger = LoggerFactory.getLogger(StatisticService.class);

    @Autowired
    private StatisticRepository repository;

    public StatisticEntity create(StatisticEntity statistic) {
        statistic.setId(null);
        return repository.save(statistic);
    }

    public String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }

    public UrlStatisticsResponse getStatisticsSummary() {
        logger.info(Constants.GETTING_STATISTICS_SUMMARY);

        UrlStatisticsResponse urlStatsResponse = new UrlStatisticsResponse();
        urlStatsResponse.setNumberOfHits(repository.getNumberOfHits());
        urlStatsResponse.setUserDetails(repository.getUserName());
        return urlStatsResponse;
    }

	public StatisticEntity buildUrlStatistics(String user, UrlEntity url) {

		return new StatisticEntity(user,url);
	}
}
