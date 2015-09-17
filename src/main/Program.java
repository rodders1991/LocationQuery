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
		
		loadStations();
		float[] locLatLng = new float[2];
		Scanner in = new Scanner(System.in);
		
		do{
			System.out.println("Enter a location");
			String location = in.nextLine();
			
		LocationFinder loc = new LocationFinder(location, "AIzaSyA2OxtZG2jUkE3-2pb6R4rc-VcWn3x35LQ");
		locLatLng = loc.latLng();
		
		if(locLatLng[0] == 999f) System.out.println("Error with the location");
		
		}while(locLatLng[0] == 999f);
				
		Station place = new Station("South Ruislip", locLatLng[0],locLatLng[1],0);
		
		Station nearestStation = place.nearestStation(allStations,7);
		
		System.out.println("The Nearest Station is " + nearestStation.getName() + " at zone " + nearestStation.getZone());
		
		in.close();
	}

	public static void loadStations() throws IOException
	{
		
		final FileReader inputCSV = new FileReader("stationsLatLng.csv");
		final BufferedReader input = new BufferedReader(inputCSV);
		
		String line = "";
		
		while ((line = input.readLine()) != null) {
			
			String[] strObj = line.split(",");
			
			String name = strObj[0];//.substring(1, (strObj[0].length() - 1));
			
			float zone = Float.parseFloat(strObj[1]);
			float lat = Float.parseFloat(strObj[2]);
			float lng = Float.parseFloat(strObj[3]);
			
			
			allStations.add(new Station(name,lat,lng,zone));
			
			
		}
		input.close();
		
	}
	

	
}
