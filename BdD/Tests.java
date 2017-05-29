package BdD;

import java.util.LinkedList;

public class Tests {

	public static void main(String[] args) {
		//test0();
		//test1();
		test3();

	}
	
	public static void test1(){
		LinkedList<String[]> liste = Measure.construction_carte();
		for (String[] l : liste){
			System.out.println("les informations pour l'obstacle "+l[0]+" sont : latitude "+l[1]+" et longitude "+l[2]+" pour un type "+l[3]);
		}
	}
	
	public static void test2(double lat, double longi){
		GPS_position gps = new GPS_position(longi, lat);
		Measure m = Measure.Une_mesure(gps);
		System.out.println("vitesse : "+m.vitesse);
	}
	
	public static void test3(){
		String[][] macro = Measure.macrodonnees();
		for (int i = 0; i < macro.length;i++){
			System.out.println(macro[i][0]+" : "+macro[i][1]);
		}
	}

}
