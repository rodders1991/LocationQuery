package main;

public class Locations {

	private String name;
	private float lat;
	private float lng;
	
	public String getName() {
		return name;
	}
	public float getLat() {
		return lat;
	}
	public float getLng() {
		return lng;
	}
	
	public Locations(String name, float lat, float lng) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
	}
	
	
	
}
