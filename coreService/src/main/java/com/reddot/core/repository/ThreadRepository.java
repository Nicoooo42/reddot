package com.reddot.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.reddot.core.domain.Thread;

/**
 * Spring Data SQL repository for the Thread entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {}
