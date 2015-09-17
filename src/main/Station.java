package main;

import java.util.ArrayList;

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
	public Station nearestStation(ArrayList<Station> allStations)
	{
	
		String inName = "";
		float inLat = 0;
		float inLng = 0;
		float inZone = 0;
		double testValue = Double.MAX_VALUE;
		
		for (int i = 0; i < allStations.size(); i++) {
			
			double d = MapUtil.getDistance(lat, lng, allStations.get(i).getLat(), allStations.get(i).getLng());
			
			if(d < testValue)
			{
				inName = allStations.get(i).getName();
				inLat = allStations.get(i).getLat();
				inLng = allStations.get(i).getLng();
				inZone = allStations.get(i).getZone();
				
				testValue = d;
			}
			
		}
		
		Station result = new Station(inName,inLat,inLng,inZone);
		
		return result;
	}
	
	
	
	public Station nearestStation(ArrayList<Station> allStations,float aZone)
	{
		
		if(aZone <= 9)
		{
			String inName = "";
			float inLat = 0;
			float inLng = 0;
			float inZone = 0;
			double testValue = Double.MAX_VALUE;
			
			for (int i = 0; i < allStations.size(); i++) {
				
				double d = MapUtil.getDistance(lat, lng, allStations.get(i).getLat(), allStations.get(i).getLng());
				
				if(d < testValue && allStations.get(i).getZone() == aZone)
				{
					inName = allStations.get(i).getName();
					inLat = allStations.get(i).getLat();
					inLng = allStations.get(i).getLng();
					inZone = allStations.get(i).getZone();
					
					testValue = d;
				}
				
			}
			
			Station result = new Station(inName,inLat,inLng,inZone);
			
			return result;
		}
		else
		{
			Station result = new Station();
			return result;
		}
	}
	
	
}
