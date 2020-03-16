package com.neueda.shortenedurl.model;

import java.io.Serializable;
import java.util.List;

public class UrlStatisticsResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long numberOfHits;
	private List<Statistics> userName;
	private List<Statistics> userDetails;

	public List<Statistics> getUserName() {
		return userName;
	}

	public void setUserName(List<Statistics> userName) {
		this.userName = userName;
	}

	public List<Statistics> getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(List<Statistics> userDetails) {
		this.userDetails = userDetails;
	}

	public Long getNumberOfHits() {
		return numberOfHits;
	}

	public void setNumberOfHits(Long numberOfHits) {
		this.numberOfHits = numberOfHits;
	}
}
