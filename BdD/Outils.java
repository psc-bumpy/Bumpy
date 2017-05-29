package BdD;

import java.util.Arrays;

public class Outils {
	
	public static double[] traitement(double [] mesures){
		int i=0;
		while (Math.abs(mesures[i+1]-mesures[i])<0.01){
			mesures[i]=0;
			i=i+1;
		}
		return mesures;
	}
	
	public static void correction_offset(double[][] accel_total,double[] temps){
		double correction_x=integrate(accel_total[0],temps)/temps[temps.length-1];
		double correction_y=integrate(accel_total[1],temps)/temps[temps.length-1];
		double correction_z=integrate(accel_total[2],temps)/temps[temps.length-1];
		for (int i=0;i<temps.length;i++){
				accel_total[0][i]=accel_total[0][i]-correction_x;
				accel_total[1][i]=accel_total[1][i]-correction_y;
				accel_total[2][i]=accel_total[2][i]-correction_z;
		}
	}
	
	public static double[][] vitesse(double[][] accel_total, double[] temps){
		int n=temps.length;
		double[][] vitesse_total=new double[3][n];
		for (int i=1;i<n;i++){
			for (int j=0;j<3;j++){
				vitesse_total[j][i]=vitesse_total[j][i-1]+(temps[i]-temps[i-1])*(accel_total[j][i-1]+accel_total[j][i])/2;
			}
		}
		return vitesse_total;
	}
	public static double integrate(double[] tableau, double[] temps){
		double s=0;
		for (int a=1;a<temps.length;a++)
			s=s+tableau[a]*(temps[a]-temps[a-1]);
		return s;
	}
	
	
	public static double gauss(double sigma){
		double s=0;
		for (int i=0;i<100;i++)
			s=s+Math.random();
		return sigma*10*(s/100-0.5);
	}
	
	public static double max (double[] mesures,int a){
		double[] current=new double[40];
		for (int i=a;i<a+40;i++){
			current[i-a]=Math.abs(mesures[i]);
		}
		Arrays.sort(current);
		return current[37];
	}
	
	public static double max_restant(double[][] mesures, int a){
		double M=Math.abs(mesures[2][a]);
		if (a>20){
		for (int i=a-20;i<mesures[0].length;i++){
			if (Math.abs(mesures[2][i])>M)
				M=Math.abs(mesures[2][i]);
		}
		return M;
		}
		else{
			return 10;
		}
	}
	
	public static double[][] filtrage (double[][] mesures, double seuil){
		boolean obstaclepasse=false;
		double[][] mesures_new=new double[3][mesures[2].length];
		for (int i=0;i<mesures[2].length-40;i++){
			if ((max(mesures[2],i)<seuil && obstaclepasse==false) || max_restant(mesures,i)<0.8){
				mesures_new[0][i]=0;
				mesures_new[1][i]=0;
				mesures_new[2][i]=0;
			}
			else{
				mesures_new[0][i]=mesures[0][i];
				mesures_new[1][i]=mesures[1][i];
				mesures_new[2][i]=mesures[2][i];
				obstaclepasse=true;
			}
		}
		return mesures_new;			
	}
	
	public static double[][] transforme(double[] temps, double[][] accel_total){
		int a=0;
		int fin=0;
		boolean e=false;
		while (e==false){
			e=true;
			for (int i=fin;i<temps.length;i++){
				if (accel_total[0][i]!=0){
					e=false;
				}
			}
			fin=fin+1;
		}
		while (accel_total[0][a]==0 && a<fin-1){
			a=a+1;
		}
		if (a==accel_total[0].length){
			return accel_total;
		}
		double[][] total=new double[4][fin-a];
		for (int i=a;i<fin;i++){
			total[0][i-a]=temps[i]-temps[a];
			total[1][i-a]=accel_total[0][i];
			total[2][i-a]=accel_total[1][i];
			total[3][i-a]=accel_total[2][i];
		}
		return total;
	}
	

	
	
	public static int pic1_haut(double[] tab, double[] temps){
		int i=0;
 
			i=i+1;
		return maximum_local(tab,i);
	}
	
	public static int maximum_local(double[] tab, int i_0){
		int I=Math.max(0,i_0-10);
		double M=tab[I];
		for (int i=I;i<Math.min(i_0+15,tab.length-1);i++){
			if (tab[i]>M){
				M=tab[i];
				I=i;
			}
		}
		return I;
	}
	
	public static int minimum_local(double[] tab, int i_0){
		double m=tab[Math.max(i_0-10,0)];
		int I=Math.max(i_0-10,0);
		for (int i=Math.max(i_0-9,0);i<i_0+15;i++){
			if (tab[i]<m){
				m=tab[i];
				I=i;
			}
		}
		return I;
	}
	
	public static int maximum(double[] tab){
		int i=0;
		double m=tab[0];
		for( int j=0;j<tab.length;j++){
			if (tab[j]>m){
				i=j;
				m=tab[i];
			}			
		}
		return i;
	}

	public static int pic1_bas(double[] tab, double[] temps){
		int i=0;
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])>-1 || tab[i+10]>-2) &&  i+11<tab.length){
			i=i+1;
			}
		if (i+11==tab.length){
			return -1;
		}
		return minimum_local(tab,i);
	}
	
	public static int minimum(double[] tab){
		int i=0;
		double m=tab[0];
		for( int j=0;j<tab.length;j++){
			if (tab[j]<m){
				i=j;
				m=tab[i];
			}			
		}
		return i;
	}
	
	public static int pic2_haut (double[] tab, double[] temps){
		int i=pic1_haut(tab,temps);
		i=i+20;
		if (i+10>tab.length-1){
			return -1;
		}
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])<10 || tab[i+10]<2) && i+11<tab.length){
			i=i+1;
		}
		if (i+11==tab.length){
			return -1;
		}
		return maximum_local(tab,i);
	}
	
	public static int pic3_haut (double[] tab, double[] temps){
		int i=pic2_haut(tab,temps);
		i=i+20;
		if (i+10>tab.length-1){
			return -1;
		}
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])>10 || tab[i+10]<2) && i+11<tab.length){
			i=i+1;
		}
		if (i+11==tab.length){
			return -1;
		}
		return maximum_local(tab,i);
	}
	
	public static int pic4_haut (double[] tab, double[] temps){
		int i=pic3_haut(tab,temps);
		i=i+20;
		if (i+10>tab.length-1){
			return -1;
		}
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])>10 || tab[i+10]<2) && i+11<tab.length){
			i=i+1;
		}
		if (i+11==tab.length){
			return -1;
		}
		return maximum_local(tab,i);
	}
	
	public static int pic2_bas (double[] tab, double[] temps){
		int i=pic1_bas(tab,temps);
		i=i+20;
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])>-7 || tab[i+10]>-2)&&  i+11<tab.length){
			i=i+1;
			}
		if (i+11==tab.length){
			return -1;
		}
		return minimum_local(tab,i);
	}
	
	public static int pic3_bas (double[] tab, double[] temps){
		int i=pic2_bas(tab,temps);
		i=i+20;
		if (i+10>tab.length-1){
			return -1;
		}
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])>-7 || tab[i+10]>-2)&&  i+11<tab.length){
			i=i+1;
			}
		if (i+11==tab.length){
			return -1;
		}
		return minimum_local(tab,i);
	}
	public static int pic4_bas (double[] tab, double[] temps){
		int i=pic3_bas(tab,temps);
		i=i+20;
		if (i+10>tab.length-1){
			return -1;
		}
		while (((tab[i+10]-tab[i])/(temps[i+10]-temps[i])>-7 || tab[i+10]>-2)&&  i+11<tab.length){
			i=i+1;
			}
		if (i+11==tab.length){
			return -1;
		}
		return minimum_local(tab,i);
	}
	
	public static double ecart_premier_dernier(double[] accel, double[] temps){
		int i=0;
		while (Math.abs(accel[i])<2 && i<accel.length-1){
			i=i+1;
		}
		if (i<accel.length-1){
		for (int j=i;j<accel.length;j++){
			boolean e=true;
			for (int k=j;k<accel.length;k++){
				if (Math.abs(accel[k])>2){
					e=false;
				}
			}
			if (e==true){
				return temps[j]-temps[i];
			}
		}
		return temps[accel.length-1]-temps[i];
		}
		else{
			return 0;
		}
	}
	
	public static double distance_courbe(double[] tab_1,double[] tab_2){
		int l=Math.min(tab_1.length,tab_2.length);
		double e=0;
		for (int i=0;i<l;i++){
			e=e+(tab_1[i]-tab_2[i])*(tab_1[i]-tab_2[i]);
		}
		return e/l;
	}
	
	public static double parametres_plateau(double[][] accel_total, double[] temps, double vitesse, double signe){
		double[][] vitesse_totale=Outils.vitesse(accel_total, temps);
		Outils.correction_offset(vitesse_totale, temps);
		double[][] position_totale=Outils.vitesse(accel_total, temps);
		//for (int i=0;i<position_totale[0].length;i++){
			//System.out.println(position_totale[2][i]);
		//}
		return Outils.norme_2(position_totale[2],temps)/accel_total[0].length;
	}
	public static double[] lissage (double[] accel){
		double [] nouveau= new double[accel.length];
		nouveau[0]=accel[0];
		for (int j=1;j<accel.length;j++){
			nouveau[j]=(accel[j]+accel[j-1])/2;
		}
		return nouveau;
	}
	
	public static double norme_2(double[] accel,double[] temps){
		double s=0;
		double[] nouveau=lissage(accel);
		for (int j=1;j<accel.length;j++){
			s=s+nouveau[j]*nouveau[j]*(temps[j]-temps[j-1]);
		}
		return s;
	}
	
	public static double coeff_fourier(double[] accel, double freq, double[] temps){
		double s=0;
		double m=0;
		double[] nouveau=lissage(accel);
		int k=0;
		for (int n=0; n<1000 ;n++){
			for (int i=1;i<accel.length;i++){
				s=s+(temps[i]-temps[i-1])*nouveau[i]*Math.cos(2*Math.PI*freq*temps[i]);
			}
			if (m<s){
				m=s;
			}
		}
		return m/norme_2(accel,temps);
	}
	
	public static double coeff_fourier_sin(double[] accel, double freq, double[] temps){
		double s=0;
		double m=0;
		double[] nouveau=lissage(accel);
		int k=0;
		for (int n=0; n<1000 ;n++){
			for (int i=1;i<accel.length;i++){
				s=s+(temps[i]-temps[i-1])*nouveau[i]*Math.sin(2*Math.PI*freq*temps[i]);
			}
			if (m<s){
				m=s;
			}
		}
		return m/norme_2(accel,temps);
	}
	
	public static double goertzel(double[] accel, double freq){
		int N=accel.length;
		double e=50;
		//double e=100;
		int k=(int)(0.5+N*freq/e);
		double w=k*2*Math.PI/N;
		double coeff=2*Math.cos(w);
		double q_1=0;
		double q_2=0;
		for (int i=0;i<N;i++){
			double q_0=coeff*q_1-q_2+accel[i];
			q_2=q_1;
			q_1=q_0;
		}
		return q_1*q_1+q_2*q_2-q_1*q_2*coeff;
	}
	
	public static double indice_g(double[] accel){
		double[] tab_goertzel=new double[50];
		for (int i=1;i<50;i++){
			tab_goertzel[i]=goertzel(accel,i);
		}
		double s_1=0;
		double s_2=0;
		for (int k=1;k<8;k++){
			s_1=s_1+tab_goertzel[k];
		}
		for (int k=8;k<18;k++){
			s_2=s_2+tab_goertzel[k];
		}
		return s_2/s_1;
	}
	public static double indice_g2(double[] accel){
		double[] tab_goertzel=new double[50];
		for (int i=1;i<50;i++){
			tab_goertzel[i]=goertzel(accel,i);
		}
		double s_1=0;
		double s_2=0;
		for (int k=8;k<13;k++){
			s_1=s_1+tab_goertzel[k];
		}
		for (int k=13;k<18;k++){
			s_2=s_2+tab_goertzel[k];
		}
		return s_1/s_2;
	}
	
	public static double regularite(double[] accel){
		int n=accel.length;
		double t=0;
		for (int i=1;i<n-1;i++){
			if ((accel[i]-accel[i-1])*(accel[i+1]-accel[i])<0){
				t=t+1;
			}	
		}
		return t/(n+0.);
	}
}
