package com.reddot.core.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.reddot.core.web.rest.TestUtil;

class ThreadDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ThreadDTO.class);
        ThreadDTO threadDTO1 = new ThreadDTO();
        threadDTO1.setId(1L);
        ThreadDTO threadDTO2 = new ThreadDTO();
        assertThat(threadDTO1).isNotEqualTo(threadDTO2);
        threadDTO2.setId(threadDTO1.getId());
        assertThat(threadDTO1).isEqualTo(threadDTO2);
        threadDTO2.setId(2L);
        assertThat(threadDTO1).isNotEqualTo(threadDTO2);
        threadDTO1.setId(null);
        assertThat(threadDTO1).isNotEqualTo(threadDTO2);
    }
}
