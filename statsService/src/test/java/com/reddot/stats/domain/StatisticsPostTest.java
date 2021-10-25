package com.reddot.stats.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reddot.stats.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StatisticsPostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StatisticsPost.class);
        StatisticsPost statisticsPost1 = new StatisticsPost();
        statisticsPost1.setId(1L);
        StatisticsPost statisticsPost2 = new StatisticsPost();
        statisticsPost2.setId(statisticsPost1.getId());
        assertThat(statisticsPost1).isEqualTo(statisticsPost2);
        statisticsPost2.setId(2L);
        assertThat(statisticsPost1).isNotEqualTo(statisticsPost2);
        statisticsPost1.setId(null);
        assertThat(statisticsPost1).isNotEqualTo(statisticsPost2);
    }
}
