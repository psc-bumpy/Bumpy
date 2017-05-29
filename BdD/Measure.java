package BdD;

import java.sql.Connection;  
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

// DONNEES SOUS FORME DE STRING !

public class Measure {
	public String date;
	public GPS_position gps;
	public String vitesse;
	public String accelZ;
	public String accelX;
	public String accelY;
	public String time;
	final static double seuil=0.5;
	
	// time = intervalles de temps
	
	Measure (String date, GPS_position gps,String vitesse, String x, String y, String z, String time){
		this.date = date;
		this.gps=gps;
		this.vitesse=vitesse;
		this.accelX = x;
		this.accelY = y;
		this.accelZ = z;
		this.time = time;
	}
	
	//date#latitude#longitude#vitesse#t x y z#t x y z
	public static Measure Recuperer(String chaine){
		String time = "";
		String accelZ = "";
		String accelX = "";
		String accelY = "";
		
		String[] t = chaine.split("#");
		String date = t[0];
		GPS_position gps = new GPS_position(Double.parseDouble(t[3]),Double.parseDouble(t[2]));
		double vitesse = Double.parseDouble(t[1]);
		System.out.println("");
		System.out.println("vitesse en km/h " + vitesse*3.6);
		
		String[] mesures1=t[4].split(" ");
		double t_debut = Double.parseDouble(mesures1[0]);
		
		for (int i = 4;i<t.length;i++){
			String[] mesures = t[i].split(" ");
			double temps = Double.parseDouble(mesures[0])-t_debut;
			time = time+temps+" ";
			accelX = accelX+mesures[1]+" ";
			accelY = accelY+mesures[2]+" ";
			accelZ = accelZ+mesures[3]+" ";
		}
		System.out.println("Création de l'objet mesure réalisée");
		return new Measure(date,gps,""+vitesse,accelX,accelY,accelZ,time);
	}
	
	public double[] accel(String s){
		String[] accelx=s.split(" ");
		double[] AccelX=new double[accelx.length];
		for (int i =0;i<accelx.length;i++){
			AccelX[i]=Double.parseDouble(accelx[i]);
		}
		return AccelX;
	}
	
	public double[][] accel_et_temps(){
		return new double[][]{accel(accelX),accel(accelY),accel(accelZ),accel(time)};
	}

	
	//ENREGISTRER UNE MESURE DANS LA BDD
	public boolean save(){
		
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		
		boolean res = false;
		
		try{
			Class.forName("org.postgresql.Driver");     
			cn = DriverManager.getConnection(url, login, password);   
			System.out.println("la position est déjà présente : "+this.gps.isPresent());
			if (!this.gps.isPresent()){
				String sql = "INSERT INTO gps_position (coordonnees_gps) VALUES (?)";
				PreparedStatement st = cn.prepareStatement(sql);
				String s = this.gps.BdDtoString();
				st.setString(1,s);
				st.executeUpdate();
				st.close();
				
				Integer n = this.gps.num_obstacle();
				
				System.out.println("le numero du nouvel obstacle est "+n);
				
				String sql3 = "INSERT INTO parametres_obstacle (numero_obstacle,parametres) VALUES(?,?)";
				String param = "0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#0#NO TYPE";
				PreparedStatement st3 = cn.prepareStatement(sql3);
				st3.setInt(1, n);
				st3.setString(2, param);
				st3.executeUpdate();
				st3.close();
				
				System.out.println("les paramètres initialisateurs sont insérés");
				
				try{
				String sql2 = "INSERT INTO mesures (accelz, accelx, accely, mesures_temps,numero_obstacle,numero_mesure,type_mesure,date,vitesse) VALUES (?,?,?,?,?,?,?,?,?)";
				PreparedStatement st2 = cn.prepareStatement(sql2);
				st2.setInt(5,n);
				st2.setString(1,this.accelZ);
				st2.setString(2, this.accelX);
				st2.setString(3, this.accelY);
				st2.setString(4, this.time);
				st2.setInt(6,1);
				st2.setString(7, this.type_mesure(n,1));
				st2.setString(8, date);
				st2.setString(9, vitesse);
				System.out.println("cette première mesure est insérée");
				st2.executeUpdate();
				st2.close();

				String sql4 = "INSERT INTO nombre_mesures (numero_obstacle,num_mesures) VALUES (?,?)";
				PreparedStatement st4 = cn.prepareStatement(sql4);
				st4.setInt(1,n);
				st4.setInt(2,1);
				System.out.println("c'est la première mesure");
				st4.executeUpdate();
				st4.close();
				
				res = true;
				} catch (Exception e){
					e.printStackTrace();
					String sql6 = "DELETE FROM gps_position WHERE numero_obstacle = ?";
					PreparedStatement st6 = cn.prepareStatement(sql6);
					st6.setInt(1,n);
					st6.executeUpdate();
					st6.close();
					//update nombre de mesures
					String sql7 = "DELETE FROM parametres_obstacle WHERE numero_obstacle=?";
					PreparedStatement st7 = cn.prepareStatement(sql7);
					st7.setInt(1,n);
					st7.executeUpdate();
					st7.close();
					System.out.println("c'est bon c'est supprimé");
				} 
			}
			
			else {
				String sql5 = "SELECT num_mesures FROM nombre_mesures WHERE numero_obstacle = ?";
				PreparedStatement st5 = cn.prepareStatement(sql5);
				Integer n = this.gps.num_obstacle();
				st5.setInt(1,n);
				ResultSet r = st5.executeQuery();
				int num = 0;
				while (r.next()){
					num = r.getInt(1)+1;
				}
				r.close();
				st5.close();
				
				String sql2 = "INSERT INTO mesures (accelz, accelx, accely, mesures_temps,numero_obstacle,numero_mesure,type_mesure,date,vitesse) VALUES (?,?,?,?,?,?,?,?,?)";
				PreparedStatement st2 = cn.prepareStatement(sql2);
				st2.setInt(5,n);
				st2.setString(1,this.accelZ);
				st2.setString(2, this.accelX);
				st2.setString(3, this.accelY);
				st2.setString(4, this.time);
				st2.setInt(6,num);
				st2.setString(7, this.type_mesure(n,num));
				st2.setString(8, date);
				st2.setString(9, vitesse);
				
				System.out.println("une mesure de plus est insérée");
				
				String sql6 = "UPDATE nombre_mesures set num_mesures =? WHERE numero_obstacle=?";
				PreparedStatement st6 = cn.prepareStatement(sql6);
				st6.setInt(1,num);
				st6.setInt(2,n);
				st6.executeUpdate();
				st6.close();

				System.out.println("le nombre de mesures vaut maintenant : "+num);
				
				res = true;
			}
			
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
	
	//SUPPRIMER UNE MESURE
	public void delete(){
		
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		
		try{
			Class.forName("org.postgresql.Driver");     
			cn = DriverManager.getConnection(url, login, password);
			
			if (gps.isPresent()){
				//récupérer le num de l'obstacle
				Integer n = gps.num_obstacle();
				System.out.println("le num de l'obstacle dont on supprime une mesure : "+n);
				//récupérer la valeur de la dernière mesure entrée
				String sql5 = "SELECT num_mesures FROM nombre_mesures WHERE numero_obstacle = ?";
				PreparedStatement st5 = cn.prepareStatement(sql5);
				st5.setInt(1,n);
				ResultSet r = st5.executeQuery();
				int num = 0;
				while (r.next()){
					num = r.getInt(1)+1;
				}
				System.out.println("le num de la mesure qu'on va supprimer : "+num);
				r.close();
				st5.close();
				//supprimer la mesure associée
				String sql6 = "DELETE FROM mesures WHERE numero_obstacle = ? AND numero_mesure = ?";
				PreparedStatement st6 = cn.prepareStatement(sql6);
				st6.setInt(1,n);
				st6.setInt(2, num);
				st6.executeUpdate();
				st6.close();
				System.out.println("c'est bon c'est supprimé");
				//update nombre de mesures
				String sql7 = "UPDATE nombre_mesures set num_mesures =? WHERE numero_obstacle=?";
				PreparedStatement st7 = cn.prepareStatement(sql7);
				int nouveau_num = num - 1;
				st7.setInt(1,nouveau_num);
				st7.setInt(2,n);
				st7.executeUpdate();
				st7.close();
				cn.close();
				System.out.println("et le nombre de mesures est mis à jour à : "+nouveau_num);
			}
			
		}
		catch (ClassNotFoundException e) {
		      e.printStackTrace();
		    } 
		catch (SQLException e) {
		      e.printStackTrace();
		    }     
	}
	//RETROUVER LE TYPE DE LA MESURE
	public String type_mesure(int n,int nbre_passages){
		
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		try{
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(url, login, password);
		
			String sql = "SELECT parametres FROM parametres_obstacle WHERE numero_obstacle=?";
			PreparedStatement st = cn.prepareStatement(sql);
			st.setInt(1, n);
			ResultSet r = st.executeQuery();
			String parametres_string="";
			while (r.next()){
				parametres_string=r.getString(1);
			}
			r.close();
			st.close();
			
			String[] parametres = parametres_string.split("#");
			double longueur_moy = Double.parseDouble(parametres[0]);
			double nombre_pics_moy = Double.parseDouble(parametres[1]);
			double signe_moy = Double.parseDouble(parametres[2]);
			double regularite_moy = Double.parseDouble(parametres[3]);
			double distance_plateau_moy = Double.parseDouble(parametres[4]);
			double pic_max_moy = Double.parseDouble(parametres[5]);
			double range_deviation_moy = Double.parseDouble(parametres[6]);
			double nombre_valeurs_moyennes_moy = Double.parseDouble(parametres[7]);
			double confiance_moy = Double.parseDouble(parametres[8]);
			double vitesse_moy = Double.parseDouble(parametres[9]);
			double entropie_moy = Double.parseDouble(parametres[10]);
			double norme_moy = Double.parseDouble(parametres[11]);
			double coeff_Fourier_moy = Double.parseDouble(parametres[12]);
			double indice_goertzel_moyen = Double.parseDouble(parametres[13]);
			double indice_goertzel2_moyen = Double.parseDouble(parametres[14]);

			double moment_2_longueur = Double.parseDouble(parametres[15]);
			double moment_2_nombre_pic = Double.parseDouble(parametres[16]);
			double moment_2_signe = Double.parseDouble(parametres[17]);
			double moment_2_indice_reg = Double.parseDouble(parametres[18]);
			double moment_2_distance_plateau = Double.parseDouble(parametres[19]);
			double moment_2_pic_max = Double.parseDouble(parametres[20]);
			double moment_2_range_deviation = Double.parseDouble(parametres[21]);
			double moment_2_nbr_valeurs_moyennes = Double.parseDouble(parametres[22]);
			double moment_2_vitesse = Double.parseDouble(parametres[23]);
			double moment_2_entropie_y = Double.parseDouble(parametres[24]);
			double moment_2_norme_2_y = Double.parseDouble(parametres[25]);
			double moment_2_coeff_fourier = Double.parseDouble(parametres[26]);
			double moment_2_indice_goertzel = Double.parseDouble(parametres[27]);
			double moment_2_indice_goertzel2 = Double.parseDouble(parametres[28]);
			double entropie_z = Double.parseDouble(parametres[29]);
			double moment_2_entropie_z = Double.parseDouble(parametres[30]);
			double norme_2_z = Double.parseDouble(parametres[31]);
			double moment_2_norme_2_z = Double.parseDouble(parametres[32]);
			double regularite_moyenne = Double.parseDouble(parametres[33]);
			double moment_2_regularite = Double.parseDouble(parametres[34]);
			
			String ancien_type = parametres[35];
			
			
			Obstacle obstacle = new Obstacle(nbre_passages,longueur_moy,nombre_pics_moy,signe_moy,regularite_moy,distance_plateau_moy, 
			pic_max_moy,confiance_moy,range_deviation_moy,vitesse_moy,nombre_valeurs_moyennes_moy,entropie_moy,norme_moy,coeff_Fourier_moy,indice_goertzel_moyen,indice_goertzel2_moyen,
			moment_2_longueur,moment_2_nombre_pic,moment_2_signe,moment_2_indice_reg,moment_2_distance_plateau,moment_2_pic_max,
			moment_2_range_deviation,moment_2_nbr_valeurs_moyennes,moment_2_vitesse,moment_2_entropie_y,moment_2_norme_2_y,moment_2_coeff_fourier,moment_2_indice_goertzel,moment_2_indice_goertzel2,
			entropie_z,moment_2_entropie_z,norme_2_z,moment_2_norme_2_z,regularite_moyenne,moment_2_regularite,ancien_type);
			
			double[] donnees = Indirect.donnees(this.accel_et_temps(), Double.parseDouble(this.vitesse)); 
			
			double aberrant = donnees[12];
			double longueur = donnees[0];
			double nbr_pic = donnees[1];
			double sgn = donnees[2];
			double I = donnees[3];
			double dist_plat = donnees[4];
			double pic_max = donnees[5];
			double range_dev = donnees[6];
			double vit_moy = donnees[7];
			double valeur_moy = donnees[8];
			double entropie = donnees[9];
			double norme = donnees[10];
			double fourier = donnees[11];
			double indice_goertzel1 = donnees[13];
			double indice_goertzel2 = donnees[14];
			double entropie_verticale = donnees[15];
			double norme_2_verticale = donnees[16];
			double regularite = donnees[17];
			
			obstacle.mis_a_jour(aberrant, longueur, nbr_pic, sgn, I, dist_plat, pic_max, range_dev, vit_moy, valeur_moy, entropie, norme, fourier,indice_goertzel1,indice_goertzel2,entropie_verticale,norme_2_verticale,regularite);
			
			obstacle.mise_a_jour_categorie();
			
			String sql2 = "DELETE FROM parametres_obstacle WHERE numero_obstacle=?";
			PreparedStatement st2 = cn.prepareStatement(sql2);
			st2.setInt(1, n);
			st2.executeUpdate();
			st2.close();
			
			String sql3 = "INSERT INTO parametres_obstacle (numero_obstacle,parametres) VALUES(?,?)";
			PreparedStatement st3 = cn.prepareStatement(sql3);
			st3.setInt(1, n);
			st3.setString(2, obstacle.mise_a_jour_BdD());
			st3.executeUpdate();
			st3.close();
			System.out.println("les nouveaux paramètres sont insérés");
			
			cn.close();
			System.out.println("le type de la mesure vaut : "+obstacle.type + " et la confiance vaut : "+obstacle.confiance);
			return obstacle.type;
			
		} 	
		catch (ClassNotFoundException e) {
				e.printStackTrace();
		} 
		catch (SQLException e) {
				e.printStackTrace();
		}
		return "NO TYPE";
	}
	
	
	//RECUPERER LES MESURES A PARTIR DE COUPLES (numero obstacle, numero mesure de cet obstacle)
	
	//Ajouter une nouvelle table avec le num de l'obstacle et le nombre total de mesures faites jusque là
	//Ajouter dans la table mesures une clé secondaire qui donne le numéro de la mesure
	
	public static LinkedList<Measure> Recuperation_mesures_precises(int[][] t){
		//exception si id et num_mesure n'ont pas la même taille
		LinkedList<Measure> res = new LinkedList<Measure>();
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		try{
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(url, login, password);
		
			String sql = "SELECT accelz, accelx, accely, mesures_temps,date,vitesse FROM mesures WHERE numero_obstacle=? AND numero_mesure=?";
			PreparedStatement st = cn.prepareStatement(sql);
			String sql2 = "SELECT coordonnees_gps FROM gps_position WHERE numero_obstacle = ?";
			PreparedStatement st2 = cn.prepareStatement(sql2);
			
			for (int k = 0;k<t.length;k++){
				st.setInt(1,t[k][0]);
				st.setInt(2, t[k][1]);
				st2.setInt(1, t[k][0]);
				ResultSet l = st.executeQuery();
				ResultSet l2 = st2.executeQuery();
				l.next();
				l2.next();
				GPS_position gps = new GPS_position(l2.getString(1));
				String x = l.getString(1);
				String z = l.getString(2);
				String y = l.getString(3);
				String tps = l.getString(4);
				String date = l.getString(5);
				String vitesse = l.getString(6);
				Measure mesure = new Measure(date,gps,vitesse,x,y,z,tps);
				l.close();
				l2.close();
				res.add(mesure);
			}
			
			st.close();
			st2.close();
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
	
	//BENOIT : DEMANDE 1
	public static LinkedList<String[]> construction_carte(){
		LinkedList<String[]> res = new LinkedList<String[]>();
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		try{
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(url, login, password);
		
			String sql1 = "SELECT numero_obstacle,coordonnees_gps FROM gps_position";
			PreparedStatement st = cn.prepareStatement(sql1);
			ResultSet r = st.executeQuery();
			
			while (r.next()){
				String[] infos = new String[4];
				int n = r.getInt(1);
				GPS_position gps = new GPS_position(r.getString(2));
				infos[0]=""+gps.latitude;
				infos[1]=""+gps.longitude;
				
				String sql2 = "SELECT parametres FROM parametres_obstacle WHERE numero_obstacle=?";
				PreparedStatement st2 = cn.prepareStatement(sql2);
				st2.setInt(1, n);
				ResultSet r2 = st.executeQuery();
				String parametres_string="";
				r2.next();
				parametres_string=r2.getString(1);
				r2.close();
				st2.close();
				String[] parametres = parametres_string.split("#");
				String type = parametres[36];
				
				infos[2]=""+type;
				
				String sql3 = "SELECT num_mesures FROM nombre_mesures WHERE numero_obstacle=?";
				PreparedStatement st3 = cn.prepareStatement(sql3);
				st3.setInt(1, n);
				ResultSet r3 = st.executeQuery();
				infos[3]=r3.getString(1);
				r3.close();
				st3.close();
				
				res.add(infos);
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
	
	
	//BENOIT : DEMANDE 2
	public static Measure Une_mesure(GPS_position gps){
			if (!gps.isPresent()){
				return null;
			}
			else {
				int n = gps.num_obstacle();
				return Recuperation_mesures_precises(new int[][]{new int[] {n,1}}).pop();
			}
	}
	
	//RENVOYER LA LISTE DES REFERENCES DES MESURES CORRESPONDANT A UN TYPE D'OBSTACLE DONNE
	//Exemple : toutes les mesures qui ont donné le résultat "dos d'âne" ou "plateau"
	
	public static LinkedList<int[]> References_mesures(String type_obstacle){
		LinkedList<int[]> res = new LinkedList<int[]>();
		String url = "jdbc:postgresql://localhost:5432/PSCMEC12 X2015 PSA";
		String login = "postgres";
		String password = "pscmec12";
		Connection cn = null;
		try{
			Class.forName("org.postgresql.Driver");
			cn = DriverManager.getConnection(url, login, password);
		
			String sql = "SELECT numero_obstacle, numero_mesure FROM mesures WHERE type_mesure = ?";
			PreparedStatement st = cn.prepareStatement(sql);
			st.setString(1, type_obstacle);
			ResultSet r = st.executeQuery();
			
			while (r.next()){
				int[] references = new int[2];
				references[0]=r.getInt(1);
				references[1]=r.getInt(2);
				res.add(references);
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
	
	public static int nombre_obstacles(LinkedList<int[]> liste){
		LinkedList<Integer> res = new LinkedList<Integer>();
		for (int[] t : liste){
			if (!res.contains(t[0])){
				res.add(t[0]);
			}
		}
		return res.size();
	}
	
	//BENOIT : DEMANDE 3
	public static String[][] macrodonnees(){
		
		LinkedList<int[]> type1 = References_mesures("NID DE POULE");
		LinkedList<int[]> type2 = References_mesures("PALIER DESCENDANT");
		LinkedList<int[]> type3 = References_mesures("PALIER MONTANT");
		LinkedList<int[]> type4 = References_mesures("DOS D'ANE");
		LinkedList<int[]> type5 = References_mesures("PLATEAU");
		LinkedList<int[]> type6 = References_mesures("COUSSIN");
		LinkedList<int[]> type7 = References_mesures("BARRE");
		LinkedList<int[]> type8 = References_mesures("ROUTE MAUVAISE");
		LinkedList<int[]> type9 = References_mesures("INCLASSABLE");
		
		return new String[][]{new String[]{"NID DE POULE",nombre_obstacles(type1)+""},
			new String[]{"PALIER DESCENDANT",nombre_obstacles(type2)+""},
			new String[]{"PALIER MONTANT",nombre_obstacles(type3)+""},
			new String[]{"DOS D'ANE",nombre_obstacles(type4)+""},
			new String[]{"PLATEAU",nombre_obstacles(type5)+""},
			new String[]{"COUSSIN",nombre_obstacles(type6)+""},
			new String[]{"BARRE",nombre_obstacles(type7)+""},
			new String[]{"ROUTE MAUVAISE",nombre_obstacles(type8)+""},
			new String[]{"INCLASSABLE",nombre_obstacles(type9)+""}};
		
	}
}
