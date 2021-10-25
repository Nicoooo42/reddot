package com.reddot.stats.service;

import com.reddot.stats.pojo.PostInfo;
import com.reddot.stats.service.dto.StatisticsPostDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.reddot.stats.domain.StatisticsPost}.
 */
public interface StatisticsPostService {
    /**
     * Save a statisticsPost.
     *
     * @param statisticsPostDTO the entity to save.
     * @return the persisted entity.
     */
    StatisticsPostDTO save(StatisticsPostDTO statisticsPostDTO);

    /**
     * Partially updates a statisticsPost.
     *
     * @param statisticsPostDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<StatisticsPostDTO> partialUpdate(StatisticsPostDTO statisticsPostDTO);

    /**
     * Get all the statisticsPosts.
     *
     * @return the list of entities.
     */
    List<StatisticsPostDTO> findAll();

    /**
     * Get the "id" statisticsPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<StatisticsPostDTO> findOne(Long id);

    /**
     * Delete the "id" statisticsPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Get statisics create post by user and date
     *
     */
	List<PostInfo> stasticsByUserAndDate();
}
