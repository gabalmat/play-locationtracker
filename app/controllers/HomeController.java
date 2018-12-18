package controllers;

import com.google.inject.Inject;

import models.User;
import models.UserLocation;
import play.mvc.*;
import services.UserLocationService;
import services.UserService;
import play.libs.Json;
import play.data.DynamicForm;
import play.data.FormFactory;

import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's home page.
 */
public class HomeController extends Controller {
	
	@Inject UserService userService;
	@Inject UserLocationService userLocationService;

    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result index() {
        return ok("Hello From eclipse");
    }
	
	@Inject FormFactory formFactory1;
	public Result handleupdatesuser() {
		DynamicForm dynamicForm = formFactory1.form().bindFromRequest();
		String username = dynamicForm.get("username");
		String distance = dynamicForm.get("distanceTraveled");
		
		ObjectNode result = Json.newObject();
		result.put("username", username);
		result.put("distanceTraveled", distance);
		
		Long newUserId = userService.createUser(result);
	
		result.put("userId", newUserId);
		
		return ok(result);
	}
	
	@Inject FormFactory formFactory2;
	public Result handleupdatesuserlocation() {
		DynamicForm dynamicForm = formFactory2.form().bindFromRequest();
		String userId = dynamicForm.get("userId");
		String speed = dynamicForm.get("speed");
		String lat = dynamicForm.get("latitude");
		String lon = dynamicForm.get("longitude");
		
		ObjectNode result = Json.newObject();
		result.put("userId", userId);
		result.put("speed", speed);
		result.put("latitude", lat);
		result.put("longitude", lon);
		
		UserLocation newLocation = userLocationService.createUserLocation(result);
		User user = newLocation.getUser();
		
		Double userDist = user.getDistanceTraveled();
		int waitTime = 0;
		if (user.getLocations().size() > 5) {
			waitTime = userService.getUserWaitTime(user.getId());
		}
		
		result.put("locationId", newLocation.getId());
		result.put("distanceTraveled", userDist);
		result.put("waitTime", waitTime);
		
		// Print results to console
		System.out.println("Username: " + user.username);
		System.out.println("Location: " + String.format("%.2f", newLocation.latitude) + ", " + String.format("%.2f", newLocation.longitude));
		System.out.println("Total Distance (m): " + String.format("%.2f", userDist));
		System.out.println("Average Speed: " + newLocation.speed);
		System.out.println("Wait Time Sent: " + waitTime);
		System.out.println();
		
		return ok(result);
	}

}
