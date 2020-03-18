package com.neueda.shortenedurl.dao.statistic;

import java.util.List;

import com.neueda.shortenedurl.model.StatisticEntity;
import com.neueda.shortenedurl.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface StatisticRepository extends JpaRepository<StatisticEntity, Long> {
    @Query("select count(s.id) from StatisticEntity s")
	Long getNumberOfHits();
	
    @Query("select count(s.id) from StatisticEntity s where s.url.code = :code")
	Long getNumberOfHitsByCode(@Param("code") String code);
	
    @Query("select new com.neueda.shortenedurl.model.Statistics(s.userName, count(s)) from StatisticEntity s group by s.userName")
    List<Statistics> getUserName();

}
