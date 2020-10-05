package com.futsal.payload.request;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReservationRequest {
	private LocalTime startingTime;
	private LocalTime endTime;
	private LocalDate date;
	private String username;
	public LocalTime getStartingTime() {
		return startingTime;
	}
	public void setStartingTime(LocalTime startingTime) {
		this.startingTime = startingTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	 
	

}
