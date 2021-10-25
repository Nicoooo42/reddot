package com.reddot.core.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.reddot.core.IntegrationTest;
import com.reddot.core.domain.Thread;
import com.reddot.core.repository.ThreadRepository;
import com.reddot.core.service.dto.ThreadDTO;
import com.reddot.core.service.mapper.ThreadMapper;
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
 * Integration tests for the {@link ThreadResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ThreadResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/threads";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ThreadRepository threadRepository;

    @Autowired
    private ThreadMapper threadMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restThreadMockMvc;

    private Thread thread;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thread createEntity(EntityManager em) {
        Thread thread = new Thread().name(DEFAULT_NAME);
        return thread;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Thread createUpdatedEntity(EntityManager em) {
        Thread thread = new Thread().name(UPDATED_NAME);
        return thread;
    }

    @BeforeEach
    public void initTest() {
        thread = createEntity(em);
    }

    @Test
    @Transactional
    void createThread() throws Exception {
        int databaseSizeBeforeCreate = threadRepository.findAll().size();
        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);
        restThreadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(threadDTO)))
            .andExpect(status().isCreated());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeCreate + 1);
        Thread testThread = threadList.get(threadList.size() - 1);
        assertThat(testThread.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createThreadWithExistingId() throws Exception {
        // Create the Thread with an existing ID
        thread.setId(1L);
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        int databaseSizeBeforeCreate = threadRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restThreadMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(threadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllThreads() throws Exception {
        // Initialize the database
        threadRepository.saveAndFlush(thread);

        // Get all the threadList
        restThreadMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(thread.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getThread() throws Exception {
        // Initialize the database
        threadRepository.saveAndFlush(thread);

        // Get the thread
        restThreadMockMvc
            .perform(get(ENTITY_API_URL_ID, thread.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(thread.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getNonExistingThread() throws Exception {
        // Get the thread
        restThreadMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewThread() throws Exception {
        // Initialize the database
        threadRepository.saveAndFlush(thread);

        int databaseSizeBeforeUpdate = threadRepository.findAll().size();

        // Update the thread
        Thread updatedThread = threadRepository.findById(thread.getId()).get();
        // Disconnect from session so that the updates on updatedThread are not directly saved in db
        em.detach(updatedThread);
        updatedThread.name(UPDATED_NAME);
        ThreadDTO threadDTO = threadMapper.toDto(updatedThread);

        restThreadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, threadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(threadDTO))
            )
            .andExpect(status().isOk());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
        Thread testThread = threadList.get(threadList.size() - 1);
        assertThat(testThread.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void putNonExistingThread() throws Exception {
        int databaseSizeBeforeUpdate = threadRepository.findAll().size();
        thread.setId(count.incrementAndGet());

        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThreadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, threadDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(threadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchThread() throws Exception {
        int databaseSizeBeforeUpdate = threadRepository.findAll().size();
        thread.setId(count.incrementAndGet());

        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThreadMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(threadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamThread() throws Exception {
        int databaseSizeBeforeUpdate = threadRepository.findAll().size();
        thread.setId(count.incrementAndGet());

        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThreadMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(threadDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateThreadWithPatch() throws Exception {
        // Initialize the database
        threadRepository.saveAndFlush(thread);

        int databaseSizeBeforeUpdate = threadRepository.findAll().size();

        // Update the thread using partial update
        Thread partialUpdatedThread = new Thread();
        partialUpdatedThread.setId(thread.getId());

        partialUpdatedThread.name(UPDATED_NAME);

        restThreadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThread.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThread))
            )
            .andExpect(status().isOk());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
        Thread testThread = threadList.get(threadList.size() - 1);
        assertThat(testThread.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void fullUpdateThreadWithPatch() throws Exception {
        // Initialize the database
        threadRepository.saveAndFlush(thread);

        int databaseSizeBeforeUpdate = threadRepository.findAll().size();

        // Update the thread using partial update
        Thread partialUpdatedThread = new Thread();
        partialUpdatedThread.setId(thread.getId());

        partialUpdatedThread.name(UPDATED_NAME);

        restThreadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedThread.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedThread))
            )
            .andExpect(status().isOk());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
        Thread testThread = threadList.get(threadList.size() - 1);
        assertThat(testThread.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingThread() throws Exception {
        int databaseSizeBeforeUpdate = threadRepository.findAll().size();
        thread.setId(count.incrementAndGet());

        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restThreadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, threadDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(threadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchThread() throws Exception {
        int databaseSizeBeforeUpdate = threadRepository.findAll().size();
        thread.setId(count.incrementAndGet());

        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThreadMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(threadDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamThread() throws Exception {
        int databaseSizeBeforeUpdate = threadRepository.findAll().size();
        thread.setId(count.incrementAndGet());

        // Create the Thread
        ThreadDTO threadDTO = threadMapper.toDto(thread);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restThreadMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(threadDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Thread in the database
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteThread() throws Exception {
        // Initialize the database
        threadRepository.saveAndFlush(thread);

        int databaseSizeBeforeDelete = threadRepository.findAll().size();

        // Delete the thread
        restThreadMockMvc
            .perform(delete(ENTITY_API_URL_ID, thread.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Thread> threadList = threadRepository.findAll();
        assertThat(threadList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
