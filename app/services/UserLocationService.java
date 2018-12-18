package services;

import java.util.Date;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import models.UserLocation;
import play.db.ebean.Transactional;

public class UserLocationService {
	
	@Transactional
	public UserLocation createUserLocation(ObjectNode object) {
		UserLocation location = new UserLocation();
		
		Long userId = object.get("userId").asLong();
		User user = User.find.byId(userId);
		
		Double lat = object.get("latitude").asDouble();
		Double lon = object.get("longitude").asDouble();
		Float speed = object.get("speed").floatValue();
		Date timestamp = new Date();
		
		// get the currently stored distance for the user
		Double mDistance = user.distanceTraveled;
		// retrieve the most recently added location record
		if (user.getLocations().size() > 0) {
			UserLocation oldLocation = user.getLocations().get(0);
			Double kDistance = calcDistance(oldLocation.latitude, oldLocation.longitude, lat, lon, 'K');
			mDistance += kDistance * 1000;
		}
		
		location.setUser(user);
		location.setLatitude(lat);
		location.setLongitude(lon);
		location.setSpeed(speed);
		location.setTimestamp(timestamp);
		location.save();
		
		// add the location to the User locations list... add to front of list
		user.getLocations().add(0, location);
		user.setDistanceTraveled(mDistance);
		user.save();
		
		return location;
	}
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  Distance Calculation Using Latitude And Longitude In Java     :*/
	/*::  Author: Unknown, Source: DZone                                :*/
	/*::  https://dzone.com/articles/distance-calculation-using-3       :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private Double calcDistance(Double lat1, Double lon1, Double lat2, Double lon2, char unit) {
		  Double theta = lon1 - lon2;
		  Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  if (unit == 'K') {
		    dist = dist * 1.609344;
		  } else if (unit == 'N') {
		  dist = dist * 0.8684;
		    }
		  return (dist);
	}
	
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts decimal degrees to radians             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private Double deg2rad(Double deg) {
	  return (deg * Math.PI / 180.0);
	}
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	/*::  This function converts radians to decimal degrees             :*/
	/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
	private Double rad2deg(Double rad) {
	  return (rad * 180.0 / Math.PI);
	}

}
