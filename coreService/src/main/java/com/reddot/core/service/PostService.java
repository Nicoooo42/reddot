package com.reddot.core.service;

import java.util.List;
import java.util.Optional;

import com.reddot.core.service.dto.PostDTO;

/**
 * Service Interface for managing {@link com.reddot.core.domain.Post}.
 */
public interface PostService {
    /**
     * Save a post.
     *
     * @param postDTO the entity to save.
     * @return the persisted entity.
     */
    PostDTO save(PostDTO postDTO);

    /**
     * Partially updates a post.
     *
     * @param postDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PostDTO> partialUpdate(PostDTO postDTO);

    /**
     * Get all the posts.
     *
     * @return the list of entities.
     */
    List<PostDTO> findAll();

    /**
     * Get the "id" post.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PostDTO> findOne(Long id);

    /**
     * Delete the "id" post.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find by UserName
     *
     * @param login
     */
	List<PostDTO> findByUserName(String login);
}
