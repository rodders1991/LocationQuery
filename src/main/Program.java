package main;

import java.io.IOException;
import java.util.ArrayList;

public class Program {

	public static ArrayList <Station> allStations = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		RouteQuery test = new RouteQuery("SW4 4AF","HA4 6RZ");
		
		
		test.showInfo();
	}

	

	
}
