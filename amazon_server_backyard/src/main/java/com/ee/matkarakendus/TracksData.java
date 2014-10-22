package com.ee.matkarakendus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.mysql.jdbc.*;

@Repository
public class TracksData {
	
	public List<Track> getAllTracks() throws SQLException {
		List<Track> tracks = new ArrayList<Track>();
		Connection conn = getConnection();
		if(conn != null) {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM TRACKS";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Track track = new Track();
				track.setID(rs.getInt("TRACK_id"));
				track.setName(rs.getString("NAME"));
				track.setDescription(rs.getString("DESCRIPTION"));
				track.setLength(rs.getDouble("LENGTH"));
				track.setLat(rs.getFloat("START_LAT"));
				track.setLng(rs.getFloat("START_LNG"));
				track.setTime(rs.getDouble("TIME"));
				track.setCountry(rs.getString("COUNTRY"));
				track.setCounty(rs.getString("COUNTY"));
				track.setType(rs.getString("TYPE"));
				if(rs.getInt("IS_OPEN") == 1){
					track.setIsOpen(true);
				}
				else{
					track.setIsOpen(false);
				}
				
				tracks.add(track);
			}
		}
		return tracks;
	}
	public List<POI> getAllPOIs() throws SQLException {
		List<POI> POIs = new ArrayList<POI>();
		Connection conn = getConnection();
		if(conn != null) {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM POI";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				POI poi = new POI();
				poi.setID(rs.getInt("POI_id"));
				poi.setName(rs.getString("NAME"));
				poi.setDescription(rs.getString("DESCRIPTION"));
				poi.setCountry(rs.getString("COUNTRY"));
				poi.setCounty(rs.getString("COUNTY"));
				poi.setType(rs.getString("TYPE"));
				poi.setLat(rs.getDouble("LAT"));
				poi.setLng(rs.getDouble("LNG"));
				POIs.add(poi);
			}
		}
		return POIs;
	}
	public List<Coordinate> getTrackCoordinates(int id) throws SQLException {
		Connection conn = getConnection();
		List<Coordinate> Coordinates = new ArrayList<Coordinate>();
		String sql = "SELECT * FROM COORDINATES WHERE TRACK_ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Coordinate coord = new Coordinate();
			coord.setLat(rs.getDouble("LAT"));
			coord.setLng(rs.getDouble("LNG"));
			Coordinates.add(coord);
		}
		return Coordinates;
	}
	public Map<Integer, List<Coordinate>> getAllCoordinates() throws SQLException {
		Connection conn = getConnection();
		Map<Integer, List<Coordinate>> Coordinates = new HashMap<Integer, List<Coordinate>>();
		String sql = "SELECT * FROM COORDINATES";
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			Coordinate coord = new Coordinate();
			coord.setLat(rs.getDouble("LAT"));
			coord.setLng(rs.getDouble("LNG"));
			int id = rs.getInt("TRACK_ID");
			if (Coordinates.containsKey(id)){
				List<Coordinate> Coords = Coordinates.get(id);
				Coords.add(coord);
				Coordinates.put(id, Coords);
			}
			else{
				List<Coordinate> Coords = new ArrayList<Coordinate>();
				Coords.add(coord);
				Coordinates.put(id, Coords);
			}
			
		}
		return Coordinates;
	}
	public Track getSingleTrack(int id) throws SQLException {
		Connection conn = getConnection();
		String sql = "SELECT * FROM track_list WHERE ID = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Track track = new Track();
		track.setID(rs.getInt("ID"));
		track.setName(rs.getString("NAME"));
		track.setDescription(rs.getString("DESCRIPTION"));
		track.setLength(rs.getDouble("LENGTH"));
		return track;
		
	}
	
	private Long getLong(Double d) {
		return Long.valueOf((new Double(d)).longValue());
	}
	
	private Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql://localhost:3306/emmprojekttest";
			connection = DriverManager.getConnection(url, "root", "emmprojekt");
		} catch (Exception e) {
			// connection problem
			e.printStackTrace();
		}
		return connection;
	}

}
