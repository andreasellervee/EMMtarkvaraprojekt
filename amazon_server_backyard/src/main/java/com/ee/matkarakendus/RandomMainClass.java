package com.ee.matkarakendus;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RandomMainClass {

	public static void main(String[] args) throws SQLException, IOException {
		System.out.println(TracksData.getTrackComments(31));
		
		
//		String sourceFileUri = "C:/Users/sande_000/Desktop/Random/Jxw4zpW.jpg";
//		String upLoadServerUri = "http://ec2-54-165-105-107.compute-1.amazonaws.com/UploadToServer.php";
//		File sourceFile = new File(sourceFileUri ); 
//		String boundary = "*****"; 
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//		
//		FileInputStream fileInputStream = new FileInputStream(sourceFile);
//        
//		URL url = new URL(upLoadServerUri);
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
//        conn.setDoInput(true); // Allow Inputs
//        conn.setDoOutput(true); // Allow Outputs
//        conn.setUseCaches(false); // Don't use a Cached Copy
//        conn.setRequestMethod("POST");
//        conn.setRequestProperty("Connection", "Keep-Alive");
//        conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//		conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//		conn.setRequestProperty("uploaded_file", sourceFileUri); 
//		DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
//        
//        dos.writeBytes(twoHyphens + boundary + lineEnd); 
//        dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ sourceFileUri + "\"" + lineEnd);
//         
//        dos.writeBytes(lineEnd);
//
//        // create a buffer of  maximum size
//        int bytesAvailable = fileInputStream.available(); 
//
//        int maxBufferSize = 1 * 1024 * 1024;
//		int bufferSize = Math.min(bytesAvailable, maxBufferSize);
//        byte[] buffer = new byte[bufferSize];
//
//        // read file and write it into form...
//        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);  
//           
//        while (bytesRead > 0) {
//             
//          dos.write(buffer, 0, bufferSize);
//          bytesAvailable = fileInputStream.available();
//          bufferSize = Math.min(bytesAvailable, maxBufferSize);
//          bytesRead = fileInputStream.read(buffer, 0, bufferSize);   
//           
//         }
//
//        // send multipart form data necesssary after file data...
//        dos.writeBytes(lineEnd);
//        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//        // Responses from the server (code and message)
//        Object content = conn.getContent();
//        
//        System.out.println("Uploaded file name: " + conn.getHeaderField("Content"));
//        fileInputStream.close();
//        dos.flush();
//        dos.close();
	}

}
