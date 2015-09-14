package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Program {

	public static ArrayList <Locations> allStations = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		loadStations();
		
		LocationQuery test = new LocationQuery("South Ruislip",51.554714f,-0.402009f,allStations);
		
		System.out.println(test.nearestStations(8));
		
	}

	public static void loadStations() throws IOException
	{
		
		final FileReader inputCSV = new FileReader("stationsLatLng.csv");
		final BufferedReader input = new BufferedReader(inputCSV);
		
		String line = "";
		
		while ((line = input.readLine()) != null) {
			
			String[] strObj = line.split(",");
			
			String name = strObj[0];//.substring(1, (strObj[0].length() - 1));
			
			float lat = Float.parseFloat(strObj[1]);
			float lng = Float.parseFloat(strObj[2]);
			
			allStations.add(new Locations(name,lat,lng));
			
			
		}
		
	}
	

	
}
