package BdD;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.LinkedList;


public class GPS_position {
	double longitude;
	double latitude;
	
	private final static double R = 6400000.0;
	
	// attention : il faut que les minutes de degré aient été converties en degré avant ce code
	// OU : constructeur GPS_position(String[] s) et on utilise les séparateurs ° et '
	// pour faire un calcul qui ramène tout en degré
	
	public GPS_position(double longi, double lat){
		this.longitude = longi;
		this.latitude = lat;
	}
	
	GPS_position(double[] position){
		// il faudra mettre une exception si la longueur du tableau position est différente de 2
		this.longitude=position[0];
		this.latitude=position[1];
	}
	
	//CONSTRUCTEUR AVEC UN STRING (POUR GPS ISSU DE LA BDD)
	
	GPS_position(String s){
		
		boolean conforme = false;
		for (int j = 0;j<s.length();j++){
			if (s.charAt(j)==','){conforme = true;}
		}
		
		if (conforme){
			
		int i = 0;
		char c = s.charAt(i);
		String longi = "";
		while (c!=','){
			longi = longi + c;
			i++;
			c=s.charAt(i);
		}

		i++;
		String lat ="";
		while (i<s.length()){
			lat = lat+s.charAt(i);
			i++;
		}
		
		this.longitude = Double.parseDouble(longi);
		this.latitude = Double.parseDouble(lat);
		}
		
		else{this.longitude = 0;this.latitude = 0;}
	}
	
	String BdDtoString(){
		return ""+this.longitude+""+","+""+this.latitude+"";
	}
	
	//REGARDER SI 2 POSITIONS GPS SONT A PEU PRES EGALES
	
	boolean isEqual(GPS_position p2){
		double b = Math.PI/180.0;
		if (R*Math.acos(Math.sin(this.latitude*b)*Math.sin(p2.latitude*b)+Math.cos(this.latitude*b)*Math.cos(p2.latitude*b)*Math.cos(this.longitude*b-p2.longitude*b))<20){
			return true;
		}
		return false;
	}
	
	//REGARDER SI UNE POSITION GPS EST DEJA PRESENTE DANS LA BDD
	
	boolean isPresent(){
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		boolean b = false;
		try{
			Class.forName("org.postgresql.Driver");     
			cn = DriverManager.getConnection(url, login, password);   
			String sql = "SELECT coordonnees_gps FROM gps_position";
			Statement st = cn.createStatement();
			//Récupération des positions Gps déjà existantes
			ResultSet res = st.executeQuery(sql);
			
			while (res.next()){
				GPS_position gps = new GPS_position(res.getString("coordonnees_gps"));
				if (this.isEqual(gps)){
					b=true;
					return true;
				}
			}
					
			res.close();
			st.close();
			cn.close();
			
			return false;
					
		} 
				
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	return false;
}
	
	// RECUPERER LE NUMERO D'UN OBSTACLE A UNE POSITION GPS DONNEE
	
	Integer num_obstacle(){
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		GPS_position gps_res=new GPS_position(0,0);
		try{
			Class.forName("org.postgresql.Driver");     
			cn = DriverManager.getConnection(url, login, password);   
			String sql = "SELECT coordonnees_gps FROM gps_position";
			Statement st = cn.createStatement();
			//Récupération des positions Gps déjà existantes
			ResultSet res = st.executeQuery(sql);
			while (res.next()){
				GPS_position gps = new GPS_position(res.getString("coordonnees_gps"));
				if (this.isEqual(gps)){
					gps_res = gps;
					break;
				}
			}
					
			res.close();
			st.close();
			
			String sql2 = "SELECT numero_obstacle FROM gps_position WHERE gps_position.coordonnees_gps=?";
			PreparedStatement st2 = cn.prepareStatement(sql2);
			st2.setString(1,gps_res.BdDtoString());
			
			ResultSet res2 = st2.executeQuery();
			res2.next();
			Integer num_obstacle = res2.getInt(1);
			
			res2.close();
			st2.close();
			cn.close();
			
			return num_obstacle;
		} 	
		catch (ClassNotFoundException e) {
				e.printStackTrace();
		} 
		catch (SQLException e) {
				e.printStackTrace();
		}
		return -1;
	}
	
	//RECUPERER LES TYPES DEJA TROUVES POUR UN OBSTACLE LOCALISE
	
	public String type_obstacle(){
		if (this.isPresent())
		{
		Integer n = this.num_obstacle();
				
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		
		String s = "";
		try{
			Class.forName("org.postgresql.Driver");    
			cn = DriverManager.getConnection(url, login, password);   
		
			String sql1 = "SELECT type_obstacle FROM types_obstacles WHERE numero_obstacle = ?";
			PreparedStatement st1 = cn.prepareStatement(sql1);
			st1.setInt(1,n);
			ResultSet res1 = st1.executeQuery();
			res1.first();
			s = res1.getString(1);
			
			res1.close();
			st1.close();
			cn.close();
		}
		catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } 
		catch (SQLException e) {
		      e.printStackTrace();
		    }

		return s;
		}
		else{ 
			return "NO TYPE";
		}
	}
	
	static LinkedList<GPS_position> Tous_les_points(){
		LinkedList<GPS_position> res = new LinkedList<GPS_position>();
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		try{
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(url, login, password);
		
			String sql = "SELECT coordonnees_gps FROM gps_position";
			PreparedStatement st = cn.prepareStatement(sql);
			ResultSet r = st.executeQuery();
			
			while (r.next()){
				res.add(new GPS_position(r.getString(1)));
			}
			
			r.close();
			st.close();
			cn.close();
			
		} 	
		catch (ClassNotFoundException e) {
				e.printStackTrace();
		} 
		catch (SQLException e) {
				e.printStackTrace();
		}
		return res;
	}
	
}
