package com.ee.matkarakendus.objects;

import java.io.Serializable;

public class Track implements Serializable {
	
	public int id;
	
	public String picture;
	
	public String name;
	
	public String description;
	
	public String country;
	
	public String county;

	public double latitude;
	
	public double longitude;
	
	public double length;
	
	public double time;
	
	public String type;
	
	public boolean isOpen;
	
	public double ascend;
	
	public boolean isFavourite;
	
	public boolean isFavourite() {
		return isFavourite;
	}

	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public String getCounty() {
		return county;
	}
	
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public double getTime() {
		return time;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture){
		this.picture = picture;
	}
	
	public void setName(String name) {
		this.name = name;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
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