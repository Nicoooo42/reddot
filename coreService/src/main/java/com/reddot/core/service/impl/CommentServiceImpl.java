package com.reddot.core.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddot.core.domain.Comment;
import com.reddot.core.repository.CommentRepository;
import com.reddot.core.service.CommentService;
import com.reddot.core.service.dto.CommentDTO;
import com.reddot.core.service.mapper.CommentMapper;

import io.undertow.util.BadRequestException;

/**
 * Service Implementation for managing {@link Comment}.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) throws BadRequestException {
        log.debug("Request to save Comment : {}", commentDTO);
        Comment comment = commentMapper.toEntity(commentDTO);
        
        boolean test = isDeepCommentOk(comment, 0);
        
        if(!isDeepCommentOk(comment, 0)) {
        	throw new BadRequestException("Only 10 level of comment are accepted");
        }
        comment = commentRepository.save(comment);
        return commentMapper.toDto(comment);
    }

    @Override
    public Optional<CommentDTO> partialUpdate(CommentDTO commentDTO) {
        log.debug("Request to partially update Comment : {}", commentDTO);

        return commentRepository
            .findById(commentDTO.getId())
            .map(existingComment -> {
                commentMapper.partialUpdate(existingComment, commentDTO);

                return existingComment;
            })
            .map(commentRepository::save)
            .map(commentMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDTO> findAll() {
        log.debug("Request to get all Comments");
        return commentRepository.findAll().stream().map(commentMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CommentDTO> findOne(Long id) {
        log.debug("Request to get Comment : {}", id);
        return commentRepository.findById(id).map(commentMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Comment : {}", id);
        commentRepository.deleteById(id);
    }
    
    private boolean isDeepCommentOk(Comment comment, int level) {
    	if (comment.getComment() == null && level < 10) {
    		return true;
    	}else if (comment.getComment() != null && level < 10) {
    		return isDeepCommentOk(comment.getComment(), level +1);
    	}
    	return false;
    }
}
