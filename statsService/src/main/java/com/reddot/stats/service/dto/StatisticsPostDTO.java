package com.reddot.stats.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.reddot.stats.domain.StatisticsPost} entity.
 */
public class StatisticsPostDTO implements Serializable {

    private Long id;

    private String user;

    private Integer year;

    private Integer month;

    private Integer day;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StatisticsPostDTO)) {
            return false;
        }

        StatisticsPostDTO statisticsPostDTO = (StatisticsPostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, statisticsPostDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "StatisticsPostDTO{" +
            "id=" + getId() +
            ", user='" + getUser() + "'" +
            ", year=" + getYear() +
            ", month=" + getMonth() +
            ", day=" + getDay() +
            "}";
    }
}
