package com.neueda.shortenedurl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
public class UrlEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String code;

	private String longUrl;
	
	@CreationTimestamp
	private Date createdAt;
	
	@UpdateTimestamp
	private Date updatedAt;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="url")
	private List<StatisticEntity> statistics = new ArrayList<>();
		
	public UrlEntity() {
		
	}
	
	public UrlEntity(String code, String longUrl) {
		super();
		this.code = code;
		this.longUrl = longUrl;
	}
	
	@PrePersist
    protected void prePersist() {
        if (this.createdAt == null) createdAt = new Date();
        if (this.updatedAt == null) updatedAt = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        this.updatedAt = new Date();
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}		

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}	

	public List<StatisticEntity> getStatistics() {
		return statistics;
	}

	public void setStatistics(List<StatisticEntity> statistics) {
		this.statistics = statistics;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((longUrl == null) ? 0 : longUrl.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		UrlEntity other = (UrlEntity) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		if (longUrl == null) {
			if (other.longUrl != null) {
				return false;
			}
		} else if (!longUrl.equals(other.longUrl)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Url [code=" + code + ", longUrl=" + longUrl + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt
				+ "]";
	}
	
}
