package com.reddot.core.web.rest;

import com.reddot.core.repository.ThreadRepository;
import com.reddot.core.service.ThreadService;
import com.reddot.core.service.dto.ThreadDTO;
import com.reddot.core.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reddot.core.domain.Thread}.
 */
@RestController
@RequestMapping("/api")
public class ThreadResource {

    private final Logger log = LoggerFactory.getLogger(ThreadResource.class);

    private static final String ENTITY_NAME = "coreThread";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ThreadService threadService;

    private final ThreadRepository threadRepository;

    public ThreadResource(ThreadService threadService, ThreadRepository threadRepository) {
        this.threadService = threadService;
        this.threadRepository = threadRepository;
    }

    /**
     * {@code POST  /threads} : Create a new thread.
     *
     * @param threadDTO the threadDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new threadDTO, or with status {@code 400 (Bad Request)} if the thread has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/threads")
    public ResponseEntity<ThreadDTO> createThread(@RequestBody ThreadDTO threadDTO) throws URISyntaxException {
        log.debug("REST request to save Thread : {}", threadDTO);
        if (threadDTO.getId() != null) {
            throw new BadRequestAlertException("A new thread cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ThreadDTO result = threadService.save(threadDTO);
        return ResponseEntity
            .created(new URI("/api/threads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /threads/:id} : Updates an existing thread.
     *
     * @param id the id of the threadDTO to save.
     * @param threadDTO the threadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated threadDTO,
     * or with status {@code 400 (Bad Request)} if the threadDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the threadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/threads/{id}")
    public ResponseEntity<ThreadDTO> updateThread(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThreadDTO threadDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Thread : {}, {}", id, threadDTO);
        if (threadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, threadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!threadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ThreadDTO result = threadService.save(threadDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, threadDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /threads/:id} : Partial updates given fields of an existing thread, field will ignore if it is null
     *
     * @param id the id of the threadDTO to save.
     * @param threadDTO the threadDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated threadDTO,
     * or with status {@code 400 (Bad Request)} if the threadDTO is not valid,
     * or with status {@code 404 (Not Found)} if the threadDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the threadDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/threads/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThreadDTO> partialUpdateThread(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThreadDTO threadDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Thread partially : {}, {}", id, threadDTO);
        if (threadDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, threadDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!threadRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ThreadDTO> result = threadService.partialUpdate(threadDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, threadDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /threads} : get all the threads.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of threads in body.
     */
    @GetMapping("/threads")
    public List<ThreadDTO> getAllThreads() {
        log.debug("REST request to get all Threads");
        return threadService.findAll();
    }

    /**
     * {@code GET  /threads/:id} : get the "id" thread.
     *
     * @param id the id of the threadDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the threadDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/threads/{id}")
    public ResponseEntity<ThreadDTO> getThread(@PathVariable Long id) {
        log.debug("REST request to get Thread : {}", id);
        Optional<ThreadDTO> threadDTO = threadService.findOne(id);
        return ResponseUtil.wrapOrNotFound(threadDTO);
    }

    /**
     * {@code DELETE  /threads/:id} : delete the "id" thread.
     *
     * @param id the id of the threadDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/threads/{id}")
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) {
        log.debug("REST request to delete Thread : {}", id);
        threadService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
