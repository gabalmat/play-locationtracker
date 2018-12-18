package services;

import java.util.List;

import com.fasterxml.jackson.databind.node.ObjectNode;

import models.User;
import models.UserLocation;
import play.db.ebean.Transactional;

public class UserService {
	
	@Transactional
	public Long createUser(ObjectNode object) {
		User user = new User();
		
		user.setUsername(object.get("username").asText());
		user.setDistanceTraveled(object.get("distanceTraveled").asDouble());
		user.save();
		
		return user.getId();
	}
	
	@Transactional
	public int getUserWaitTime(Long userId) {
		int waitTime = 0;
		
		// get the 5 most recent location updates belonging to user
		List<UserLocation> lastFive = User.find.byId(userId).
				getLocations().subList(0, 5);
		
		// get the average speed of the last 5 records
		Double averageSpeed = lastFive.stream().mapToDouble(l -> l.speed).average().orElse(0.0);
		
		if (averageSpeed <= 1.0) {
			waitTime = 5;	// 5 seconds
		} else if (averageSpeed >= 20.0) {
			waitTime = 1;	// 1 second
		} else if (1.0 < averageSpeed && averageSpeed < 20.0) {
			// interpolate to get appropriate wait time
			int x=averageSpeed.intValue(), x1=1, y1=5, x2=20, y2=1;
			waitTime = y1 + (x-x1) * ((y2-y1)/(x2-x1));
		}
		
		return waitTime;
	}
	
}
