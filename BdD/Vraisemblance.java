package BdD;

public class Vraisemblance {
	
	public static String[] types={"NID DE POULE","PALIER DESCENDANT","PALIER MONTANT", "DOS D'ANE","PLATEAU","COUSSIN","BARRE/TROTTOIR","ROUTE MAUVAISE","PETITE IRREGULARITE DE LA ROUTE"};
	public static String[] indices={"entropie_y","norme_2_y","coeff_fourier","nombre_valeurs_moyennes","indice_goertzel","indice_goertzel_moyen","distance_plateau","longueur","indice_reg","range_deviation","norme 2 verticale","entropie verticale","regularite"};
	public static int N=types.length;
	public static int P=indices.length;
	
	public static double densite(double m, double s, double x){
		return 1/(1.414*s)*Math.exp(-(x-m)*(x-m)/(2*s*s));
	}
	
	public static double vraisemblance(double[] moyennes,double[] ecart_types, double[] x){
		double p=1;
		for (int i=0;i<moyennes.length;i++){
			p=p*densite(moyennes[i],ecart_types[i],x[i]);
		}
		return p;
	}
	
	public static int decision(double[] x){
		double[][][] resultat=Recup.extraction();
		double[][] moyennes_indices=resultat[1];
		double[][] moment_2_indices=resultat[2];
		double[][] ecart_type_indices=new double[moyennes_indices.length][moyennes_indices[0].length];
		for (int i=0;i<moyennes_indices.length;i++){
			for (int j=0;j<moyennes_indices[0].length;j++){
				ecart_type_indices[i][j]=Math.pow(moment_2_indices[i][j]-moyennes_indices[i][j]*moyennes_indices[i][j],0.5);
			}
		}
		double[][] moyennes_categories=new double[N][P];
		double[][] ecart_type_categories=new double[N][P];
		for (int i=0;i<N;i++){
			for (int j=0;j<P;j++){
				moyennes_categories[i][j]=moyennes_indices[j][i];
				ecart_type_categories[i][j]=ecart_type_indices[j][i];
			}
		}
		
		int[] presences_donnees=new int[N];
		double[] vraisemblance=new double[N];
		for (int i=0;i<N;i++){
			if (moyennes_indices[0][i]!=0){
				presences_donnees[i]=1;
				vraisemblance[i]=vraisemblance(moyennes_categories[i],ecart_type_categories[i],x);
			}
		}
		double L=0;
		int I=1;
		double r=0;
		for (int i=1;i<N;i++){
			if (vraisemblance[i]>L){
				r=vraisemblance[i]/L;
				L=vraisemblance[i];
				I=i;
			}
		}
		return I;
	}
}
