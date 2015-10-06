package main;

import java.io.IOException;
import java.util.ArrayList;

public class Program {

	public static ArrayList <Station> allStations = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		LocationQuery test = new LocationQuery("HA4 6RZ","Liverpool Lime Street",30);
		
		test.showInfo();
	}

	

	
}
