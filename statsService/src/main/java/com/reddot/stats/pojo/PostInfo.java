package com.reddot.stats.pojo;

public class PostInfo {
	
	private String user;
	private int year;
	private int date;
	private int day;
	private long numberRecord;
	
	
	public PostInfo() {
		// TODO Auto-generated constructor stub
	}


	public PostInfo(String user, int year, int date, int day, long numberRecord) {
		super();
		this.user = user;
		this.year = year;
		this.date = date;
		this.day = day;
		this.numberRecord = numberRecord;
	}


	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public long getNumberRecord() {
		return numberRecord;
	}

	public void setNumberRecord(long numberRecord) {
		this.numberRecord = numberRecord;
	}


	@Override
	public String toString() {
		return "PostInfo [user=" + user + ", year=" + year + ", date=" + date + ", day=" + day + "]";
	}
	
	
	

}
