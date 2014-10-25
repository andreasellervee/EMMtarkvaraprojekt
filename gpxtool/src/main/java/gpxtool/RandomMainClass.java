package gpxtool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;




public class RandomMainClass {

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String TEST_XML_STRING = "";
    public static String jsonPrettyPrintString = "";

    public static void main(String[] args) throws SQLException {
    	
    	BufferedReader br = null;
    	 
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("RMK Sakala tee matkarada.gpx"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				TEST_XML_STRING += sCurrentLine + "\n";
			}
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
    	
        try {
            JSONObject xmlJSONObj = XML.toJSONObject(TEST_XML_STRING);
            jsonPrettyPrintString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);
            System.out.println(jsonPrettyPrintString);
        } catch (JSONException je) {
            System.out.println(je.toString());
        }
        
        try {
			JSONObject gpx = new JSONObject(jsonPrettyPrintString).getJSONObject("gpx");
			//track - name
			JSONObject track = gpx.getJSONObject("trk");
			String trackName = track.getString("name");
			//list of trackpoints
			JSONObject trackSeg = track.getJSONObject("trkseg");
			
			//pair of lat/longs
			JSONArray trackPoints = trackSeg.getJSONArray("trkpt");
			
			//elevation, lat, long, name, symbolURL per object
			JSONArray pointsOfInterest = gpx.getJSONArray("wpt");
			
			//list of list of latitudes and longitudes
			List<List<Double>> listOfLatLongs = new ArrayList<List<Double>>();
			for(int i = 0; i < trackPoints.length(); i++) {
				JSONObject pair = trackPoints.getJSONObject(i);
				List<Double> onePair = new ArrayList<Double>();
				onePair.add(pair.getDouble("lat"));
				onePair.add(pair.getDouble("lon"));
				listOfLatLongs.add(onePair);
			}
			addTrack(trackName, listOfLatLongs);
			
			//map of POIs
			Map<String, List<Double>> POIs = new HashMap<String, List<Double>>();
			for(int i = 0; i < pointsOfInterest.length(); i++) {
				JSONObject singlePOI = pointsOfInterest.getJSONObject(i);
				List<Double> onePair = new ArrayList<Double>();
				onePair.add(singlePOI.getDouble("lat"));
				onePair.add(singlePOI.getDouble("lon"));
				String name = singlePOI.getString("name");
				POIs.put(name, onePair);
			}
			addPOIs(POIs);
			

			System.out.println(POIs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
    private static void addTrack(String name, List<List<Double>> coordinates) throws SQLException{
    	Connection conn = getConnection();
    	conn.setAutoCommit(false);
    	
    	// get distance
    	double distance = 0;
    	for(int i = 0; i < coordinates.size() - 1; i++){
    		distance += distance(coordinates.get(i).get(0),coordinates.get(i).get(1),coordinates.get(i+1).get(0),coordinates.get(i+1).get(1));
    	}
    	
    	// add to tracks table
    	String sql = "INSERT INTO TRACKS(NAME, LENGTH, START_LAT, START_LNG) VALUES(?,?,?,?)";
    	Double lat = coordinates.get(0).get(0);
		Double lng = coordinates.get(0).get(1);
		String trackName = name;
		List parameters = Arrays.asList(name, distance, lat, lng);
		int numRowsUpdated = update(conn, sql, parameters);
		conn.commit();
		
		// get added track id
		String sqlForID = "SELECT TRACK_id FROM TRACKS WHERE NAME = '" + trackName + "'";
		PreparedStatement stmt = conn.prepareStatement(sqlForID);
		List parametersForTrack = Arrays.asList(trackName);
		List<Map<String, Object>> query = query(conn, sqlForID, Collections.EMPTY_LIST);
		ResultSet rs = stmt.executeQuery(sqlForID);
		int trackID = 0;
		while(rs.next()) {
			trackID = rs.getInt("TRACK_id");
		}
		
		
		// add coordinates
//		for (List<Double> pointCoordinates : coordinates){
//			
//			Float pointLat = pointCoordinates.get(0).floatValue();
//			Float pointLng = pointCoordinates.get(1).floatValue();
//			String sqlForCoordinates = "INSERT INTO COORDINATES(TRACK_ID, LAT, LNG) VALUES(?,?,?)";
//			List parametersForCoordinates = Arrays.asList(trackID, pointLat, pointLng);
//    		int numRowsUpdatedForCoordinates = update(conn, sqlForCoordinates, parametersForCoordinates);
//    		conn.commit();
//		}
		
		
		
//    	for(String key : POIs.keySet()){
//    		String name = key;
//    		Double lat = POIs.get(key).get(0);
//    		Double lng = POIs.get(key).get(1);
//    		String sql = "INSERT INTO POI(NAME, LAT, LNG) VALUES(?,?,?)";
//    		List parameters = Arrays.asList(name, lat, lng);
//    		int numRowsUpdated = update(conn, sql, parameters);
//    		conn.commit();
//    	}
//    	INSERT INTO foo (auto,text)
//        VALUES(NULL,'text');         # generate ID by inserting NULL
//    INSERT INTO foo2 (id,text)
//        VALUES(LAST_INSERT_ID(),'text');  # use ID in second table
//    LAST_INSERT_ID()
    }
    private static void addPOIs(Map<String, List<Double>> POIs) throws SQLException{
    	Connection conn = getConnection();
    	conn.setAutoCommit(false);
    	for(String key : POIs.keySet()){
    		String name = key;
    		Double lat = POIs.get(key).get(0);
    		Double lng = POIs.get(key).get(1);
    		String sql = "INSERT INTO POI(NAME, LAT, LNG) VALUES(?,?,?)";
    		List parameters = Arrays.asList(name, lat, lng);
    		int numRowsUpdated = update(conn, sql, parameters);
    		conn.commit();
    	}
    }
    public static List<Map<String, Object>> map(ResultSet rs) throws SQLException {
        List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
        try {
            if (rs != null) {
                ResultSetMetaData meta = rs.getMetaData();
                int numColumns = meta.getColumnCount();
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<String, Object>();
                    for (int i = 1; i <= numColumns; ++i) {
                        String name = meta.getColumnName(i);
                        Object value = rs.getObject(i);
                        row.put(name, value);
                    }
                    results.add(row);
                }
            }
        } finally {
            close(rs);
        }
        return results;
    }
    public static List<Map<String, Object>> query(Connection connection, String sql, List<Object> parameters) throws SQLException {
        List<Map<String, Object>> results = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject(++i, parameter);
            }
            rs = ps.executeQuery();
            results = map(rs);
        } finally {
            close(rs);
            close(ps);
        }
        return results;
    }
	private static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			String url = "jdbc:mysql://ec2-54-164-116-207.compute-1.amazonaws.com:3306/emmprojekttest";
			connection = DriverManager.getConnection(url, "andreas", "parool");
		} catch (Exception e) {
			// connection problem
			e.printStackTrace();
		}
		return connection;
	}
	public static int update(Connection connection, String sql, List<Object> parameters) throws SQLException {
        int numRowsUpdated = 0;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);

            int i = 0;
            for (Object parameter : parameters) {
                ps.setObject(++i, parameter);
            }
            numRowsUpdated = ps.executeUpdate();
        } finally {
            close(ps);
        }
        return numRowsUpdated;
    }
	public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1.609344;
        return (dist);
      }

      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }

      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }

}
