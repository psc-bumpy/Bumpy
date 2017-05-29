package BdD;

import tc.TC;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Indirect {
	public static double ks=50000;
	public static double ms=250;
	public static double lambda=1000;
	public static double mu=30;
	public static double ku=250000;
	

	
	public static double[] schema_inverse(double[] donnees_n, double[] ant, double temps_1, double temps_2, double accel){
		double delta=(temps_2-temps_1);
		double[] nouveau=new double[3];
		nouveau[1]=delta*delta*accel+2*donnees_n[1]-ant[1];
		nouveau[0]=1/(ks+lambda/delta)*(ms*accel+ks*nouveau[1]+lambda/delta*(nouveau[1]-donnees_n[1])+lambda/delta*donnees_n[0]);
		nouveau[2]=nouveau[0]+mu/ku/delta/delta*(nouveau[0]-2*donnees_n[0]+ant[0])+ms/ku*accel;
		return nouveau;
	}
	
	
	public static double[] donnees (double[][] mesures,double vitesse){


        double[] donnees=new double[18];

        double [][] total = new double[3][];
        double[] temps=mesures[3];
        total[0]=mesures[0];
        total[1]=mesures[1];
        total[2]=mesures[2];

        double [][] accel_total=new double[3][total[0].length];
        for (int i=0;i<accel_total[0].length;i++){
            accel_total[1][i]=((double)(total[1][i]));
            accel_total[0][i]=(double)(total[0][i]);
            accel_total[2][i]=-(double)(total[2][i]-9.81);
            
            
            /*
            accel_total[1][i]=((double)(total[1][i]))*9.81;
            accel_total[0][i]=(double)(total[0][i])*9.81;
            accel_total[2][i]=-(double)(total[2][i]-1.0)*9.81;
            */
            
        }

        accel_total=Outils.filtrage(accel_total,2);
        double[][] new_total=Outils.transforme(temps, accel_total);
        temps=new_total[0];
        accel_total[0]=new_total[1];
        accel_total[1]=new_total[2];
        accel_total[2]=new_total[3];
        Outils.correction_offset(accel_total, temps);

        //a ce stade les accelerations sont prêtes à l'emploi    


        double[][] vitesses=Outils.vitesse(accel_total, temps);
        double[][] position=Outils.vitesse(vitesses, temps);
        double[][] measures=new double[2][temps.length];
        measures[0]=temps;
        measures[1]=accel_total[2];

        donnees[7]=vitesse;
        donnees[0]=Outils.ecart_premier_dernier(accel_total[2], temps)*donnees[7];
        donnees[1]=Classement.pics_plus(accel_total[2], temps).size()+Classement.pics_moins(accel_total[2], temps).size();
        donnees[2]=Classement.signe_premier_pic(accel_total[2]);
        donnees[3]=Classement.indice_regularite(accel_total[2]);
        donnees[4]=Outils.parametres_plateau(accel_total, temps, donnees[7], donnees[2]);
        donnees[5]=Math.max(Outils.maximum(accel_total[2]),-Outils.minimum(accel_total[2]));
        donnees[6]=Classement.rang_deviation(accel_total[2]);
        donnees[8]=Classement.nbr_medium_values(accel_total[2]);
        donnees[9]=Classement.entropie(accel_total)[0];
        donnees[10]=Outils.norme_2(accel_total[0], temps)/accel_total[0].length;
        donnees[11]=Outils.coeff_fourier(accel_total[2], 2 , temps);
        if (donnees[5]>12){
			donnees[12]=1; ///aberrant route mauvaise
		}
		else if (donnees[0]==0){
			donnees[12]=2; ///aberrant petite irregularite
		}
		else{
			donnees[12]=0;
		}
        donnees[13]=Outils.indice_g(accel_total[2]);
        donnees[14]=Outils.indice_g2(accel_total[2]);
        donnees[15]=Classement.entropie(accel_total)[2];
		donnees[16]=Outils.norme_2(accel_total[2], temps)/accel_total[0].length;
		donnees[17]=Outils.regularite(accel_total[2]);
        
        return donnees;
	}

}
