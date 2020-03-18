package com.neueda.shortenedurl.dao.url;

import com.neueda.shortenedurl.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, String>{

}
