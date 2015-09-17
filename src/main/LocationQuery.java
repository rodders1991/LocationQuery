package main;

import java.util.ArrayList;

public class LocationQuery {

	private String name;
	private float lat;
	private float lng;
	private ArrayList<Station> sample;
	
	
	public LocationQuery(String name, float lat, float lng, ArrayList<Station> sample) {
		this.name = name;
		this.lat = lat;
		this.lng = lng;
		this.sample = sample;
	}


	public String getName() {
		return name;
	}
	
	public String nearestStations(int size)
	{
		String[] resultArr = new String[size]; // To list he station Names
		
		double[] disArr = new double[size]; // To list the Distances
		for (int i = 0; i < size; i++) disArr[i] = Double.MAX_VALUE; // Set to Max Value, this will enable me to check
		
		String result = "";
		
		for (int i = 0; i < sample.size(); i++) {
			
			double d = MapUtil.getDistance(lat,lng,sample.get(i).getLat(),sample.get(i).getLng());
			
			for (int j = 0; j < size; j++) {
				
				if(d < disArr[j]){
					
					double[] copy = new double [size];
					String[] copyStr = new String[size];
					
					for (int k = j-1; k >= 0; k--) 
						{
						copy[k] = disArr[k];
						copyStr[k] = resultArr[k];
						}
					
					for (int j2 = j+1; j2 < size; j2++){
						copy[j2] = disArr[j2-1];
						copyStr[j2] = resultArr[j2-1];
					}
			
					
					copy[j] = d;
					copyStr[j] = sample.get(i).getName();
					
					disArr = copy;
					resultArr = copyStr;
					
					break;
					
				}
				
				
			}
			
		}
		
		for (int i = 0; i < size; i++) {
			result += (i+1) + ". Station Closest is " + resultArr[i] + " it is " + disArr[i] + " metres away.\n"; 
		}
		return result;
	}
	
	

	
}
