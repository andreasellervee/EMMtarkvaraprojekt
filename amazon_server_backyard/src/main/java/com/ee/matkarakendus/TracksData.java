package com.ee.matkarakendus;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class TracksData {
	
	public List<Track> getAllTracks() throws SQLException {
		List<Track> tracks = new ArrayList<Track>();
		Connection conn = getConnection();
		if(conn != null) {
			Statement stmt = conn.createStatement();
			String sql = "SELECT * FROM track_list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Track track = new Track();
				track.setID(rs.getInt("ID"));
				track.setName(rs.getString("NAME"));
				track.setDescription(rs.getString("DESCRIPTION"));
				track.setLength(rs.getDouble("LENGTH"));
				tracks.add(track);
			}
		}
		return tracks;
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
