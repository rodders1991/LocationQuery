package main;

public class MapUtil {

	public static double getDistance (float lat, float lng, float lat1, float lng1){
		/*
		 * Haversine Formula
		  	dlng = lng2 - lng1 
			dlat = lat2 - lat1 
			a = (sin(dlat/2))^2 + cos(lat1) * cos(lat2) * (sin(dlng/2))^2 
			c = 2 * atan2( sqrt(a), sqrt(1-a) ) 
			d = R * c (where R is the radius of the Earth)
		 */
		
		final int RADIUS = 6372800;
		double dLng = Math.toRadians(lng1 - lng);
		double dLat = Math.toRadians(lat1 - lat);
		double newLat = Math.toRadians(lat);
		double newLat1 = Math.toRadians(lat1);
		
		
		double a = Math.pow(Math.sin(dLat/2),2) + Math.cos(newLat) * Math.cos(newLat1) * Math.pow(Math.sin(dLng/2), 2);
		double c = 2* Math.atan2(Math.sqrt(a),Math.sqrt(1 - a));
		double d = RADIUS* c;
		
		return d;
	}
	
	public static double[] bikeTime(Location start, Location end)
	{
		double result[] = new double[5];
		
		
		return result;
	}
}
