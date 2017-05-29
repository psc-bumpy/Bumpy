package BdD;

import tc.TC;
import java.util.*;

//cette classe traite le fichier sorti par l'application fait de:
//4 colonnes de chiffres
//la premiËre la deuxiËme et la troisiËme c'est les accÈlÈrations selon x y et z
//la quatriËme la diffÈrence de temps entre la mesure n et la n-1

public class Recup {
	
	public static String[] types={"NID DE POULE","PALIER DESCENDANT","PALIER MONTANT", "DOS D'ANE","PLATEAU","COUSSIN","BARRE/TROTTOIR","ROUTE MAUVAISE","INCLASSABLE"};
	public static String[] indices={"entropie_y","norme_2_y","coeff_fourier","nombre_valeurs_moyennes","indice_goertzel","indice_goertzel_moyen","distance_plateau","longueur","indice_reg","range_deviation"};
	public static int N=types.length;
	public static int P=indices.length;
	
	public static String[] recuperation(){
		TC.lectureDansFichier("accel_1116_133656.txt");
		String[] res = new String[4];
		String ligne0 = TC.lireLigne();
		String[] l0= TC.motsDeChaine(ligne0);
		res[0]=l0[0];
		res[1]=l0[1];
		res[2]=l0[2];
		res[3]=l0[3];
		while (!TC.finEntree()){
			String ligne = TC.lireLigne();
			String[] l = TC.motsDeChaine(ligne);
			res[0]=res[0]+" "+l[0];
			res[1]=res[1]+" "+l[1];
			res[2]=res[2]+" "+l[2];
			res[3]=res[3]+" "+l[3];
		}
		return res;
	}
	
	public static String envoi_internet(){
		String res;
		TC.lectureDansFichier("Charles2.txt");
		String ligne0 = TC.lireLigne();
		res=ligne0;
		while (!TC.finEntree()){
			String ligne = TC.lireLigne();
			res=res+"#"+ligne;
		}
		return res;
	}
	
	public static Float[][] traiter(){
			 
	//listes chaÓnÈes qui contiendront la suite des valeurs des accÈlÈrations selon les trois axes et le temps
		
			 LinkedList<Float> valx = new LinkedList<Float>();
			 LinkedList<Float> valy = new LinkedList<Float>();
			 LinkedList<Float> valz = new LinkedList<Float>();
			 LinkedList<Float> valt = new LinkedList<Float>();
			 Float[][] totale = new Float[4][];
		
	// on remplit le premier ÈlÈment des listes (pour les temps il faut le premier)
			 String lign = TC.lireLigne();	 
		     String[] valeur = TC.motsDeChaine(lign);
		     
	//il faut convertir les strings en float dans le tableau valeurs (qui est fait des strings lus sur le fichier)
		    
		     int l=valeur.length;
		     
	
	//boucle pour lire toutes les lignes
		     
			 while (!TC.finEntree()) { 
			
			 String ligne = TC.lireLigne();	 
		     String[] valeurs = TC.motsDeChaine(ligne);
		     
		     //il faut convertir les strings en float dans le tableau valeurs
		    
		     valx.addLast(Float.parseFloat(valeurs[0]));
		     valy.addLast(Float.parseFloat(valeurs[1]));
		     valz.addLast(Float.parseFloat(valeurs[2]));
		     //valt.addLast(Float.parseFloat(valeurs[3])+valt.getLast());
		     valt.addLast(Float.parseFloat(valeurs[3]));
			 }
			 
		//l‡ on va transformer les listes en tableaux de float.
			 
		     Float[] valeurx = valx.toArray(new Float[0]); // convertit liste chainÈe en tableau de float
		     Float[] valeury = valy.toArray(new Float[0]);
		     Float[] valeurz = valz.toArray(new Float[0]);
		     Float[] valeurt = valt.toArray(new Float[0]);
		     
		//totale va contenir les 4 tableaux
		     
			 totale[0]=(valeurt);
			 totale[1]=(valeurx);
			 totale[2]=(valeury);
			 totale[3]=(valeurz);
			 for (int i=0;i<totale[2].length;i++){
				 //System.out.println(totale[0][i]);
			 }
			 for (int i=0;i<totale[2].length;i++){
				 //System.out.println(totale[3][i]);
			 }
			 return totale;	 
		}
	
		public static double[][][] extraction(){
			TC.lectureDansFichier("Moyennes_obstacle.txt");
			int i=0;
			String ligne_0 = TC.lireLigne();	 
		    String[] valeurs_0 = TC.motsDeChaine(ligne_0);
		    double [] nbr_passages=new double[N];
		    for (int k=0;k<N;k++){
		    	nbr_passages[k]=(double)(Double.parseDouble(valeurs_0[k]));
		    }
		    double[][] tab={nbr_passages};
			double[][] moyenne=new double[P][N];
			double[][] ecart_type=new double[P][N];
			while (!TC.finEntree()) { 
				String ligne = TC.lireLigne();	 
			    String[] valeurs = TC.motsDeChaine(ligne);
			    for (int j=0;j<N;j++){
			    	moyenne[i][j]=(double)(Double.parseDouble(valeurs[j]));
			    	ecart_type[i][j]=(double)(Double.parseDouble(valeurs[j+N]));
			    }
			    i=i+1;
			}
			double [][][] resultat={tab,moyenne,ecart_type};
			return resultat;
		}
	}
