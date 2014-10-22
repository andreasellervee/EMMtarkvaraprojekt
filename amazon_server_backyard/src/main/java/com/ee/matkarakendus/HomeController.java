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
	public @ResponseBody List<Coordinate> getTrackCoordinates(@RequestParam(value="id", required=true) int id) throws SQLException {
		List<Coordinate> coords = tracksData.getTrackCoordinates(id);
		
		return coords;
	}
	@RequestMapping("/allCoordinates")
	public @ResponseBody Map<Integer, List<Coordinate>> getAllCoordinates() throws SQLException {
		Map<Integer, List<Coordinate>> coords = tracksData.getAllCoordinates();
		
		return coords;
	}
}
