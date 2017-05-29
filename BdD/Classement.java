package BdD;

import java.util.LinkedList;

public class Classement {
	public static double[] integration (double[] accel, double[] temps){
		//intégration de l'acceleration, supposée filtrée, ie pas de déviation lineaire moyenne
		double[] vit=new double [accel.length];
		for (int i=1;i<accel.length;i++){
			vit[i]=vit[i-1]+(temps[i]-temps[i-1])*accel[i];
		}
		return vit;
	}
	
	public static double[][][] sample(double[][] accel_total, int taille){
		//on échantillonne en K sous vecteurs les 3 accelérations x y z
		int d=(int)(accel_total[0].length/taille);
		double [][][] accel_sampled=new double[3][taille][d];
		for (int i=0;i<accel_total[0].length;i++){
			int q=i/d;
			int r=i-q*d;
			accel_sampled[0][q][r]=accel_total[0][i];
			accel_sampled[1][q][r]=accel_total[1][i];
			accel_sampled[2][q][r]=accel_total[2][i];
		}
		return accel_sampled;
	}
	
	public static double moyenne(double[] tableau){
		double m=0;
		for (int i=0;i<tableau.length;i++){
			m=m+tableau[i];
		}
		return m/(double)(tableau.length);
	}
	
	public static double ecart_type(double[] tableau){
		double m=moyenne(tableau);
		double s=0;
		for (int i=0;i<tableau.length;i++){
			s=s+(tableau[i]-m)*(tableau[i]-m);
		}
		return Math.sqrt(s/tableau.length);
	}
	
	public static double rang_deviation(double[] tableau){
		double m=moyenne(tableau);
		double r=0;
		for (int i=0;i<tableau.length;i++){
			r=r+Math.abs(tableau[i]-m);
		}
		return r/tableau.length;
	}
	
	public static double M_4(double[] tableau){
		double m=moyenne(tableau);
		double M_4=0;
		for (int i=0;i<tableau.length;i++){
			M_4=M_4+Math.pow(tableau[i]-m, 4);
		}
		return Math.pow(M_4/tableau.length,0.25);
	}
	
	public static double amplitude(double[] tableau){
		//retourne l'ecart entre les deux indices du min et max du tableau 
		double m=tableau[0];
		double M=tableau[0];
		int i=0;
		int j=0;
		for (int k=1;k<tableau.length;k++){
			if (tableau[k]<m){
				m=tableau[k];
				i=k;
			}
			if (tableau[k]>M){
				M=tableau[k];
				j=k;
			}
		}
		return Math.abs(i-j);
	}
	
	public static double indice_regularite(double[] tableau){
		double freq=100;
		double I=0;
		for (int i=0;i<tableau.length-2;i++){
			I=I+Math.pow((tableau[i+2]-2*tableau[i+1]+tableau[i])*freq,2);
		}
		return I/tableau.length;
	}
	
	public static double covariance(double[] tab_1, double[] tab_2){
		double m_1=moyenne(tab_1);
		double m_2=moyenne(tab_2);
		double cv=0;
		for(int i=0;i<Math.min(tab_1.length, tab_2.length);i++){
			cv=cv+(tab_1[i]-m_1)*(tab_2[i]-m_2);
		}
		return cv;
	}
	
	public static double[][][] indiv_features(double[][] accel_total, int taille){
		//renvoie les moyenne/ecarts types/ecart a la moyenne/moments 4
		double [][][] accel_sampled=sample(accel_total,taille);
		double [][][] indiv_features=new double[3][4][taille];
		for (int i=0;i<taille;i++){
			for (int j=0;j<3;j++){
				indiv_features[j][0][i]=moyenne(accel_sampled[j][i]);
			}
			for (int j=0;j<3;j++){
				indiv_features[j][1][i]=ecart_type(accel_sampled[j][i]);
			}
			for (int j=0;j<3;j++){
				indiv_features[j][2][i]=rang_deviation(accel_sampled[j][i]);
			}
			for (int j=0;j<3;j++){
				indiv_features[j][3][i]=M_4(accel_sampled[j][i]);
			}
		}
		return indiv_features;
	}
	
	public static double[][][] variance_matrix(double[][] accel_total,int taille){
		double [][][] accel_sampled=sample(accel_total,taille);
		double [][][] variance_matrix=new double[3][taille][taille];
		for (int i=0;i<taille;i++){
			for (int j=0;j<taille;j++){
				for (int l=0;l<3;l++){
					variance_matrix[l][i][j]=covariance(accel_sampled[l][i],accel_sampled[l][j]);
				}
			}
		}
		return variance_matrix;
	}
	
	public static int nbr_high_values(double[] accel){
		int N=0;
		for (int i=0;i<accel.length;i++){
			if (Math.abs(accel[i])>2){
				N=N+1;
			}
		}
		return N;
	}
	
	public static double nbr_medium_values(double[] accel){
		int N=0;
		for (int i=0;i<accel.length;i++){
			if (Math.abs(accel[i])>1 && Math.abs(accel[i])<2){
				N=N+1;
			}
		}
		return (N+0.)/accel.length;
	}
	
	public static double prop_low_values(double[] accel){
		int N=0;
		for (int i=0;i<accel.length;i++){
			if (Math.abs(accel[i])<0.3){
				N=N+1;
			}
		}
		return N/(accel.length+0.);
	}
	public static double[][] differential_signal(double[][] accel_total){
		double[][] new_accel_total=new double [3][accel_total[0].length];
		new_accel_total[0][0]=accel_total[0][0];
		new_accel_total[0][1]=accel_total[0][1];
		new_accel_total[1][0]=accel_total[1][0];
		new_accel_total[1][1]=accel_total[1][1];
		new_accel_total[2][0]=accel_total[2][0];
		new_accel_total[2][1]=accel_total[2][1];
		double delta_x=accel_total[0][1]-accel_total[0][0];
		double delta_y=accel_total[1][1]-accel_total[1][0];
		double delta_z=accel_total[2][1]-accel_total[2][0];

		for (int i=2;i<accel_total[0].length;i++){
			double epsilon_x=accel_total[0][i]-accel_total[0][i-1];
			double epsilon_y=accel_total[1][i]-accel_total[1][i-1];
			double epsilon_z=accel_total[2][i]-accel_total[2][i-1];
			if (epsilon_x*delta_x>0 && Math.abs(epsilon_x)>0.4){
				new_accel_total[0][i]=new_accel_total[0][i-1]+epsilon_x;
			}
			if (epsilon_x*delta_x<=0 && Math.abs(epsilon_x)>0.4){
				new_accel_total[0][i]=epsilon_x;
			}
			
			if (epsilon_y*delta_y>0 && Math.abs(epsilon_y)>0.4){
				new_accel_total[1][i]=new_accel_total[1][i-1]+epsilon_y;
			}
			if (epsilon_y*delta_y<=0 && Math.abs(epsilon_y)>0.4){
				new_accel_total[1][i]=epsilon_y;
			}
			
			if (epsilon_z*delta_z>0 && Math.abs(epsilon_z)>0.4){
				new_accel_total[2][i]=new_accel_total[2][i-1]+epsilon_z;
			}
			if (epsilon_z*delta_z<=0 && Math.abs(epsilon_z)>0.4){
				new_accel_total[2][i]=epsilon_z;
			}
		}
		return new_accel_total;
	}
	
	
	public static LinkedList<Double> pics_plus (double[] accel, double[] temps){
		LinkedList<Double> l=new LinkedList<Double>();
		int pic1=Outils.pic1_haut(accel,temps);
		if (pic1>=0){
			l.add(temps[pic1]);
			int pic2=Outils.pic2_haut(accel,temps);
			if (pic2>=0){
				l.add(temps[pic2]);
				int pic3=Outils.pic3_haut(accel,temps);
				if (pic3>=0){
					l.add(temps[pic3]);
					int pic4=Outils.pic4_haut(accel,temps);
					if (pic4>=0){
						l.add(temps[pic4]);
					}
				}
			}	
		}
		return l;
	}
	
	public static LinkedList<Double> pics_moins (double[] accel, double[] temps){
		LinkedList<Double> l=new LinkedList<Double>();
		int pic1=Outils.pic1_bas(accel,temps);
		if (pic1>=0){
			l.add(temps[pic1]);
			int pic2=Outils.pic2_bas(accel,temps);
			if (pic2>=0){
				l.add(temps[pic2]);
				int pic3=Outils.pic3_bas(accel,temps);
				if (pic3>=0){
					l.add(temps[pic3]);
					int pic4=Outils.pic4_bas(accel,temps);
					if (pic4>=0){
						l.add(temps[pic4]);
					}
				}
			}	
		}
		return l;
	}
	public static double regularity_btw_pics(double[] accel,double[] temps){
		int pic1=Outils.pic1_haut(accel, temps);
		int pic2=Outils.pic2_haut(accel, temps);
		if (pic2>0){	
			if (temps[pic2]-temps[pic1]>0.5){
				double I=0;
				for (int i=pic1+10;i<pic2-10;i++){
					I=I+accel[i]*accel[i];
				}
				return I/(pic2-pic1+20);
			}
		}
		return 0;
	}
	
	public static int signe_premier_pic(double[] accel){
		int i=0;
		while (Math.abs(accel[i])<2.2 && i<accel.length-1){
			i=i+1;
		}
		if (i==accel.length-1){
			return 0;
		}
		if (accel[i]>0){
			return 1;
		}
		return -1;
	}
	
	public static double[][] repartition(double[][] accel){
		double[][] repartition=new double[3][18];
		for (int i=0;i<accel[0].length;i++){
			for (int j=0;j<3;j++){
				if (accel[j][i]>4){
					repartition[j][17]=repartition[j][17]+1;
				}
				else if (accel[j][i]<-4){
					repartition[j][0]=repartition[j][0]+1;
				}
				else{
					int n=(int)((accel[j][i]+4)/0.5);
					repartition[j][n]=repartition[j][n]+1;
				}
			}
		}
		int N=accel[0].length;
		for (int i=0;i<18;i++){
			for (int j=0;j<3;j++){
				repartition[j][i]=repartition[j][i]/N;
			}
		}
		return repartition;
	}
	
	public static double[] entropie(double[][] accel){
		double[][] repartition=repartition(accel);
		double[] s=new double[3];
		for (int i=0;i<18;i++){
			for (int j=0;j<3;j++){
				if (repartition[j][i]>0){
					s[j]=s[j]-repartition[j][i]*Math.log(repartition[j][i]);
				}
			}
		}
		return s;
	}

}
