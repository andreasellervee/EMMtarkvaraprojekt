package com.ee.matkarakendus;

import java.sql.SQLException;
import java.util.List;

public class RandomMainClass {

	public static void main(String[] args) throws SQLException {
		TracksData x = new TracksData();
		
		List<Track> tracks = x.getAllTracks();
		
		System.out.println(tracks.size());

	}

}
