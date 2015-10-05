package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

	public static ArrayList <Station> allStations = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		loadStations(); // Load stations from CSV file
		Scanner in = new Scanner(System.in);
		
		Location loc = Location.createLocation(in); // Get a starter Location
		System.out.println("Enter the zone you wish to travel too:(If you just want the nearest type 0)");
		
		float zone = Float.parseFloat(in.nextLine()); 
		
		Station[] nearestStation;
		
		//if(zone == 0) nearestStation = loc.nearestStation(allStations);
		//else nearestStation = loc.nearestStation(allStations,zone);
		
		nearestStation = loc.nearestStations(allStations, 30);
		
		for (int i = 0; i < nearestStation.length; i++) {
			
			System.out.println("The Nearest Station is " + nearestStation[i].getName() + " at zone " + nearestStation[i].getZone());
		}
		
		
		in.close();
	}

	public static void loadStations() throws IOException
	{
		
		final FileReader inputCSV = new FileReader("stationsLatLng.csv");
		final BufferedReader input = new BufferedReader(inputCSV);
		
		String line = "";
		
		while ((line = input.readLine()) != null) {
			
			String[] strObj = line.split(",");
			
			String name = strObj[0];
			
			float zone = Float.parseFloat(strObj[1]);
			float lat = Float.parseFloat(strObj[2]);
			float lng = Float.parseFloat(strObj[3]);
			
			
			allStations.add(new Station(name,lat,lng,zone));
			
			
		}
		input.close();
		
	}
	

	
}
