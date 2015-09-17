package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class Location {

	private String address;
	private float lat;
	private float lng;
	private boolean isSet;
	
	public String getAddress() {
		return address;
	}
	public float getLat() {
		return lat;
	}
	public float getLng() {
		return lng;
	}
	public boolean isSet() {
		return isSet;
	}
	
	public Location(String address)
	{
		try {
		float[] loc = latLng(address,"AIzaSyA2OxtZG2jUkE3-2pb6R4rc-VcWn3x35LQ");
		
		if(loc[0] != 999f)
		{
			this.address = address;
			this.lat = loc[0];
			this.lng = loc[1];
			this.isSet = true;
		}else
		{
			this.isSet = false;
		}
		
		}
		catch(IOException e)
		{
			this.isSet = false;
		}
	}
	
	public static float[] latLng(String name, String APIKEY) throws IOException
	{
		
		float[] result = new float[2];
		
		String inName = name.replace(" ", "+");
		
		URL url = null;
		try
		{
		url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+inName+"&key="+APIKEY);
		
		} catch(MalformedURLException e)
		{
			System.out.println("URL ERROR: " + e);
			return result;
		}
		
		   InputStream is = url.openStream();
		   
		    try {
	
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		      String jsonText = readAll(rd);
		      JSONObject json = new JSONObject(jsonText);
		      
		      JSONArray loc = (JSONArray) json.get("results"); // If it's surrounded by [] cast it to JSONArray
		      
		      
		      if(json.get("status").equals("OK")) { 
		    	  
		    	   JSONObject locOb = (JSONObject)loc.get(0);
				      locOb = (JSONObject) locOb.get("geometry"); // If it's surrounded by {} cast it to JSONObject
				      locOb = (JSONObject) locOb.get("location");
				      
				      Object lat = locOb.get("lat"); //Once the final value is obtained convert it use it as an object
				      Object lng = locOb.get("lng");
				      
				      
				     result[0] = Float.parseFloat(lat.toString());
				     result[1] = Float.parseFloat(lng.toString());
				      
				      return result;
				      
		    	  }
		      else{
		      
		    result[0] = 999f;
		    return result; 
		    
		      }
		    } finally {
		      is.close();
	
		    }
		
	}
	
	 private static String readAll(Reader rd) throws IOException {
		    StringBuilder sb = new StringBuilder();
		    int cp;
		    while ((cp = rd.read()) != -1) {
		      sb.append((char) cp);
		    }
		    return sb.toString();
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
		
		
		public static Location createLocation(Scanner in)
		{
			// Loop to get a valid Location
			Location loc;
			do{
				System.out.println("Enter a location");
				String location = in.nextLine();
				
			loc = new Location(location);
		
			
			if(!loc.isSet()) System.out.println("Error with the location");
			
			}while(!loc.isSet());
			
			return loc;
		}
	
}
