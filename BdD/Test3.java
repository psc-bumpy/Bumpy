package BdD;

public class Test3 {
	
	public static void main(String[] args) {
		GPS_position gps1 = new GPS_position(2.14920,48.80173);
		GPS_position gps2 = new GPS_position(new double []{52.0, 1.0});
		GPS_position gps3 = new GPS_position(10.0, 10.0);
		GPS_position gps4 = new GPS_position(2.15099487,48.79877734);
		
		System.out.println(gps1.isPresent()+" "+gps1.num_obstacle());
		System.out.println(gps2.isPresent());
		System.out.println(gps3.isPresent());
		System.out.println(gps4.isPresent()+" "+gps4.num_obstacle());
		
	}
}
