package BdD;

import tc.TC;

//PARTIE SECRETE MACHINE LEARNING

public class Parametres_classification {
	
	public static String[] types={"NID DE POULE","PALIER DESCENDANT","PALIER MONTANT", "DOS D'ANE","PLATEAU","COUSSIN","BARRE/TROTTOIR","ROUTE MAUVAISE","PETITE IRREGULARITE DE LA ROUTE"};
	public static String[] indices={"entropie_y","norme_2_y","coeff_fourier","nombre_valeurs_moyennes","indice_goertzel","indice_goertzel_moyen","distance_plateau","longueur","indice_reg","range_deviation","norme 2 verticale","entropie verticale","regularite"};
	
/* données de base, les moyennes des indices suivant par catégories (9 catégories, donc tant de tableaux de taille 9)
 * entropie
 * norme_2_y
 * coeff_fourier
 * nombre_valeurs_moyenne
 * indice_goertzel
 * indice_goertzel2
 * distance_plateau
 * longueur 
 * indice_reg_moyen
 * range_deviation_moyen
 * eventuellement d'autres à trouver
 * les ecart types associés comme en econométrie, on fera un t_test
 * moyennes[i][j] moyenne pour le ième paramètre, sur la jème catégorie (cf méthode obstacle)
 */
	public static void mis_a_jour_parametres(double[][] moyennes_categories, double[][] moment_2_categories){
		//pour chaque tableau, trouver les extremaux, et voir par un t-test si l'hypothèse nulle est accpetable
		//si elle est rejetée, on peut désormais prendre en compte ce paramètre comme classifiant positivement la catégorie
		//on imprime un fichier texte qui regroupe pour chaque catégorie ceux des indices qui sont significatifs vu ce qu'on a
		//avec le seuil données par le test à 75% mettons grace a l'ecart type, et le sens de comparaison
		//enregistrer le fichier texte pour l'utilisation suivante
		double[][] ecart_type_categories=new double[moyennes_categories.length][moyennes_categories[0].length];
		for (int i=0;i<moyennes_categories.length;i++){
			for (int j=0;j<moyennes_categories[0].length;j++){
				ecart_type_categories[i][j]=Math.pow(moment_2_categories[i][j]-moyennes_categories[i][j]*moyennes_categories[i][j],0.5);
			}
		}
		TC.ecritureDansNouveauFichier("Parametres_code.txt");
		for (int i=0;i<moyennes_categories.length;i++){
			double M_1=0;
			double M_2=0;
			int j_1=0;
			int j_2=0;
			for (int j=0;j<moyennes_categories[0].length;j++){
				if (moyennes_categories[i][j]>=M_1){
					M_2=M_1;
					j_2=j_1;
					M_1=moyennes_categories[i][j];
					j_1=j;
				}
				else if (moyennes_categories[i][j]>=M_2){
					j_2=j;
					M_2=moyennes_categories[i][j];
				}
			}
			if (moyennes_categories[i][j_1]-ecart_type_categories[i][j_1]>moyennes_categories[i][j_2]+ecart_type_categories[i][j_2]){
				TC.println(i+" "+j_1+" "+"superieur "+(M_1-ecart_type_categories[i][j_1]));
			}
			double N_1=100;
			double N_2=100;
			int k_1=0;
			int k_2=0;
			for (int k=0;k<moyennes_categories[0].length;k++){
				if (moyennes_categories[i][k]<=N_1 && moyennes_categories[i][k]>0){
					N_2=N_1;
					k_2=k_1;
					N_1=moyennes_categories[i][k];
					k_1=k;
				}
				else if (moyennes_categories[i][k]<=N_2 && moyennes_categories[i][k]>0){
					k_2=k;
					N_2=moyennes_categories[i][k];
				}
			}
			if (moyennes_categories[i][k_2]-ecart_type_categories[i][k_1]>moyennes_categories[i][k_1]+ecart_type_categories[i][k_1]){
				TC.println(i+" "+k_1+" "+"inferieur "+(N_1+ecart_type_categories[i][k_1]));
			}
		}
	}
}
