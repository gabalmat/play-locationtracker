package models;

import java.util.*;
import javax.persistence.*;

import io.ebean.*;

@Entity
public class UserLocation extends Model {
	
	@ManyToOne(cascade = CascadeType.ALL)
	public User user;
	
	@Id
	public Long id;
	public Date timestamp;
	public Double latitude;
	public Double longitude;
	public Float speed;

	public static final Finder<Long, UserLocation> find = new Finder<>(UserLocation.class);
	
	public Long getId() {
		return id;
	}
	
	public void setUser(User newUser) {
		user = newUser;
	}
	
	public void setLatitude(Double lat) {
		latitude = lat;
	}
	
	public void setLongitude(Double lon) {
		longitude = lon;
	}
	
	public void setSpeed(Float speed) {
		this.speed = speed;
	}
	
	public void setTimestamp(Date date) {
		timestamp = date;
	}
	
	public User getUser() {
		return user;
	}
}
