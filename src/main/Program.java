package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Program {

	public static ArrayList <Locations> allStations = new ArrayList<>();
	
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
		//LocationQuery test = new LocationQuery("South Ruislip",51.554714f,-0.402009f,allStations);
		LocationQuery test = new LocationQuery("South Ruislip",locLatLng[0],locLatLng[1],allStations);
		
		System.out.println(test.nearestStations(20));
		
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
