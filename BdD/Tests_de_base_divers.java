package BdD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import tc.TC;

public class Tests_de_base_divers {
	
	public static void main(String[] args) {
		//test1mesure();
		//testInternet();
		//test_creation();
		//rafraichir_BdD();
	}
	
	public static void test1mesure(){
		GPS_position gps1 = new GPS_position(7.16,49.67);
		Measure m1 = new Measure("19janvier2017",gps1,"20.0", Recup.recuperation()[1],Recup.recuperation()[2],Recup.recuperation()[3],Recup.recuperation()[0]);
		m1.save();
	}
	
	//ENREGISTRER LES MESURES D'INTERNET
	public static void testInternet(){
		DownloadManager d = new DownloadManager();
		LinkedList<String> mesures = d.download(Request.GETUNTREATED);
		System.out.println(mesures.size());
		Enregistrer(Reconstituer_mesures(mesures),243);
	}
	public static void Enregistrer(String[] resultats,int n){
		int i = 0;
		for (int k=241;k<244;k++){
			try{
				if (Measure.Recuperer(resultats[k]).save()){i=i+1;}
			} catch (Exception e){
				System.out.println("cette mesure n'est pas valide");
				e.printStackTrace();
			}
		}
		System.out.println(i+" mesure(s) sauvegardée(s) sur "+resultats.length +" mesures");
	}
	public static String[] Reconstituer_mesures(LinkedList<String> liste){
		String[] tab = new String[liste.size()];
		int i = 0;
		for (String s : liste){
			tab[i]=s;
			i++;
		}
		String[] resultats = new String[liste.size()/2];
		for (int j = 0 ; j<liste.size()-1;j=j+2){
			resultats[j/2]=tab[j]+"#"+tab[j+1];
		}
		System.out.println("les mesures en chaine de caractère sont reconstituées");
		return resultats;
	}
	
	
	//METTRE LES MESURES EN FICHIERS EXPLOITABLES
	public static void test_creation(){
		DownloadManager d = new DownloadManager();
		LinkedList<String> mesures = d.download(Request.GETUNTREATED);
		Creer_tous_les_fichiers(Reconstituer_mesures(mesures));
	}
	public static void Creer_tous_les_fichiers(String[] resultats){
		for (int i = 0;i<resultats.length;i++){
			fichiers_txt(resultats[i],i);
		}
	}
	public static void fichiers_txt(String s,int i){
		TC.ecritureDansNouveauFichier("fichier"+i);
		String[] decoupage = s.split("#");
		TC.println(decoupage[0]);
		TC.println(decoupage[1]);
		TC.println(decoupage[2] + " " + decoupage[3]);
		String ligne1 = decoupage[4];
		String[] infos1 = ligne1.split(" ");
		Double t_ref = Double.parseDouble(infos1[0]);
		TC.println(Double.parseDouble(infos1[0])-t_ref+" "+infos1[1]+" "+infos1[2]+" "+infos1[3]);
		for (int j = 5; j<decoupage.length-1;j++){
			String ligne = decoupage[j];
			String[] infos = ligne.split(" ");
			TC.println((Double.parseDouble(infos[0])-t_ref)*0.001+" "+infos[1]+" "+infos[2]+" "+infos[3]);
		}
	}
	
	//SUPPRIMER TOUTES LES DONNEES DE LA BDD
	public static void rafraichir_BdD(){
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		
		try{
			Class.forName("org.postgresql.Driver");     
			cn = DriverManager.getConnection(url, login, password);
				String sql1 = "DELETE FROM mesures";
				PreparedStatement st1 = cn.prepareStatement(sql1);
				st1.executeUpdate();
				st1.close();
				System.out.println("Table mesures vidée");
			
				String sql2 = "DELETE FROM gps_position";
				PreparedStatement st2 = cn.prepareStatement(sql2);
				st2.executeUpdate();
				st2.close();
				System.out.println("Table gps vidée");
				
				String sql3 = "DELETE FROM nombre_mesures";
				PreparedStatement st3 = cn.prepareStatement(sql3);
				st3.executeUpdate();
				st3.close();
				System.out.println("Table nombre_mesures vidée");
				
				String sql4 = "DELETE FROM parametres_obstacle";
				PreparedStatement st4 = cn.prepareStatement(sql4);
				st4.executeUpdate();
				st4.close();
				System.out.println("Table parametres_obstacle vidée");

				String sql5 = "DELETE FROM types_obstacles";
				PreparedStatement st5 = cn.prepareStatement(sql5);
				st5.executeUpdate();
				st5.close();
				System.out.println("Table types_obstacles vidée");
				
				cn.close();
		}
		catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } 
		catch (SQLException e) {
		      e.printStackTrace();
		    }     
	}
	
}

