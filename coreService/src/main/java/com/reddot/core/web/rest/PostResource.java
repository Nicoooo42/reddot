package com.reddot.core.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reddot.core.repository.PostRepository;
import com.reddot.core.service.PostService;
import com.reddot.core.service.dto.PostDTO;
import com.reddot.core.service.dto.StatisticPostDTO;
import com.reddot.core.web.rest.errors.BadRequestAlertException;

import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.reddot.core.domain.Post}.
 */
@RestController
@RequestMapping("/api")
public class PostResource {

    private final Logger log = LoggerFactory.getLogger(PostResource.class);

    private static final String ENTITY_NAME = "corePost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PostService postService;

    private final PostRepository postRepository;
    
	private final ProducerTemplate producerTemplate;

    public PostResource(PostService postService, PostRepository postRepository, ProducerTemplate producerTemplate) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.producerTemplate = producerTemplate;
    }

    /**
     * {@code POST  /posts} : Create a new post.
     *
     * @param postDTO the postDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new postDTO, or with status {@code 400 (Bad Request)} if the post has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws JsonProcessingException 
     */
    @PostMapping("/posts")
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) throws URISyntaxException {
        log.debug("REST request to save Post : {}", postDTO);
        if (postDTO.getId() != null) {
            throw new BadRequestAlertException("A new post cannot already have an ID", ENTITY_NAME, "idexists");
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        
        postDTO.setUserName(login);
        PostDTO result = postService.save(postDTO);

    	try {
            ObjectMapper mapper = new ObjectMapper();
            StatisticPostDTO stats = new StatisticPostDTO();
            
            Calendar cal = Calendar.getInstance();
            
            stats.setUser(login);
            
            stats.setYear(cal.get(Calendar.YEAR));
            stats.setMonth(cal.get(Calendar.MONTH));
            stats.setDay(cal.get(Calendar.DAY_OF_MONTH));
            
        	String jsonString = mapper.writeValueAsString(stats);
            
            producerTemplate.sendBody("direct:firstRoute", jsonString);
		} catch (Exception e) {
			// Error is is no blocking
		}

        
        return ResponseEntity
            .created(new URI("/api/posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /posts/:id} : Updates an existing post.
     *
     * @param id the id of the postDTO to save.
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postDTO,
     * or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the postDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/posts/{id}")
    public ResponseEntity<PostDTO> updatePost(@PathVariable(value = "id", required = false) final Long id, @RequestBody PostDTO postDTO)
        throws URISyntaxException {
        log.debug("REST request to update Post : {}, {}", id, postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PostDTO result = postService.save(postDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /posts/:id} : Partial updates given fields of an existing post, field will ignore if it is null
     *
     * @param id the id of the postDTO to save.
     * @param postDTO the postDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated postDTO,
     * or with status {@code 400 (Bad Request)} if the postDTO is not valid,
     * or with status {@code 404 (Not Found)} if the postDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the postDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/posts/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PostDTO> partialUpdatePost(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody PostDTO postDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Post partially : {}, {}", id, postDTO);
        if (postDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, postDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!postRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PostDTO> result = postService.partialUpdate(postDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, postDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /posts} : get all the posts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posts in body.
     */
    @GetMapping("/posts")
    public List<PostDTO> getAllPosts() {
        log.debug("REST request to get all Posts");
        return postService.findAll();
    }
    
    
    /**
     * {@code GET  /posts} : get all the posts by UserName.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of posts in body.
     */
    @GetMapping("/posts/username/{login}")
    public List<PostDTO> getAllPosts(@PathVariable String login) {
        log.debug("REST request to get all Posts by login");
        return postService.findByUserName(login);
    }

    /**
     * {@code GET  /posts/:id} : get the "id" post.
     *
     * @param id the id of the postDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the postDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDTO> getPost(@PathVariable Long id) {
        log.debug("REST request to get Post : {}", id);
        Optional<PostDTO> postDTO = postService.findOne(id);
        return ResponseUtil.wrapOrNotFound(postDTO);
    }

    /**
     * {@code DELETE  /posts/:id} : delete the "id" post.
     *
     * @param id the id of the postDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        log.debug("REST request to delete Post : {}", id);
        postService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
