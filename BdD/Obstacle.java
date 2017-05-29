package BdD;

import tc.TC;

public class Obstacle {
	public int nbr_passages;
	public double longueur_moyenne;
	public double moment_2_longueur;
	public double nombre_pic_moyen;
	public double moment_2_nombre_pic;
	public double signe_moyen;
	public double moment_2_signe;
	public double indice_reg_moyen;
	public double moment_2_indice_reg;
	public double distance_plateau_moyen;
	public double moment_2_distance_plateau;
	public double pic_max_moyen;
	public double moment_2_pic_max;
	public double range_deviation_moyen;
	public double moment_2_range_deviation;
	public double nbr_valeurs_moyennes;
	public double moment_2_nbr_valeurs_moyennes;
	public double confiance;
	public double vitesse_moyenne;
	public double moment_2_vitesse;
	public double entropie_y;
	public double moment_2_entropie_y;
	public double norme_2_y;
	public double moment_2_norme_2_y;
	public double coeff_fourier_moyen;
	public double moment_2_coeff_fourier;
	public double indice_goertzel_moyen;
	public double moment_2_indice_goertzel;
	public double indice_goertzel2_moyen;
	public double moment_2_indice_goertzel2;
	public String type;
	public double entropie_z;
	public double moment_2_entropie_z;
	public double norme_2_z;
	public double moment_2_norme_2_z;
	public double regularite_moyenne;
	public double moment_2_regularite;
	
	public static String[] types={"NID DE POULE","PALIER DESCENDANT","PALIER MONTANT", "DOS D'ANE","PLATEAU","COUSSIN","BARRE/TROTTOIR","ROUTE MAUVAISE","PETITE IRREGULARITE DE LA ROUTE"};
	public static String[] indices={"entropie_y","norme_2_y","coeff_fourier","nombre_valeurs_moyennes","indice_goertzel","indice_goertzel_moyen","distance_plateau","longueur","indice_reg","range_deviation","norme 2 verticale","entropie verticale","regularite"};
	public static int N=types.length;
	public static int P=indices.length;
	public Obstacle(){
		nbr_passages=0;
		longueur_moyenne=0;
		nombre_pic_moyen=0;
		signe_moyen=0;
		indice_reg_moyen=0;
		distance_plateau_moyen=0;
		pic_max_moyen=0;
		range_deviation_moyen=0;
		nbr_valeurs_moyennes=0;
		confiance=0;
		vitesse_moyenne=0;
		entropie_y=0;
		norme_2_y=0;
		coeff_fourier_moyen=0;
		indice_goertzel_moyen=0;
		indice_goertzel2_moyen=0;
		type="NO TYPE";
	}
	
	public Obstacle(int d1,double d2,double d3,double d4,double d5,double d6,double d7,double d8,double d9,double d10,
			double d11,double d12,double d13,double d14,double d15,double d16,double d17,double d18,double d19,double d20,double d21,
			double d22,double d23,double d24,double d25,double d26,double d27,double d28,double d29,double d30,double d31, double d32, double d33, double d34, double d35, double d36,String s){
		nbr_passages=d1;
		longueur_moyenne=d2;
		nombre_pic_moyen=d3;
		signe_moyen=d4;
		indice_reg_moyen=d5;
		distance_plateau_moyen=d6;
		pic_max_moyen=d7;
		range_deviation_moyen=d9;
		nbr_valeurs_moyennes=d11;
		confiance=d8;
		vitesse_moyenne=d10;
		entropie_y=d12;
		norme_2_y=d13;
		coeff_fourier_moyen=d14;
		indice_goertzel_moyen=d15;
		indice_goertzel2_moyen=d16;
		type=s;
		
		moment_2_longueur=d17;
		moment_2_nombre_pic=d18;
		moment_2_signe=d19;
		moment_2_indice_reg=d20;
		moment_2_distance_plateau=d21;
		moment_2_pic_max=d22;
		moment_2_range_deviation=d23;
		moment_2_nbr_valeurs_moyennes=d24;
		moment_2_vitesse=d25;
		moment_2_entropie_y=d26;
		moment_2_norme_2_y=d27;
		moment_2_coeff_fourier=d28;
		moment_2_indice_goertzel=d29;
		moment_2_indice_goertzel2=d30;
		
		entropie_z=d31;
		moment_2_entropie_z=d32;
		norme_2_z=d33;
		moment_2_norme_2_z=d34;
		regularite_moyenne=d35;
		moment_2_regularite=d36;
	}
	
	public String mise_a_jour_BdD(){
		return ""+
		longueur_moyenne+"#"+
		nombre_pic_moyen+"#"+
		signe_moyen+"#"+
		indice_reg_moyen+"#"+
		distance_plateau_moyen+"#"+
		pic_max_moyen+"#"+
		range_deviation_moyen+"#"+
		nbr_valeurs_moyennes+"#"+
		confiance+"#"+
		vitesse_moyenne+"#"+
		entropie_y+"#"+
		norme_2_y+"#"+
		coeff_fourier_moyen+"#"+
		indice_goertzel_moyen+"#"+
		indice_goertzel2_moyen+"#"+
		moment_2_longueur+"#"+
		moment_2_nombre_pic+"#"+
		moment_2_signe+"#"+
		moment_2_indice_reg+"#"+
		moment_2_distance_plateau+"#"+
		moment_2_pic_max+"#"+
		moment_2_range_deviation+"#"+
		moment_2_nbr_valeurs_moyennes+"#"+
		moment_2_vitesse+"#"+
		moment_2_entropie_y+"#"+
		moment_2_norme_2_y+"#"+
		moment_2_coeff_fourier+"#"+
		moment_2_indice_goertzel+"#"+
		moment_2_indice_goertzel2+"#"+
		entropie_z+"#"+
		moment_2_entropie_z+"#"+
		norme_2_z+"#"+
		moment_2_norme_2_z+"#"+
		regularite_moyenne+"#"+
		moment_2_regularite+"#"+
		type;
	}
	
	//objet paramÃ¨tre
	// dans mis a jour, rajouter un test de non etrangetÃ© total pour pas perturber des donnÃ©es
	public void mis_a_jour(double aberrant, double longueur, double nbr_pic, double sgn, double I, double dist_plat, double pic_max, double range_dev, double vit_moy, double valeur_moy, double entropie, double norme, double fourier, double indice_g, double indice_g2,double entropie_verticale, double norme_2_verticale, double regularite){
		int n=nbr_passages-1;
		
		if (aberrant==0){
			
			double longueur_moyenne_previous=longueur_moyenne;
			double nombre_pic_moyen_previous=nombre_pic_moyen;
			double signe_moyen_previous=signe_moyen;
			double indice_reg_moyen_previous=indice_reg_moyen;
			double distance_plateau_moyen_previous=distance_plateau_moyen;
			double pic_max_moyen_previous=pic_max_moyen;
			double range_deviation_moyen_previous=range_deviation_moyen;
			double nbr_valeurs_moyennes_previous=nbr_valeurs_moyennes;
			double entropie_y_previous=entropie_y;
			double norme_2_y_previous=norme_2_y;
			double coeff_fourier_moyen_previous=coeff_fourier_moyen;
			double indice_goertzel_moyen_previous=indice_goertzel_moyen;
			double indice_goertzel2_moyen_previous=indice_goertzel2_moyen;	
			double entropie_z_previous=entropie_z;
			double norme_2_z_previous=norme_2_z;
			double regularite_previous=regularite_moyenne;
			
		longueur_moyenne=(n*longueur_moyenne+longueur)/(n+1);
		moment_2_longueur=moment_2_longueur+longueur*longueur;
		nombre_pic_moyen=(n*nombre_pic_moyen+nbr_pic)/(n+1);
		moment_2_nombre_pic=moment_2_nombre_pic+nbr_pic*nbr_pic;
		signe_moyen=(n*signe_moyen+sgn)/(n+1);
		moment_2_signe=moment_2_signe+sgn*sgn;
		indice_reg_moyen=(n*indice_reg_moyen+I)/(n+1);
		moment_2_indice_reg=moment_2_indice_reg+I*I;
		distance_plateau_moyen=(n*distance_plateau_moyen+dist_plat)/(n+1);
		moment_2_distance_plateau=moment_2_distance_plateau+dist_plat*dist_plat;
		pic_max_moyen=(n*pic_max_moyen+pic_max)/(n+1);
		moment_2_pic_max=moment_2_pic_max+pic_max*pic_max;
		range_deviation_moyen=(n*range_deviation_moyen+range_dev)/(n+1);
		moment_2_range_deviation=moment_2_range_deviation+range_dev*range_dev;
		vitesse_moyenne=(n*vitesse_moyenne+vit_moy)/(n+1);
		moment_2_vitesse=moment_2_vitesse+vit_moy*vit_moy;
		entropie_y=(n*entropie_y+entropie)/(n+1);
		moment_2_entropie_y=moment_2_entropie_y+entropie*entropie;
		norme_2_y=(n*norme_2_y+norme)/(n+1);
		moment_2_norme_2_y=moment_2_norme_2_y+norme*norme;
		nbr_valeurs_moyennes=(n*nbr_valeurs_moyennes+valeur_moy)/(n+1);
		moment_2_nbr_valeurs_moyennes=moment_2_nbr_valeurs_moyennes+valeur_moy*valeur_moy;
		coeff_fourier_moyen=(n*coeff_fourier_moyen+fourier)/(n+1);
		moment_2_coeff_fourier=moment_2_coeff_fourier+fourier*fourier;
		indice_goertzel_moyen=(n*indice_goertzel_moyen+indice_g)/(n+1);
		moment_2_indice_goertzel=moment_2_indice_goertzel+indice_g*indice_g;
		indice_goertzel2_moyen=(n*indice_goertzel2_moyen+indice_g2)/(n+1);
		moment_2_indice_goertzel2=moment_2_indice_goertzel2*indice_g2*indice_g2;
		entropie_z=(n*entropie_z+entropie_verticale)/(n+1);
		moment_2_entropie_z=moment_2_entropie_z+entropie_verticale*entropie_verticale;
		norme_2_z=(n*norme_2_z+norme_2_verticale)/(n+1);
		moment_2_norme_2_z=moment_2_norme_2_z+norme_2_verticale*norme_2_verticale;
		regularite_moyenne=(n*regularite_moyenne+regularite)/(n+1);
		moment_2_regularite=moment_2_regularite+regularite*regularite;

		String previous=type;
		mise_a_jour_categorie();
		double[][][] resultats=Recup.extraction();
		double[][] moyennes_categories=resultats[1];
		double [][] moment_2_categories=resultats[2];
		double[] nbr_passage=resultats[0][0];
		int j=0;
		while (!types[j].equals(type)){
			j=j+1;
		}
		
		if (previous.equals(type) || nbr_passages==1){

		
			moyennes_categories[0][j]=(moyennes_categories[0][j]*nbr_passage[j]+entropie)/(nbr_passage[j]+1);
	        moyennes_categories[1][j]=(moyennes_categories[1][j]*nbr_passage[j]+norme)/(nbr_passage[j]+1);
	        moyennes_categories[2][j]=(moyennes_categories[2][j]*nbr_passage[j]+fourier)/(nbr_passage[j]+1);
	        moyennes_categories[3][j]=(moyennes_categories[3][j]*nbr_passage[j]+valeur_moy)/(nbr_passage[j]+1);
	        moyennes_categories[4][j]=(moyennes_categories[4][j]*nbr_passage[j]+indice_g)/(nbr_passage[j]+1);
	        moyennes_categories[5][j]=(moyennes_categories[5][j]*nbr_passage[j]+indice_g2)/(nbr_passage[j]+1);
	        moyennes_categories[6][j]=(moyennes_categories[6][j]*nbr_passage[j]+dist_plat)/(nbr_passage[j]+1);
	        moyennes_categories[7][j]=(moyennes_categories[7][j]*nbr_passage[j]+longueur)/(nbr_passage[j]+1);
	        moyennes_categories[8][j]=(moyennes_categories[8][j]*nbr_passage[j]+I)/(nbr_passage[j]+1);
	        moyennes_categories[9][j]=(moyennes_categories[9][j]*nbr_passage[j]+range_dev)/(nbr_passage[j]+1);
	        moyennes_categories[10][j]=(moyennes_categories[10][j]*nbr_passage[j]+norme_2_verticale)/(nbr_passage[j]+1);
	        moyennes_categories[11][j]=(moyennes_categories[11][j]*nbr_passage[j]+entropie_verticale)/(nbr_passage[j]+1);
	        moyennes_categories[12][j]=(moyennes_categories[12][j]*nbr_passage[j]+regularite)/(nbr_passage[j]+1);
	        
	        
	        moment_2_categories[0][j]=(moment_2_categories[0][j]*nbr_passage[j]+entropie*entropie)/(nbr_passage[j]+1);
	        moment_2_categories[1][j]=(moment_2_categories[1][j]*nbr_passage[j]+norme*norme)/(nbr_passage[j]+1);
	        moment_2_categories[2][j]=(moment_2_categories[2][j]*nbr_passage[j]+fourier*fourier)/(nbr_passage[j]+1);
	        moment_2_categories[3][j]=(moment_2_categories[3][j]*nbr_passage[j]+valeur_moy*valeur_moy)/(nbr_passage[j]+1);
	        moment_2_categories[4][j]=(moment_2_categories[4][j]*nbr_passage[j]+indice_g*indice_g)/(nbr_passage[j]+1);
	        moment_2_categories[5][j]=(moment_2_categories[5][j]*nbr_passage[j]+indice_g2*indice_g2)/(nbr_passage[j]+1);
	        moment_2_categories[6][j]=(moment_2_categories[6][j]*nbr_passage[j]+dist_plat*dist_plat)/(nbr_passage[j]+1);
	        moment_2_categories[7][j]=(moment_2_categories[7][j]*nbr_passage[j]+longueur*longueur)/(nbr_passage[j]+1);
	        moment_2_categories[8][j]=(moment_2_categories[8][j]*nbr_passage[j]+I*I)/(nbr_passage[j]+1);
	        moment_2_categories[9][j]=(moment_2_categories[9][j]*nbr_passage[j]+range_dev*range_dev)/(nbr_passage[j]+1);
	        moment_2_categories[10][j]=(moment_2_categories[10][j]*nbr_passage[j]+norme_2_verticale*norme_2_verticale)/(nbr_passage[j]+1);
	        moment_2_categories[11][j]=(moment_2_categories[11][j]*nbr_passage[j]+entropie_verticale*entropie_verticale)/(nbr_passage[j]+1);
	        moment_2_categories[12][j]=(moment_2_categories[12][j]*nbr_passage[j]+regularite*regularite)/(nbr_passage[j]+1);
	        
	        nbr_passage[j]=nbr_passage[j]+1;
	            }
	        
	        else{
	            int i=0;
	            while (!types[i].equals(previous)){
	                i=i+1;
	            }
	            //on se retrouve avec l'indice i de l'obstcle catégorisé avant, j le nouveau
	            if (nbr_passage[i]-nbr_passages+1>0){
	            
	            moyennes_categories[0][i]=(moyennes_categories[0][i]*nbr_passage[i]-entropie_y_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[1][i]=(moyennes_categories[1][i]*nbr_passage[i]-norme_2_y_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[2][i]=(moyennes_categories[2][i]*nbr_passage[i]-coeff_fourier_moyen_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[3][i]=(moyennes_categories[3][i]*nbr_passage[i]-nbr_valeurs_moyennes_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[4][i]=(moyennes_categories[4][i]*nbr_passage[i]-indice_goertzel_moyen_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[5][i]=(moyennes_categories[5][i]*nbr_passage[i]-indice_goertzel2_moyen_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[6][i]=(moyennes_categories[6][i]*nbr_passage[i]-distance_plateau_moyen_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[7][i]=(moyennes_categories[7][i]*nbr_passage[i]-longueur_moyenne_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[8][i]=(moyennes_categories[8][i]*nbr_passage[i]-indice_reg_moyen_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[9][i]=(moyennes_categories[9][i]*nbr_passage[i]-range_deviation_moyen_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[10][i]=(moyennes_categories[10][i]*nbr_passage[i]-norme_2_z*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[11][i]=(moyennes_categories[11][i]*nbr_passage[i]-entropie_z_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);
	            moyennes_categories[12][i]=(moyennes_categories[12][i]*nbr_passage[i]-regularite_previous*(nbr_passages-1))/(nbr_passage[i]-nbr_passages+1);

	            
	            moment_2_categories[0][i]=(moment_2_categories[0][i]*nbr_passage[i]-moment_2_entropie_y+entropie*entropie)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[1][i]=(moment_2_categories[1][i]*nbr_passage[i]-moment_2_norme_2_y+norme*norme)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[2][i]=(moment_2_categories[2][i]*nbr_passage[i]-moment_2_coeff_fourier+fourier*fourier)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[3][i]=(moment_2_categories[3][i]*nbr_passage[i]-moment_2_nbr_valeurs_moyennes+valeur_moy*valeur_moy)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[4][i]=(moment_2_categories[4][i]*nbr_passage[i]-moment_2_indice_goertzel+indice_g*indice_g)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[5][i]=(moment_2_categories[5][i]*nbr_passage[i]-moment_2_indice_goertzel2+indice_g2*indice_g2)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[6][i]=(moment_2_categories[6][i]*nbr_passage[i]-moment_2_distance_plateau+dist_plat*dist_plat)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[7][i]=(moment_2_categories[7][i]*nbr_passage[i]-moment_2_longueur+longueur*longueur)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[8][i]=(moment_2_categories[8][i]*nbr_passage[i]-moment_2_indice_reg+I*I)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[9][i]=(moment_2_categories[9][i]*nbr_passage[i]-moment_2_range_deviation+range_dev*range_dev)/(nbr_passage[i]-nbr_passages+1);
	             moment_2_categories[10][i]=(moment_2_categories[10][i]*nbr_passage[i]-moment_2_norme_2_z+norme_2_verticale*norme_2_verticale)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[11][i]=(moment_2_categories[11][i]*nbr_passage[i]-moment_2_entropie_z+entropie_verticale*entropie_verticale)/(nbr_passage[i]-nbr_passages+1);
	            moment_2_categories[12][i]=(moment_2_categories[12][i]*nbr_passage[i]-moment_2_regularite+regularite*regularite)/(nbr_passage[i]-nbr_passages+1);
	            
	            }
	            else{
	                moyennes_categories[0][i]=0;
	                moyennes_categories[1][i]=0;
	                moyennes_categories[2][i]=0;
	                moyennes_categories[3][i]=0;
	                moyennes_categories[4][i]=0;
	                moyennes_categories[5][i]=0;
	                moyennes_categories[6][i]=0;
	                moyennes_categories[7][i]=0;
	                moyennes_categories[8][i]=0;
	                moyennes_categories[9][i]=0;
	                moyennes_categories[10][i]=0;
	                moyennes_categories[11][i]=0;
	                moyennes_categories[12][i]=0;
	                
	                moment_2_categories[0][i]=0;
	                moment_2_categories[1][i]=0;
	                moment_2_categories[2][i]=0;
	                moment_2_categories[3][i]=0;
	                moment_2_categories[4][i]=0;
	                moment_2_categories[5][i]=0;
	                moment_2_categories[6][i]=0;
	                moment_2_categories[7][i]=0;
	                moment_2_categories[8][i]=0;
	                moment_2_categories[9][i]=0;
	                moment_2_categories[10][i]=0;
	                moment_2_categories[11][i]=0;
	                moment_2_categories[12][i]=0;
	            }
	            
	            moyennes_categories[0][j]=(moyennes_categories[0][j]*nbr_passage[j]+entropie_y*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[1][j]=(moyennes_categories[1][j]*nbr_passage[j]+norme_2_y*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[2][j]=(moyennes_categories[2][j]*nbr_passage[j]+coeff_fourier_moyen*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[3][j]=(moyennes_categories[3][j]*nbr_passage[j]+nbr_valeurs_moyennes*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[4][j]=(moyennes_categories[4][j]*nbr_passage[j]+indice_goertzel_moyen*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[5][j]=(moyennes_categories[5][j]*nbr_passage[j]+indice_goertzel2_moyen*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[6][j]=(moyennes_categories[6][j]*nbr_passage[j]+distance_plateau_moyen*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[7][j]=(moyennes_categories[7][j]*nbr_passage[j]+longueur_moyenne*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[8][j]=(moyennes_categories[8][j]*nbr_passage[j]+indice_reg_moyen*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[9][j]=(moyennes_categories[9][j]*nbr_passage[j]+range_deviation_moyen*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[10][j]=(moyennes_categories[10][j]*nbr_passage[j]+norme_2_verticale*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[11][j]=(moyennes_categories[11][j]*nbr_passage[j]+entropie_verticale*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            moyennes_categories[12][j]=(moyennes_categories[12][j]*nbr_passage[j]+regularite*nbr_passages)/(nbr_passage[j]+nbr_passages);
	            
	            
	            moment_2_categories[0][j]=(moment_2_categories[0][j]*nbr_passage[j]+moment_2_entropie_y)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[1][j]=(moment_2_categories[1][j]*nbr_passage[j]+moment_2_norme_2_y)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[2][j]=(moment_2_categories[2][j]*nbr_passage[j]+moment_2_coeff_fourier)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[3][j]=(moment_2_categories[3][j]*nbr_passage[j]+moment_2_nbr_valeurs_moyennes)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[4][j]=(moment_2_categories[4][j]*nbr_passage[j]+moment_2_indice_goertzel)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[5][j]=(moment_2_categories[5][j]*nbr_passage[j]+moment_2_indice_goertzel2)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[6][j]=(moment_2_categories[6][j]*nbr_passage[j]+moment_2_distance_plateau)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[7][j]=(moment_2_categories[7][j]*nbr_passage[j]+moment_2_longueur)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[8][j]=(moment_2_categories[8][j]*nbr_passage[j]+moment_2_indice_reg)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[9][j]=(moment_2_categories[9][j]*nbr_passage[j]+moment_2_range_deviation)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[10][j]=(moment_2_categories[10][j]*nbr_passage[j]+moment_2_norme_2_z)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[11][j]=(moment_2_categories[11][j]*nbr_passage[j]+moment_2_entropie_z)/(nbr_passage[j]+nbr_passages);
	            moment_2_categories[12][j]=(moment_2_categories[12][j]*nbr_passage[j]+moment_2_regularite)/(nbr_passage[j]+nbr_passages);

	            
	            //tout un travail de correction des moyennes, il faut retirer les moyennes dues a cet obstacle puisqu'il a changé de type et rajouter 
	            //d'un seul coup les nbr+1 passages de l'autres
	            nbr_passage[i]=nbr_passage[i]-nbr_passages+1;
	            nbr_passage[j]=nbr_passage[j]+nbr_passages;		}
		mis_a_jour_moyennes(nbr_passage,moyennes_categories,moment_2_categories);
		Parametres_classification.mis_a_jour_parametres(moyennes_categories,moment_2_categories);
		}
		else{
			if (aberrant==1){type="ROUTE MAUVAISE";confiance=60;}
			else {type="PETITE IRREGULARITE DE ROUTE";confiance=60;}
		}
	}
	public void mise_a_jour_categorie(){
        /*
        System.out.println("longueur moyenne:  "+ longueur_moyenne);
        System.out.println("signe moyen:  "+signe_moyen);
        System.out.println("indice de régularité:   " +indice_reg_moyen);
        System.out.println("proportion de cassure:" + regularite_moyenne);
        System.out.println("nombre pic moyen :" +nombre_pic_moyen);
        System.out.println("norme L1 z:  " + range_deviation_moyen);
        System.out.println("norme L2 y:  " + norme_2_y);
        System.out.println("norme L2 z:  "+ norme_2_z);
        System.out.println("entropie y:  "+ entropie_y);
        System.out.println("entropie z:  "+ entropie_z);
        System.out.println("indice de goerztel : "+ indice_goertzel_moyen);
        System.out.println("indice de goerztel : "+ indice_goertzel2_moyen);
        System.out.println("accel max:   "+pic_max_moyen);
        System.out.println("proportion valeur moyenne:  "+nbr_valeurs_moyennes);
        */
        //System.out.println(entropie_y);
        if (signe_moyen<-0.5){
            if (longueur_moyenne<1.5 || (longueur_moyenne<3 &&norme_2_z<0.02)) {
                type="PETITE IRREGULARITE DE ROUTE";
                confiance=70+20-20/nbr_passages;
            }
            else if (longueur_moyenne<4){
                if (indice_reg_moyen>10000){
                    type="NID DE POULE";
                    confiance=80-30/nbr_passages;
                }
                else{
                    type="PALIER DESCENDANT";
                    if (nombre_pic_moyen<2){
                        confiance=60;
                    }
                }
            }
            else{
                type="ROUTE MAUVAISE";
                if (nbr_passages>10){
                    confiance=80-30/nbr_passages;
                }
                else{
                    confiance=50;
                }
                if (entropie_y>2){
                    confiance=confiance+10;
                }
            }
        }
        else if (longueur_moyenne<1.5){
            type="PETITE IRREGULARITE DE ROUTE";
            confiance=70+20-20/nbr_passages;
        }
        else if (longueur_moyenne<5 && indice_reg_moyen<6000){
            type="PALIER MONTANT";
            confiance=50+20-20/nbr_passages;
        }
        else if (longueur_moyenne>9 && longueur_moyenne<20 && indice_goertzel2_moyen>0.7 && entropie_y<1.9){
            type="PLATEAU";
            confiance=70-20/nbr_passages;
            if (longueur_moyenne>13){
                confiance=confiance+10-10/nbr_passages;;
            }
            if (indice_reg_moyen>15000){
                confiance=confiance+10-10/nbr_passages;
            }
            if (range_deviation_moyen<1){
                confiance=confiance+5-5/nbr_passages;
            }
            if (indice_goertzel_moyen<0.15){
                confiance=confiance+5-5/nbr_passages;
            }
            if (indice_goertzel_moyen>0.3){
                confiance=confiance-10+5/nbr_passages;
            }
            
        }
        else if (indice_reg_moyen<6000 && nbr_valeurs_moyennes>0.18 && longueur_moyenne>5){
            type="DOS D'ANE";
            confiance=70-20/nbr_passages;
            if (nombre_pic_moyen>=4.5){
                confiance=confiance+10-10/nbr_passages;
            }
            if (indice_goertzel_moyen<0.15){
                confiance=confiance+5-5/nbr_passages;
            }
            if (indice_goertzel_moyen>0.3){
                confiance=confiance-10+5/nbr_passages;
            }
        }
        else if (pic_max_moyen>=7 && vitesse_moyenne<5){
            type="BARRE/TROTTOIR";
            confiance=50+10-10/nbr_passages;
            if (nbr_valeurs_moyennes<30){
                confiance=confiance+10;
            }
            if (indice_goertzel_moyen<0.15){
                confiance=confiance-5+5/nbr_passages;
            }
            if (indice_goertzel2_moyen>1.8){
                confiance=confiance+5+5/nbr_passages;
            }
            if (longueur_moyenne<3){
                confiance=confiance+10;
            }
        }
        else if ((entropie_y>1.8 || norme_2_y>0.006)  && indice_goertzel_moyen>0.3 && indice_goertzel2_moyen<0.30){
                type="COUSSIN";
                confiance=70;
                if (norme_2_y>0.006){
                    confiance=80;
                }
        }
        else{
            type="ROUTE MAUVAISE";
            if (Math.sqrt(moment_2_longueur-longueur_moyenne*longueur_moyenne)>2){
                confiance=80;
            }
        }
        int i=Vraisemblance.decision(get_parametres());
        if (type==types[i]){
            confiance=confiance+10;
        }
    }
	public static void mis_a_jour_moyennes(double[] nbr_passages,double[][] moyennes_categories,double[][] moment_2_categories){
		TC.ecritureDansNouveauFichier("Moyennes_obstacle.txt");
		for (int j=0;j<N-1;j++){
			TC.print(nbr_passages[j]+" ");
		}
		TC.println(nbr_passages[N-1]);
		for (int i=0;i<P;i++){
			for (int j=0;j<N;j++){
				TC.print((moyennes_categories[i][j])+" ");
			}
			for (int j=0;j<N-1;j++){
				TC.print(moment_2_categories[i][j]+" ");
			}
			TC.println(moment_2_categories[i][N-1]);
		}
	}
	
	public double[] get_parametres(){
		double[] parametres=new double[10];
		parametres[0]=entropie_y;
		parametres[1]=norme_2_y;
		parametres[2]=coeff_fourier_moyen;
		parametres[3]=nbr_valeurs_moyennes;
		parametres[4]=indice_goertzel_moyen;
		parametres[5]=indice_goertzel2_moyen;
		parametres[6]=distance_plateau_moyen;
		parametres[7]=longueur_moyenne;
		parametres[8]=indice_reg_moyen;
		parametres[9]=range_deviation_moyen;
		parametres[10]=norme_2_z;
		parametres[11]=entropie_z;
		parametres[12]=regularite_moyenne;
		
		return parametres;
	}
}
