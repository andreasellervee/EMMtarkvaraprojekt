package com.ee.matkarakendus.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class FileIOUtility {
	
	private Context context;
	
	public FileIOUtility(Context context) {
		this.context = context;
	}
	
	public void writeToFile(String data, String filename) {
		Log.i("WRITING", "writing to file " + filename + ".txt");
	    try {
	        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename + ".txt", Context.MODE_WORLD_READABLE));
	        outputStreamWriter.write(data);
	        outputStreamWriter.close();
	    }
	    catch (IOException e) {
	        Log.e("Exception", "File write failed: " + e.toString());
	    } 
	}
	
	public boolean fileExists(String filename) {
		Log.i("FILESTO", context.getFilesDir().toString());
		File file = new File(context.getFilesDir(), filename + ".txt");
		return file.exists();
	}


	public String readFromFile(String filename) {

	    String ret = "";

	    try {
	    	Log.i("READING", "reading from file " + filename + ".txt");
	        InputStream inputStream = context.openFileInput(filename + ".txt");

	        if ( inputStream != null ) {
	            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
	            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
	            String receiveString = "";
	            StringBuilder stringBuilder = new StringBuilder();

	            while ( (receiveString = bufferedReader.readLine()) != null ) {
	                stringBuilder.append(receiveString);
	            }

	            inputStream.close();
	            ret = stringBuilder.toString();
	        }
	    }
	    catch (FileNotFoundException e) {
	        Log.e("login activity", "File not found: " + e.toString());
	    } catch (IOException e) {
	        Log.e("login activity", "Can not read file: " + e.toString());
	    }

	    return ret;
	}

}
