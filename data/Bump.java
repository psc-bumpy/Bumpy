package data;

import com.lynden.gmapsfx.service.geocoding.GeocodingResult;

import BdD.Measure;
import bumpy.ui.GraphicManager;
import bumpy.ui.Map;
import javafx.util.Pair;
import netscape.javascript.JSObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;

public class Bump 
{
	private double latitude;
	private double longitude;
	private String location;
	LinkedList<Record> measures;
	String type;
	Marker marker;
	int nbDePassages;
	static int sum=0;
	public static LinkedList<Pair<String,Integer>> stats;
	public Bump(double lat,double lon,String t,int nb)
	{
		latitude=lat;longitude=lon;type=t;nbDePassages=nb;location=null;
	}
	public static void loadStats(){
		stats=new LinkedList<Pair<String,Integer>>();
		String[][] macrodonnees=Measure.macrodonnees();
		for(int i=0;i<macrodonnees.length;i++){
			stats.add(new Pair(macrodonnees[i][0],Integer.parseInt(macrodonnees[i][1])));
			sum+=Integer.parseInt(macrodonnees[i][1]);
		}
	}
	static public int getSum(){return sum;}
	public double getLatitude(){return latitude;}
	public double getLongitude(){return longitude;}
	public String getType(){return type;}
	public int getNbPassages(){return nbDePassages;}
	public String getLocation(){return location;}
	public void addRecord(Record record){measures.add(record);}
	public LinkedList<Record> getRecords(){return measures;}
	public void setLocation(String s){location=s;}
	
	public static HashMap<Double,Bump> loadBumps(){
		LinkedList<String[]> temp=Measure.construction_carte();
		HashMap<Double,Bump> bumps=new HashMap<Double,Bump>();
		for(String[] s:temp){
			Bump b=new Bump(Double.parseDouble(s[0]),Double.parseDouble(s[1]),s[2],Integer.parseInt(s[3]));
			bumps.put(b.getLatitude(), b);
		}
		return bumps;
	}
}

