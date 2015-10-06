package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LocationQuery {

	private String startName;
	private Location startLocation; // Location has lat, lng, validLocation
	private String endName;
	private Location endLocation;
	private ArrayList<Station> allStations = new ArrayList<Station>();
	private int rideDuration;
	private int quickestTransit;
	private Station stationStart;
	private Station stationEnd;
	private final String APIKEY = "AIzaSyA2OxtZG2jUkE3-2pb6R4rc-VcWn3x35LQ";
	
	public LocationQuery(String startName, String endName, int rideDuration)
	{
		this.startName = startName;
		this.endName = endName;
		this.rideDuration = rideDuration;
		allStations = loadStations();
		
		startLocation = new Location(startName);
		endLocation = new Location(endName);
		
		if(!startLocation.isSet() && !endLocation.isSet())
		{
			if(!startLocation.isSet()) System.out.println(startName + " is not a valid Address");
			
			if(!endLocation.isSet()) System.out.println(endName + " is not a valid address");
			
		}
		else
		{
			stationStart = startLocation.nearestStation(allStations);
			stationEnd = endLocation.nearestStation(allStations);
			quickestTransit = findQuickestTransit();                                                                                                                                                                                                                                                                                                                                                                                                                                                              
		}
	}
	
	private ArrayList<Station> loadStations()
	{
		ArrayList<Station> stations = new ArrayList<Station>();
		
		try{
		final FileReader inputCSV = new FileReader("stationsLatLng.csv");
		final BufferedReader input = new BufferedReader(inputCSV);
		
		
		
		String line = "";
		
		while ((line = input.readLine()) != null) {
			
			String[] strObj = line.split(",");
			
			String name = strObj[0];
			
			float zone = Float.parseFloat(strObj[1]);
			float lat = Float.parseFloat(strObj[2]);
			float lng = Float.parseFloat(strObj[3]);
			
			
			stations.add(new Station(name,lat,lng,zone));
			
			
		}
		input.close();
		}
		catch(IOException e)
		{
			
		}
		
		return stations;
	}
	
	public void showInfo()
	{
		if(startLocation.isSet() && endLocation.isSet())
		{
			System.out.printf("Nearest station to %s is %s zone %f",startName,stationStart.getName(),stationStart.getZone());
			System.out.printf("\nNearest station to %s is %s zone %f",endName,stationEnd.getName(),stationEnd.getZone());
			System.out.printf("\nQuickest route by transit takes %d mins", quickestTransit);
		}
	}
	
	private int findQuickestTransit()
	{
		String inStart = startName.replace(' ', '+');
		String inEnd = endName.replace(' ', '+');
		
		URL url = null;
		
		try
		{
		url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+inStart+"&destination="+inEnd+"&mode=transit"+"&key="+APIKEY);
		
		} catch(MalformedURLException e)
		{
			System.out.println("URL ERROR: " + e);
			return 0;
		}
		
		InputStream is = null;
		try {
		is = url.openStream();
		   
	    

	    BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      
	     
	      
	      if(json.get("status").equals("OK")) { 
	    	  
	      JSONArray loc = (JSONArray) json.get("routes"); // If it's surrounded by [] cast it to JSONArray
	      JSONObject loc1 = (JSONObject) loc.get(0);
	      JSONArray loc2 = (JSONArray) loc1.get("legs");
	      JSONObject loc3 = (JSONObject) loc2.get(0);
	      JSONObject loc4 = (JSONObject)loc3.get("duration");
	      
	      Object duration = loc4.get("value");
	      
	      is.close();
	      return Integer.parseInt(duration.toString()) / 60;
			      
	    	  }
	      else{
	      
	    //result[0] = 999f;
	    	  System.out.println(json.get("status").toString());
	    return 0; 
	    
	      }
	    }catch(JSONException e)
	    {
	    	System.out.println("JSON ERROR: " + e);
	    	return 0;
	    }
		catch(IOException e)
		{
			System.out.println("IO ERROR: " + e);
			return 0;
		}
	    
		
		
		
	}
	
	private String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
}
