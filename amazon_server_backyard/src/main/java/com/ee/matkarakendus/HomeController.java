package com.ee.matkarakendus;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Handles requests for the application home page.
 */
@RestController
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Resource
	private TracksData tracksData;
	
	@RequestMapping("/allTracks")
	public @ResponseBody List<Track> getAllTracks() throws SQLException {
		List<Track> track = tracksData.getAllTracks();
		
		return track;
	}
	
	@RequestMapping("/test")
	public @ResponseBody String sayHello() {
		return "{\"tracks\":[{\"id\":\"3\",\"description\":\"abc\",\"length\":1234.78,\"longitude\":-0.65464,\"latitude\":0.65465,\"ascend\":23.57,\"type\":1,\"isOpen\":false},{\"id\":\"1\",\"description\":\"Hästi pikk\",\"length\":8787,\"longitude\":-0.10101,\"latitude\":0.010101,\"ascend\":23.57,\"type\":2,\"isOpen\":true},{\"id\":\"2\",\"description\":\"Algajale parim\",\"length\":4564.57,\"longitude\":-0.10101,\"latitude\":0.010101,\"ascend\":0.17,\"type\":3,\"isOpen\":true}]}";
	}
	
	@RequestMapping("/allPOIs")
	public @ResponseBody List<POI> getAllPOIs() throws SQLException {
		List<POI> POIs = tracksData.getAllPOIs();
		
		return POIs;
	}
	
	@RequestMapping("/TrackCoordinates")
	public @ResponseBody Map<Integer, List<Coordinate>> getTrackCoordinates(@RequestParam(value="id", required=true) int id) throws SQLException {
		Map<Integer, List<Coordinate>> coords;
		if (id == 0){
			coords = tracksData.getAllCoordinates();
		}
		else {
			coords = tracksData.getTrackCoordinates(id);
		}
		
		return coords;
	}
	
	
	
	@RequestMapping("/TrackPOIs")
	public @ResponseBody List<POI> getTrackPOIs(@RequestParam(value="id", required=true) int id) throws SQLException {
		List<POI> POIs;
		if (id == 0){
			POIs = tracksData.getAllPOIs();
		}
		else {
			POIs = tracksData.getTrackPOIs(id);
		}
		
		
		return POIs;
	}
	
	@RequestMapping("/TrackComments")
	public @ResponseBody List<Comment> getTrackComments(@RequestParam(value="id", required=true) int id) throws SQLException {
		List<Comment> comments;
		comments = tracksData.getTrackComments(id);
		return comments;
	}
	
	@RequestMapping("/allCoordinates")
	public @ResponseBody Map<Integer, List<Coordinate>> getAllCoordinates() throws SQLException {
		Map<Integer, List<Coordinate>> coords = tracksData.getAllCoordinates();
		
		return coords;
	}
	
	@RequestMapping("/TrackGPX")
	public @ResponseBody String getTrackGPX(@RequestParam(value="id", required=true) int id) throws SQLException {
		String GPX;
		GPX = tracksData.getTrackGPX(id);
		return GPX;
	}
	
	@RequestMapping("/TrackImages")
	public @ResponseBody List<String> getTrackImages(@RequestParam(value="id", required=true) int id) throws SQLException {
		List<String> images;
		images = tracksData.getTrackImages(id);
		return images;
	}
	
	// test for post data
	@RequestMapping(value="/gpx", method=RequestMethod.POST)
	@ResponseBody
    public String gpxSubmit(@RequestParam(value="gpx", required=true) String gpx, @RequestParam(value="desc", required=true) String desc, @RequestParam(value="county", required=true) String county) {
		try {
			PushToDB.DBinsert(gpx, desc, county);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fukd";
		}
        return "1";
    }
	
	@RequestMapping(value="/addComment", method=RequestMethod.POST)
	@ResponseBody
    public String addComment(@RequestParam(value="comment", required=true) String comment, @RequestParam(value="id", required=true) int id) {
		try {
			PushToDB.addComment(id, comment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fukd";
		}
        return "1";
    }
	
	@RequestMapping(value="/addImage", method=RequestMethod.POST)
	@ResponseBody
    public String addImage(@RequestParam(value="link", required=true) String link, @RequestParam(value="id", required=true) int id) {
		try {
			PushToDB.addImage(id, link);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fukd";
		}
        return "1";
    }
}
