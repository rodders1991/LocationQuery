package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ZoneQuery extends RouteQuery {

	private Station stationStart;
	private Station stationEnd;
	private ArrayList<Station> allStations = new ArrayList<Station>();
	private int[] zonesToQuery;
	
	
	public ZoneQuery(String startName,String endName)
	{
		super(startName,endName);
		allStations = loadStations();
		
		stationStart = super.getStartLocation().nearestStation(allStations);
		stationEnd = super.getEndLocation().nearestStation(allStations);
		
		if(stationStart.getZone() > stationEnd.getZone())
		{
			int start = (int)stationStart.getZone();
			int end = Math.round(stationEnd.getZone());
			
			if(start != end)
			{
				int difference = start - end;
				
				zonesToQuery = new int[difference];
				
				for (int i = 0; i < difference; i++) {
					zonesToQuery[i] = end;
					end++;
					
				}
				
			}
		}
		else if(stationStart.getZone() == stationEnd.getZone())
		{
			
		}
		else
		{
			int start = Math.round(stationStart.getZone());
			int end = (int)stationEnd.getZone();
			
			int difference = end - start;
			
			zonesToQuery = new int[difference];
			
			for (int i = 0; i < difference; i++) {
				zonesToQuery[i] = start;
				start++;
				
			}
		}
		
	}
	
	
	public void showInfo()
	{
		Station[] small = nearestStationsBetween(5);
		
		System.out.println("Nearest station Between locations are:");
		
		for(Station a : small)
		{
			System.out.println(a.getName());
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
	
	
	/* Think Triangles... there is an error in this functions, need to look into more maths, maybe compare
	 * the angle??
	 * 
	 * SUCCESSS I Used the mid-point as my reference (((x1+x2)/2),((y1+y2)/2))
	 */
	private Station[] nearestStationsBetween( int sample)
	{
		Station[] small = new Station[sample];
		double[] values = new double[sample];
		
		for (int i = 0; i < allStations.size(); i++) {
			
			//double d1 = MapUtil.getDistance(super.getStartLocation().getLat(), super.getStartLocation().getLng(), allStations.get(i).getLat(), allStations.get(i).getLng());
			//double d2 = MapUtil.getDistance(super.getEndLocation().getLat(), super.getEndLocation().getLng(), allStations.get(i).getLat(), allStations.get(i).getLng());
			
			float lat1 = (super.getStartLocation().getLat() + super.getEndLocation().getLat()) / 2;
			float lng1 = (super.getStartLocation().getLng() + super.getEndLocation().getLng()) / 2;
			double d = MapUtil.getDistance(lat1,lng1,allStations.get(i).getLat(),allStations.get(i).getLng());
			
			for (int j = 0; j < sample; j++) {
				
				if(small[j] == null)
				{
					values[j] = d;
					small[j] = allStations.get(i);
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
					small[j] = allStations.get(i);
					break;
					
				}
				
			}
		}
		
		
		
		return small;
	}
}
