package BdD;

public class Test2 {

	public static void main(String[] args) {
		
		GPS_position gps1 = new GPS_position(2.207477,48.710112);
		GPS_position gps2 = new GPS_position(new double []{52.0, 1.0});
		GPS_position gps3 = new GPS_position(2.207732,48.710096);
		GPS_position gps4 = new GPS_position(52.000000001, 32.00000000001);
		
		System.out.println("gps1 "+gps1.BdDtoString()+" est egal à gps2 "+gps2.BdDtoString()+" : "+gps1.isEqual(gps2)+"");
		System.out.println("gps1 "+gps1.BdDtoString()+" est egal à gps3 "+gps3.BdDtoString()+" : "+gps1.isEqual(gps3)+"");
		System.out.println("gps1 "+gps1.BdDtoString()+" est egal à gps4 "+gps4.BdDtoString()+" : "+gps1.isEqual(gps4)+"");
		System.out.println("gps1 "+gps1.BdDtoString()+" est egal à gps1 "+gps1.BdDtoString()+" : "+gps1.isEqual(gps1)+"");
	}
	
}
