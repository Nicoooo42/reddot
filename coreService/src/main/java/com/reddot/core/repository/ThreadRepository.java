package com.reddot.core.repository;

import com.reddot.core.domain.Thread;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Thread entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ThreadRepository extends JpaRepository<Thread, Long> {}
