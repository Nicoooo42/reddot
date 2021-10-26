package com.reddot.core.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.reddot.core.web.rest.TestUtil;

class ThreadTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thread.class);
        Thread thread1 = new Thread();
        thread1.setId(1L);
        Thread thread2 = new Thread();
        thread2.setId(thread1.getId());
        assertThat(thread1).isEqualTo(thread2);
        thread2.setId(2L);
        assertThat(thread1).isNotEqualTo(thread2);
        thread1.setId(null);
        assertThat(thread1).isNotEqualTo(thread2);
    }
}
