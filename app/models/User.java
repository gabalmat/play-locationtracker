package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;

@Entity
public class User extends Model {
	
	@OneToMany(mappedBy = "user", cascade=CascadeType.ALL)
	public List<UserLocation> locations = new ArrayList<UserLocation>();
	
	@Id
	public Long id;
	public String username;
	public Double distanceTraveled;
	
	public static final Finder<Long, User> find = new Finder<>(User.class);
	
	public Long getId() {
		return id;
	}
	
	public List<UserLocation> getLocations() {
		return locations;
	}
	
	public void setUsername(String name) {
		username = name;
	}
	
	public Double getDistanceTraveled() {
		return distanceTraveled;
	}
	
	public void setDistanceTraveled(Double distance) {
		distanceTraveled = distance;
	}
}
