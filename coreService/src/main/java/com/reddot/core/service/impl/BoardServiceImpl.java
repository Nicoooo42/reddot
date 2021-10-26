package com.reddot.core.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddot.core.domain.Board;
import com.reddot.core.repository.BoardRepository;
import com.reddot.core.service.BoardService;
import com.reddot.core.service.dto.BoardDTO;
import com.reddot.core.service.mapper.BoardMapper;

/**
 * Service Implementation for managing {@link Board}.
 */
@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final Logger log = LoggerFactory.getLogger(BoardServiceImpl.class);

    private final BoardRepository boardRepository;

    private final BoardMapper boardMapper;

    public BoardServiceImpl(BoardRepository boardRepository, BoardMapper boardMapper) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
    }

    @Override
    public BoardDTO save(BoardDTO boardDTO) {
        log.debug("Request to save Board : {}", boardDTO);
        Board board = boardMapper.toEntity(boardDTO);
        board = boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    @Override
    public Optional<BoardDTO> partialUpdate(BoardDTO boardDTO) {
        log.debug("Request to partially update Board : {}", boardDTO);

        return boardRepository
            .findById(boardDTO.getId())
            .map(existingBoard -> {
                boardMapper.partialUpdate(existingBoard, boardDTO);

                return existingBoard;
            })
            .map(boardRepository::save)
            .map(boardMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoardDTO> findAll() {
        log.debug("Request to get all Boards");
        return boardRepository.findAll().stream().map(boardMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BoardDTO> findOne(Long id) {
        log.debug("Request to get Board : {}", id);
        return boardRepository.findById(id).map(boardMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Board : {}", id);
        boardRepository.deleteById(id);
    }
}
