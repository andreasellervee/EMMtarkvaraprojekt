package com.ee.matkarakendus;

public class Comment {
	private int id;
	private int TrackID;
	private String comment;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrackID() {
		return TrackID;
	}
	public void setTrackID(int trackID) {
		TrackID = trackID;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
