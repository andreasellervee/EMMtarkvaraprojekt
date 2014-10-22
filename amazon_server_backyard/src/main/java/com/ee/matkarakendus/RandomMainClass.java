package com.ee.matkarakendus;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RandomMainClass {

	public static void main(String[] args) throws SQLException {
		TracksData x = new TracksData();
		
		List<Track> tracks = x.getAllTracks();
		List<POI> POIs = x.getAllPOIs();
		Map<Integer, List<Coordinate>> allCoords= x.getAllCoordinates();
		List<Coordinate> coords7 = allCoords.get(7);
		System.out.println(coords7.size());

	}

}
