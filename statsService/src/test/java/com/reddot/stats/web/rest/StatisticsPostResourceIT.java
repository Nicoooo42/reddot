package com.reddot.stats.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reddot.stats.IntegrationTest;
import com.reddot.stats.domain.StatisticsPost;
import com.reddot.stats.repository.StatisticsPostRepository;
import com.reddot.stats.service.dto.StatisticsPostDTO;
import com.reddot.stats.service.mapper.StatisticsPostMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link StatisticsPostResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class StatisticsPostResourceIT {

    private static final String DEFAULT_USER = "AAAAAAAAAA";
    private static final String UPDATED_USER = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_MONTH = 1;
    private static final Integer UPDATED_MONTH = 2;

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final String ENTITY_API_URL = "/api/statistics-posts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StatisticsPostRepository statisticsPostRepository;

    @Autowired
    private StatisticsPostMapper statisticsPostMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatisticsPostMockMvc;

    private StatisticsPost statisticsPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticsPost createEntity(EntityManager em) {
        StatisticsPost statisticsPost = new StatisticsPost().user(DEFAULT_USER).year(DEFAULT_YEAR).month(DEFAULT_MONTH).day(DEFAULT_DAY);
        return statisticsPost;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatisticsPost createUpdatedEntity(EntityManager em) {
        StatisticsPost statisticsPost = new StatisticsPost().user(UPDATED_USER).year(UPDATED_YEAR).month(UPDATED_MONTH).day(UPDATED_DAY);
        return statisticsPost;
    }

    @BeforeEach
    public void initTest() {
        statisticsPost = createEntity(em);
    }

    @Test
    @Transactional
    void createStatisticsPost() throws Exception {
        int databaseSizeBeforeCreate = statisticsPostRepository.findAll().size();
        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);
        restStatisticsPostMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isCreated());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeCreate + 1);
        StatisticsPost testStatisticsPost = statisticsPostList.get(statisticsPostList.size() - 1);
        assertThat(testStatisticsPost.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testStatisticsPost.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testStatisticsPost.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testStatisticsPost.getDay()).isEqualTo(DEFAULT_DAY);
    }

    @Test
    @Transactional
    void createStatisticsPostWithExistingId() throws Exception {
        // Create the StatisticsPost with an existing ID
        statisticsPost.setId(1L);
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        int databaseSizeBeforeCreate = statisticsPostRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatisticsPostMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStatisticsPosts() throws Exception {
        // Initialize the database
        statisticsPostRepository.saveAndFlush(statisticsPost);

        // Get all the statisticsPostList
        restStatisticsPostMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statisticsPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].user").value(hasItem(DEFAULT_USER)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].month").value(hasItem(DEFAULT_MONTH)))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)));
    }

    @Test
    @Transactional
    void getStatisticsPost() throws Exception {
        // Initialize the database
        statisticsPostRepository.saveAndFlush(statisticsPost);

        // Get the statisticsPost
        restStatisticsPostMockMvc
            .perform(get(ENTITY_API_URL_ID, statisticsPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statisticsPost.getId().intValue()))
            .andExpect(jsonPath("$.user").value(DEFAULT_USER))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.month").value(DEFAULT_MONTH))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY));
    }

    @Test
    @Transactional
    void getNonExistingStatisticsPost() throws Exception {
        // Get the statisticsPost
        restStatisticsPostMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStatisticsPost() throws Exception {
        // Initialize the database
        statisticsPostRepository.saveAndFlush(statisticsPost);

        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();

        // Update the statisticsPost
        StatisticsPost updatedStatisticsPost = statisticsPostRepository.findById(statisticsPost.getId()).get();
        // Disconnect from session so that the updates on updatedStatisticsPost are not directly saved in db
        em.detach(updatedStatisticsPost);
        updatedStatisticsPost.user(UPDATED_USER).year(UPDATED_YEAR).month(UPDATED_MONTH).day(UPDATED_DAY);
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(updatedStatisticsPost);

        restStatisticsPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticsPostDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isOk());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
        StatisticsPost testStatisticsPost = statisticsPostList.get(statisticsPostList.size() - 1);
        assertThat(testStatisticsPost.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testStatisticsPost.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testStatisticsPost.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testStatisticsPost.getDay()).isEqualTo(UPDATED_DAY);
    }

    @Test
    @Transactional
    void putNonExistingStatisticsPost() throws Exception {
        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();
        statisticsPost.setId(count.incrementAndGet());

        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, statisticsPostDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStatisticsPost() throws Exception {
        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();
        statisticsPost.setId(count.incrementAndGet());

        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsPostMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStatisticsPost() throws Exception {
        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();
        statisticsPost.setId(count.incrementAndGet());

        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsPostMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStatisticsPostWithPatch() throws Exception {
        // Initialize the database
        statisticsPostRepository.saveAndFlush(statisticsPost);

        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();

        // Update the statisticsPost using partial update
        StatisticsPost partialUpdatedStatisticsPost = new StatisticsPost();
        partialUpdatedStatisticsPost.setId(statisticsPost.getId());

        partialUpdatedStatisticsPost.year(UPDATED_YEAR);

        restStatisticsPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticsPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatisticsPost))
            )
            .andExpect(status().isOk());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
        StatisticsPost testStatisticsPost = statisticsPostList.get(statisticsPostList.size() - 1);
        assertThat(testStatisticsPost.getUser()).isEqualTo(DEFAULT_USER);
        assertThat(testStatisticsPost.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testStatisticsPost.getMonth()).isEqualTo(DEFAULT_MONTH);
        assertThat(testStatisticsPost.getDay()).isEqualTo(DEFAULT_DAY);
    }

    @Test
    @Transactional
    void fullUpdateStatisticsPostWithPatch() throws Exception {
        // Initialize the database
        statisticsPostRepository.saveAndFlush(statisticsPost);

        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();

        // Update the statisticsPost using partial update
        StatisticsPost partialUpdatedStatisticsPost = new StatisticsPost();
        partialUpdatedStatisticsPost.setId(statisticsPost.getId());

        partialUpdatedStatisticsPost.user(UPDATED_USER).year(UPDATED_YEAR).month(UPDATED_MONTH).day(UPDATED_DAY);

        restStatisticsPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStatisticsPost.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStatisticsPost))
            )
            .andExpect(status().isOk());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
        StatisticsPost testStatisticsPost = statisticsPostList.get(statisticsPostList.size() - 1);
        assertThat(testStatisticsPost.getUser()).isEqualTo(UPDATED_USER);
        assertThat(testStatisticsPost.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testStatisticsPost.getMonth()).isEqualTo(UPDATED_MONTH);
        assertThat(testStatisticsPost.getDay()).isEqualTo(UPDATED_DAY);
    }

    @Test
    @Transactional
    void patchNonExistingStatisticsPost() throws Exception {
        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();
        statisticsPost.setId(count.incrementAndGet());

        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatisticsPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, statisticsPostDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStatisticsPost() throws Exception {
        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();
        statisticsPost.setId(count.incrementAndGet());

        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsPostMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStatisticsPost() throws Exception {
        int databaseSizeBeforeUpdate = statisticsPostRepository.findAll().size();
        statisticsPost.setId(count.incrementAndGet());

        // Create the StatisticsPost
        StatisticsPostDTO statisticsPostDTO = statisticsPostMapper.toDto(statisticsPost);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStatisticsPostMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(statisticsPostDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the StatisticsPost in the database
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStatisticsPost() throws Exception {
        // Initialize the database
        statisticsPostRepository.saveAndFlush(statisticsPost);

        int databaseSizeBeforeDelete = statisticsPostRepository.findAll().size();

        // Delete the statisticsPost
        restStatisticsPostMockMvc
            .perform(delete(ENTITY_API_URL_ID, statisticsPost.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatisticsPost> statisticsPostList = statisticsPostRepository.findAll();
        assertThat(statisticsPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
