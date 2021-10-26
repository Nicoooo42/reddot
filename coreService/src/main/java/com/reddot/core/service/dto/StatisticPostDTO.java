package com.reddot.core.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.reddot.core.domain.Board} entity.
 */
public class StatisticPostDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String user;

    private int day;
    
    private int month;
    
    private int year;

    public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public String toString() {
		return "StatisticPostDTO [user=" + user + ", day=" + day + ", month=" + month + ", year=" + year + "]";
	}


}
