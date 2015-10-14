package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RouteQuery {

	private final Location startLocation; // Location has lat, lng, validLocation
	private final Location endLocation;
	private final int quickestTransit;
	private final int quickestBike;
	private final boolean bikeQuicker;
	private final int timeQuicker;
	
	private final String APIKEY = "AIzaSyA2OxtZG2jUkE3-2pb6R4rc-VcWn3x35LQ";
	
	public RouteQuery(String startName, String endName)
	{
		startLocation = new Location(startName);
		endLocation = new Location(endName);
		
		
		
		if(!validLocations(startLocation,endLocation))
		{
			if(!startLocation.isSet()) System.out.println(startName + " is not a valid Address");
			
			if(!endLocation.isSet()) System.out.println(endName + " is not a valid address");
			
		quickestTransit = 0;
		quickestBike = 0;
		bikeQuicker = false;
		timeQuicker = 0;
		
		}
		else
		{
			quickestTransit = findQuickestTransit();
			quickestBike = findQuickestBike();
			
			timeQuicker = quickestTransit - quickestBike;
			
			if(timeQuicker >= 0) bikeQuicker = true;
			else bikeQuicker = false;
			
		}
	}
	
	protected String getAPIKEY()
	{
		return APIKEY;
	}
	
	protected Location getStartLocation()
	{
		return startLocation;
	}
	
	protected Location getEndLocation()
	{
		return endLocation;
	}
	
	private boolean validLocations(Location start, Location end)
	{
		if(start.isSet() && start.isSet())
		{
			return true;
		}else
		{
			return false;
		}
		
	}
	
	
	public void showInfo()
	{
		if(validLocations(startLocation,endLocation))
		{
			//System.out.printf("Nearest station to %s is %s zone %f",startLocation.getAddress(),stationStart.getName(),stationStart.getZone());
			//System.out.printf("\nNearest station to %s is %s zone %f",endLocation.getAddress(),stationEnd.getName(),stationEnd.getZone());
			System.out.printf("Quickest route by transit takes %d mins", quickestTransit);
			System.out.printf("\nQuickest route by cycle takes %d mins\n", quickestBike);
			
			if(bikeQuicker)
			{
				System.out.println("Congrats it is quicker to cycle to your location than public transport");
				System.out.printf("It's %d mins Quicker!!!",timeQuicker);
				
			}else
			{
				System.out.println("Sorry it takes longer to travel by bike");
				System.out.printf("But only by %d mins!!!", Math.abs(timeQuicker));
			}
		}
	}
	
	private int findQuickestTransit()
	{
		
		URL url = null;
		
		try
		{
		url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+endLocation.getLat()+","+endLocation.getLng()+"&destination="+startLocation.getLat()+","+startLocation.getLng()+"&mode=transit&key="+APIKEY);
		
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
	      
	      String duration = loc4.get("value").toString();
	      
	      is.close();
	      return Integer.parseInt(duration) / 60;
			      
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
	
	private int findQuickestBike()
	{
		
		URL url = null;
		
		try
		{
		url = new URL("https://maps.googleapis.com/maps/api/directions/json?origin="+endLocation.getLat()+","+endLocation.getLng()+"&destination="+startLocation.getLat()+","+startLocation.getLng()+"&mode=bicycling&key="+APIKEY);
		
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
	      
	      String duration = loc4.get("value").toString();
	      
	      is.close();
	      return Integer.parseInt(duration) / 60;
			      
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
	
	protected String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }
	
}
