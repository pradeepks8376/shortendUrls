package com.neueda.shortenedurl.services;

import com.neueda.shortenedurl.dao.UrlRepository;
import com.neueda.shortenedurl.model.UrlEntity;
import com.neueda.littleurl.services.exceptions.UrlNotFoundException;
import com.neueda.shortenedurl.utils.Constants;
import com.neueda.shortenedurl.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlService {

	Logger logger = LoggerFactory.getLogger(UrlService.class);

	@Autowired
	private UrlRepository repository;

	public UrlEntity find(String urlCode) {
		Optional<UrlEntity> optional = repository.findById(urlCode);
		return optional.orElseThrow(() -> new UrlNotFoundException(Constants.URL_NOT_FOUND_FOR_CODE + urlCode));
	}
	public String save(String longUrl) {
		logger.info("Inside UrlService.save", longUrl);
		int startIndex = 0;
		int endIndex = startIndex + Constants.URL_CODE_SIZE - 1;
		String code = UrlUtils.generateShortURL(longUrl, startIndex, endIndex);
		repository.save(new UrlEntity(code, longUrl));
		return code;
	}

}
