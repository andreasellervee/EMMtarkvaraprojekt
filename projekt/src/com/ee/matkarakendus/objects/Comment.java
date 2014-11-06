package com.ee.matkarakendus.objects;

import java.io.Serializable;

public class Comment implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String body;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
}