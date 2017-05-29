package BdD;

import java.util.LinkedList;

public class Test4Gestion {

	public static void main(String[] args) {
		test1();
		//test2();

	}
	
	public static void test1(){
		int[][] t = new int[][]{new int[]{41,1},new int[]{42,1}};
		LinkedList<Measure> liste = Measure.Recuperation_mesures_precises (t);
		for (Measure m : liste){
			System.out.println("GPS : "+m.gps);
			System.out.println("AccelZ : "+m.accelZ);
		}
	}
	
	public static void test2(){
		//A faire
	}

}
