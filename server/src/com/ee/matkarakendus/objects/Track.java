package com.ee.matkarakendus.objects;

public class Track {
	public String id;
	
	public String description;
	
	public double latitude;
	
	public double longitude;
	
	public double length;
	
	public long type;
	
	public Boolean isOpen;
	
	public double ascend;
	
	//Media (pictures)
	
	//Points of interest near the track
		
	//User submitted track comments

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public long getType() {
		return type;
	}

	public void setType(long type) {
		this.type = type;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

	public double getAscend() {
		return ascend;
	}

	public void setAscend(double ascend) {
		this.ascend = ascend;
	}
}