package com.ee.matkarakendus.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ee.matkarakendus.objects.Track;
import com.ee.matkarakendus.utils.Codec;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

@SuppressWarnings("serial")
public class TracksServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("application/json");
		JSONObject json = Codec.EncodeTracksList(getAllTracks());
		resp.getWriter().print(json);

		/*
		 * DatastoreService datastore =
		 * DatastoreServiceFactory.getDatastoreService();
		 * 
		 * List<Entity> entities = new ArrayList<Entity>();
		 * 
		 * Entity t1 = new Entity("Track"); t1.setProperty("id", "1");
		 * t1.setProperty("description", "jejee"); t1.setProperty("latitude",
		 * 0.010101); t1.setProperty("longitude", -0.101010);
		 * t1.setProperty("length", 8787.0); t1.setProperty("type", 2);
		 * t1.setProperty("open", true); t1.setProperty("ascend", 23.57);
		 * 
		 * Entity t2 = new Entity("Track"); t2.setProperty("id", "2");
		 * t2.setProperty("description", "midagimidagi");
		 * t2.setProperty("latitude", 0.010101); t2.setProperty("longitude",
		 * -0.101010); t2.setProperty("length", 4564.57); t2.setProperty("type",
		 * 3); t2.setProperty("open", true); t2.setProperty("ascend", 0.17);
		 * 
		 * Entity t3 = new Entity("Track"); t3.setProperty("id", "3");
		 * t3.setProperty("description", "abc"); t3.setProperty("latitude",
		 * 0.65465); t3.setProperty("longitude", -0.65464);
		 * t3.setProperty("length", 1234.78); t3.setProperty("type", 1);
		 * t3.setProperty("open", false); t3.setProperty("ascend", 23.57);
		 * 
		 * entities.add(t1); entities.add(t2); entities.add(t3);
		 * 
		 * datastore.put(entities);
		 */
	}

	public List<Track> getAllTracks() {
		DatastoreService datastore = DatastoreServiceFactory
				.getDatastoreService();

		Query q = new Query("Track");

		PreparedQuery pq = datastore.prepare(q);

		List<Track> tracks = new ArrayList<Track>();

		for (Entity result : pq.asIterable()) {
			try {
				Track track = new Track();
				track.setId((String) result.getProperty("id"));
				track.setDescription((String) result.getProperty("description"));
				track.setLatitude((Double) result.getProperty("latitude"));
				track.setLongitude((Double) result.getProperty("longitude"));
				track.setLength((Double) result.getProperty("length"));
				track.setType((Long) result.getProperty("type"));
				track.setIsOpen((Boolean) result.getProperty("open"));
				track.setAscend((Double) result.getProperty("ascend"));
				tracks.add(track);
			} catch (Exception ex) {
				continue;
			}

		}
		return tracks;
	}
}