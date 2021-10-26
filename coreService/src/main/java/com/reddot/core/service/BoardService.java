package com.reddot.core.service;

import java.util.List;
import java.util.Optional;

import com.reddot.core.service.dto.BoardDTO;

/**
 * Service Interface for managing {@link com.reddot.core.domain.Board}.
 */
public interface BoardService {
    /**
     * Save a board.
     *
     * @param boardDTO the entity to save.
     * @return the persisted entity.
     */
    BoardDTO save(BoardDTO boardDTO);

    /**
     * Partially updates a board.
     *
     * @param boardDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BoardDTO> partialUpdate(BoardDTO boardDTO);

    /**
     * Get all the boards.
     *
     * @return the list of entities.
     */
    List<BoardDTO> findAll();

    /**
     * Get the "id" board.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoardDTO> findOne(Long id);

    /**
     * Delete the "id" board.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
