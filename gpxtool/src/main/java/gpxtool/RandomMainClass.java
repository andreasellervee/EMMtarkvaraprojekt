package gpxtool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;



public class RandomMainClass {

	public static int PRETTY_PRINT_INDENT_FACTOR = 4;
    public static String TEST_XML_STRING = "";
    public static String jsonPrettyPrintString = "";

    public static void main(String[] args) {
    	
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
			System.out.println(listOfLatLongs);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
}
