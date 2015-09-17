package main;

public class Station {

	private String name;
	private float lat;
	private float lng;
	private float zone;
	
	public String getName() {
		return name;
	}
	public float getLat() {
		return lat;
	}
	public float getLng() {
		return lng;
	}
	public float getZone() {
		return zone;
	}
	
	public Station(String name, float lat, float lng,float zone) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.zone = zone;
	}
	
	public Station()
	{
		
	}
	
	
}
