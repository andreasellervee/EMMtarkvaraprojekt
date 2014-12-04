package com.ee.matkarakendus.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class PictureDBUtil {
	
	public void postPictureURLToDB(int trackId, String pictureName) {
		
		String validName = pictureName.substring(9);
		String baseUrl = "http://ec2-54-165-105-107.compute-1.amazonaws.com/uploads/";
		
		Log.i("URL", baseUrl + validName);
		
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://ec2-54-165-105-107.compute-1.amazonaws.com:8080/matkarakendus-0.1.0/addImage");

	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", String.valueOf(trackId)));
	        nameValuePairs.add(new BasicNameValuePair("link", baseUrl + validName));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    	e.printStackTrace();
	    }
	}
}
