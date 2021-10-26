package com.reddot.core.service;

import java.util.List;
import java.util.Optional;

import com.reddot.core.service.dto.ThreadDTO;

/**
 * Service Interface for managing {@link com.reddot.core.domain.Thread}.
 */
public interface ThreadService {
    /**
     * Save a thread.
     *
     * @param threadDTO the entity to save.
     * @return the persisted entity.
     */
    ThreadDTO save(ThreadDTO threadDTO);

    /**
     * Partially updates a thread.
     *
     * @param threadDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ThreadDTO> partialUpdate(ThreadDTO threadDTO);

    /**
     * Get all the threads.
     *
     * @return the list of entities.
     */
    List<ThreadDTO> findAll();

    /**
     * Get the "id" thread.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ThreadDTO> findOne(Long id);

    /**
     * Delete the "id" thread.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
