package com.reddot.core.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reddot.core.domain.Thread;
import com.reddot.core.repository.ThreadRepository;
import com.reddot.core.service.ThreadService;
import com.reddot.core.service.dto.ThreadDTO;
import com.reddot.core.service.mapper.ThreadMapper;

/**
 * Service Implementation for managing {@link Thread}.
 */
@Service
@Transactional
public class ThreadServiceImpl implements ThreadService {

    private final Logger log = LoggerFactory.getLogger(ThreadServiceImpl.class);

    private final ThreadRepository threadRepository;

    private final ThreadMapper threadMapper;

    public ThreadServiceImpl(ThreadRepository threadRepository, ThreadMapper threadMapper) {
        this.threadRepository = threadRepository;
        this.threadMapper = threadMapper;
    }

    @Override
    public ThreadDTO save(ThreadDTO threadDTO) {
        log.debug("Request to save Thread : {}", threadDTO);
        Thread thread = threadMapper.toEntity(threadDTO);
        thread = threadRepository.save(thread);
        return threadMapper.toDto(thread);
    }

    @Override
    public Optional<ThreadDTO> partialUpdate(ThreadDTO threadDTO) {
        log.debug("Request to partially update Thread : {}", threadDTO);

        return threadRepository
            .findById(threadDTO.getId())
            .map(existingThread -> {
                threadMapper.partialUpdate(existingThread, threadDTO);

                return existingThread;
            })
            .map(threadRepository::save)
            .map(threadMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ThreadDTO> findAll() {
        log.debug("Request to get all Threads");
        return threadRepository.findAll().stream().map(threadMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ThreadDTO> findOne(Long id) {
        log.debug("Request to get Thread : {}", id);
        return threadRepository.findById(id).map(threadMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Thread : {}", id);
        threadRepository.deleteById(id);
    }
}
