package main;

import java.util.ArrayList;

public class LocationQuery {

	private String startName;
	private Location startLocation; // Location has lat, lng, validLocation
	private String endName;
	private Location endLocation;
	private ArrayList<Station> allStations = new ArrayList<Station>();
	private int rideDuration;
	private float zoneStart;
	private float zoneEnd;
	
	public LocationQuery(String startName, String endName, int rideDuration, ArrayList<Station> allStations)
	{
		this.startName = startName;
		this.endName = endName;
		this.rideDuration = rideDuration;
		this.allStations = allStations;
		
		startLocation = new Location(startName);
		endLocation = new Location(endName);
		
		if(!startLocation.isSet() && !endLocation.isSet())
		{
			if(!startLocation.isSet()) System.out.println(startName + " is not a valid Address");
			
			if(!endLocation.isSet()) System.out.println(endName + " is not a valid address");
			
		}
		else
		{
			zoneStart = startLocation.nearestStation(allStations).getZone();
			zoneEnd = endLocation.nearestStation(allStations).getZone();
			
		}
	}
}
