package com.reddot.stats.web.rest;

import com.reddot.stats.pojo.PostInfo;
import com.reddot.stats.repository.StatisticsPostRepository;
import com.reddot.stats.service.StatisticsPostService;
import com.reddot.stats.service.dto.StatisticsPostDTO;
import com.reddot.stats.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link com.reddot.stats.domain.StatisticsPost}.
 */
@RestController
@RequestMapping("/api")
public class StatisticsPostResource {

    private final Logger log = LoggerFactory.getLogger(StatisticsPostResource.class);

    private static final String ENTITY_NAME = "statsServiceStatisticsPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StatisticsPostService statisticsPostService;

    private final StatisticsPostRepository statisticsPostRepository;

    public StatisticsPostResource(StatisticsPostService statisticsPostService, StatisticsPostRepository statisticsPostRepository) {
        this.statisticsPostService = statisticsPostService;
        this.statisticsPostRepository = statisticsPostRepository;
    }
    
    
    /**
     * {@code GET  /statistics-by-user-and-datr}
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the PostInfo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statistics-by-user-and-date")
    public List<PostInfo> getStatisticsPost() {
        log.debug("REST request to get StatisticsPost : {}");
        return statisticsPostService.stasticsByUserAndDate();
    }
    


    /**
     * {@code POST  /statistics-posts} : Create a new statisticsPost.
     *
     * @param statisticsPostDTO the statisticsPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new statisticsPostDTO, or with status {@code 400 (Bad Request)} if the statisticsPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/statistics-posts")
    public ResponseEntity<StatisticsPostDTO> createStatisticsPost(@RequestBody StatisticsPostDTO statisticsPostDTO)
        throws URISyntaxException {
        log.debug("REST request to save StatisticsPost : {}", statisticsPostDTO);
        if (statisticsPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new statisticsPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StatisticsPostDTO result = statisticsPostService.save(statisticsPostDTO);
        return ResponseEntity
            .created(new URI("/api/statistics-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /statistics-posts/:id} : Updates an existing statisticsPost.
     *
     * @param id the id of the statisticsPostDTO to save.
     * @param statisticsPostDTO the statisticsPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticsPostDTO,
     * or with status {@code 400 (Bad Request)} if the statisticsPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the statisticsPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/statistics-posts/{id}")
    public ResponseEntity<StatisticsPostDTO> updateStatisticsPost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticsPostDTO statisticsPostDTO
    ) throws URISyntaxException {
        log.debug("REST request to update StatisticsPost : {}, {}", id, statisticsPostDTO);
        if (statisticsPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticsPostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        StatisticsPostDTO result = statisticsPostService.save(statisticsPostDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statisticsPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /statistics-posts/:id} : Partial updates given fields of an existing statisticsPost, field will ignore if it is null
     *
     * @param id the id of the statisticsPostDTO to save.
     * @param statisticsPostDTO the statisticsPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated statisticsPostDTO,
     * or with status {@code 400 (Bad Request)} if the statisticsPostDTO is not valid,
     * or with status {@code 404 (Not Found)} if the statisticsPostDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the statisticsPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/statistics-posts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<StatisticsPostDTO> partialUpdateStatisticsPost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody StatisticsPostDTO statisticsPostDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update StatisticsPost partially : {}, {}", id, statisticsPostDTO);
        if (statisticsPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, statisticsPostDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!statisticsPostRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<StatisticsPostDTO> result = statisticsPostService.partialUpdate(statisticsPostDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, statisticsPostDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /statistics-posts} : get all the statisticsPosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of statisticsPosts in body.
     */
    @GetMapping("/statistics-posts")
    public List<StatisticsPostDTO> getAllStatisticsPosts() {
        log.debug("REST request to get all StatisticsPosts");
        return statisticsPostService.findAll();
    }

    /**
     * {@code GET  /statistics-posts/:id} : get the "id" statisticsPost.
     *
     * @param id the id of the statisticsPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the statisticsPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/statistics-posts/{id}")
    public ResponseEntity<StatisticsPostDTO> getStatisticsPost(@PathVariable Long id) {
        log.debug("REST request to get StatisticsPost : {}", id);
        Optional<StatisticsPostDTO> statisticsPostDTO = statisticsPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(statisticsPostDTO);
    }

    /**
     * {@code DELETE  /statistics-posts/:id} : delete the "id" statisticsPost.
     *
     * @param id the id of the statisticsPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/statistics-posts/{id}")
    public ResponseEntity<Void> deleteStatisticsPost(@PathVariable Long id) {
        log.debug("REST request to delete StatisticsPost : {}", id);
        statisticsPostService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
