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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Location {

	private String address;
	private float lat;
	private float lng;
	private boolean isSet;
	
	public float getLat() {
		return lat;
	}
	public float getLng() {
		return lng;
	}
	public String getAddress()
	{
		return address;
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
		catch (JSONException e)
		{
			this.isSet = false;
		}
	}
	
	private float[] latLng(String name, String APIKEY) throws IOException, JSONException
	{
		
		float[] result = new float[2];
		
		String inName = name.replace(" ", "+");
		
		URL url = null;
		try
		{
		url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address="+inName+"&components=country:GB&key="+APIKEY);
		
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
	
	 private String readAll(Reader rd) throws IOException {
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
		
		
		public Station[] nearestStations(ArrayList<Station> large, int sample)
		{
		
			Station[] small = new Station[sample];
			double[] values = new double[sample];
			
			for (int i = 0; i < large.size(); i++) {
				
				double d = MapUtil.getDistance(lat, lng, large.get(i).getLat(), large.get(i).getLng());
				
				for (int j = 0; j < sample; j++) {
					
					if(small[j] == null)
					{
						values[j] = d;
						small[j] = large.get(i);
						break;
						
					}
					else if(d < values[j])
					{
						Station[] copySmall = small.clone();
						double[] copyValues = values.clone();
						
						for (int k = j+1; k < sample; k++){
							small[k] = copySmall[k-1];
							values[k] = copyValues[k-1];
						}
						
						values[j] = d;
						small[j] = large.get(i);
						break;
						
					}
					
				}
			}
			
			return small;
			
		}
		
		
		public Station[] nearestStations(ArrayList<Station> large, int sample, float aZone)
		{
		
			Station[] small = new Station[sample];
			double[] values = new double[sample];
			
			for (int i = 0; i < large.size(); i++) {
				
				double d = MapUtil.getDistance(lat, lng, large.get(i).getLat(), large.get(i).getLng());
				
				for (int j = 0; j < sample; j++) {
					
					if(small[j] == null && large.get(i).getZone() == aZone)
					{
						values[j] = d;
						small[j] = large.get(i);
						break;
						
					}
					else if(d < values[j] && large.get(i).getZone() == aZone)
					{
						Station[] copySmall = small.clone();
						double[] copyValues = values.clone();
						
						for (int k = j+1; k < sample; k++){
							small[k] = copySmall[k-1];
							values[k] = copyValues[k-1];
						}
						
						values[j] = d;
						small[j] = large.get(i);
						break;
						
					}
					
				}
			}
			
			return small;
			
		}
		
	
}
