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
				track.setID(rs.getInt("TRACK_ID"));
				track.setName(rs.getString("NAME"));
				track.setDescription(rs.getString("DESCRIPTION"));
				track.setLength(rs.getDouble("LENGTH"));
				track.setLat(rs.getDouble("START_LAT"));
				track.setLng(rs.getDouble("START_LNG"));
				track.setEndLat(rs.getDouble("END_LAT"));
				track.setEndLng(rs.getDouble("END_LNG"));
				track.setCountry(rs.getString("COUNTRY"));
				track.setCounty(rs.getString("COUNTY"));
				track.setType(rs.getString("TYPE"));
				track.setStatus(rs.getInt("STATUS"));
				
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
			String sql = "SELECT * FROM POIS";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				POI poi = new POI();
				poi.setID(rs.getInt("POI_ID"));
				poi.setTrack_ID(rs.getInt("TRACK_ID"));
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
	public String getTrackGPX(int id) throws SQLException {
		String GPX = new String();
		Connection conn = getConnection();
		if(conn != null) {
			String sqlGPX = "SELECT GPX FROM GPX WHERE TRACK_ID = ?";
			PreparedStatement stmtGPX = conn.prepareStatement(sqlGPX);
			stmtGPX.setInt(1, id);
			ResultSet rs = stmtGPX.executeQuery();
			while(rs.next()) {
				GPX = rs.getString("GPX");
			}
		}
		return GPX;
	}
	
	public static List<Comment> getTrackComments(int id) throws SQLException {
		List<Comment> comments = new ArrayList<Comment>();
		Connection conn = getConnection();
		if(conn != null) {
			String sqlComment = "SELECT * FROM COMMENTS WHERE TRACK_ID = ? ORDER BY COMMENT_ID DESC";
			PreparedStatement stmtComment = conn.prepareStatement(sqlComment);
			stmtComment.setInt(1, id);
			ResultSet rs = stmtComment.executeQuery();
			while(rs.next()) {
				Comment comm = new Comment();
				comm.setId(rs.getInt("COMMENT_ID"));
				comm.setTrackID(rs.getInt("TRACK_ID"));
				comm.setComment(rs.getString("COMMENT"));
				
				comments.add(comm);
			}
		}
		return comments;
	}
	
	public List<POI> getTrackPOIs(int id) throws SQLException {
		List<POI> POIs = new ArrayList<POI>();
		Connection conn = getConnection();
		if(conn != null) {
			String sqlPOI = "SELECT * FROM POIS WHERE TRACK_ID = ?";
			PreparedStatement stmtPOI = conn.prepareStatement(sqlPOI);
			stmtPOI.setInt(1, id);
			ResultSet rs = stmtPOI.executeQuery();
			while(rs.next()) {
				POI poi = new POI();
				poi.setID(rs.getInt("POI_ID"));
				poi.setTrack_ID(rs.getInt("TRACK_ID"));
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
	
	
	public List<String> getTrackImages(int id) throws SQLException {
		List<String> images = new ArrayList<String>();
		Connection conn = getConnection();
		if(conn != null) {
			String sqlImage = "SELECT URL FROM TRACK_IMAGES WHERE TRACK_ID = ?";
			PreparedStatement stmtImage = conn.prepareStatement(sqlImage);
			stmtImage.setInt(1, id);
			ResultSet rs = stmtImage.executeQuery();
			while(rs.next()) {
				images.add(rs.getString("URL"));
			}
		}
		return images;
	}
	public Map<Integer, List<Coordinate>> getTrackCoordinates(int id) throws SQLException {
		Connection conn = getConnection();
		List<Coordinate> Coordinates = new ArrayList<Coordinate>();
		Map<Integer, List<Coordinate>> coords = new HashMap<Integer, List<Coordinate>>();
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
		coords.put(id, Coordinates);
		return coords;
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
	
	
	private Long getLong(Double d) {
		return Long.valueOf((new Double(d)).longValue());
	}
	
	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql://ec2-54-165-105-107.compute-1.amazonaws.com:3306/EMMdb";
			connection = DriverManager.getConnection(url, "tere", "talv");
			
//			String url = "jdbc:mysql://localhost:3306/EMMdb";
//			connection = DriverManager.getConnection(url, "päka", "pikk");
//			hea et me mõlemad paroolid nähtavale jätsime
		} catch (Exception e) {
			// connection problem
			e.printStackTrace();
		}
		return connection;
	}

}
