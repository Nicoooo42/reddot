package com.reddot.stats.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reddot.stats.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticsPostDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticsPostDTO.class);
        StatisticsPostDTO statisticsPostDTO1 = new StatisticsPostDTO();
        statisticsPostDTO1.setId(1L);
        StatisticsPostDTO statisticsPostDTO2 = new StatisticsPostDTO();
        assertThat(statisticsPostDTO1).isNotEqualTo(statisticsPostDTO2);
        statisticsPostDTO2.setId(statisticsPostDTO1.getId());
        assertThat(statisticsPostDTO1).isEqualTo(statisticsPostDTO2);
        statisticsPostDTO2.setId(2L);
        assertThat(statisticsPostDTO1).isNotEqualTo(statisticsPostDTO2);
        statisticsPostDTO1.setId(null);
        assertThat(statisticsPostDTO1).isNotEqualTo(statisticsPostDTO2);
    }
}
