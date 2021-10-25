package com.reddot.stats.repository;

import com.reddot.stats.domain.StatisticsPost;
import com.reddot.stats.pojo.PostInfo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the StatisticsPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatisticsPostRepository extends JpaRepository<StatisticsPost, Long> {
	
	@Query("SELECT new com.reddot.stats.pojo.PostInfo(user, year, month, day, count(s.user)) FROM StatisticsPost AS s GROUP BY s.user, s.month, s.day, s.year")
    List<PostInfo> findAllforTest();
}
