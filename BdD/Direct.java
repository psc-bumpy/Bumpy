package BdD;

public class Direct {
	public static double ks=25000;
	public static double ms=250;
	public static double lambda=1000;
	public static double mu=30;
	public static double ku=250000;
	
	public static double delta_x = 0.01;
	public static double delta_t= 0.001;
	
	public static double sol (double[] tab, double pos){
		int a=(int)(pos/delta_x);
		double r=pos/delta_x-a;
		if (a+1<tab.length)
			return tab[a]+r*(tab[a+1]-tab[a]);
		return tab[a];
	}
	
	public static double integrate(double[] tab){
		double s=0;
		for (int i=0;i<tab.length;i++)
			s=s+tab[i];
		return s*0.001;
	}
	public static double[] implement (double [] x,double [] z_rtab, double vit_1, double vit_2){
		double[] x_prime={0,0,0,0,0,0,0} ;
		x_prime[6]=x[6]+(vit_1+vit_2)/2*delta_t;
		x_prime[1] = x[1]+ delta_t*x[2]; //z_s point
		x_prime[0]=x[0]+delta_t*(x[1]+x_prime[1])/2; //z_s
		x_prime[4]=x[4]+delta_t*x[5]; //z_u point
		x_prime[3]=x[3]+delta_t*(x[4]+x_prime[4])/2; //z_u
		x_prime[2]=-ks/ms*(x_prime[0]-x_prime[3])-lambda/ms*(x_prime[1]-x_prime[4]); //z_s point point
		x_prime[5]=-ku/mu*(x_prime[3]-sol(z_rtab,x_prime[6]))-ms/mu*x_prime[2]; //z_u point point
		return (x_prime);
		}
		
	public static void main (String[] args){
		double [] z_rtab = new double[4000] ;  
		int l=z_rtab.length;
		for (int z=0;z<l;z++){
			z_rtab[z]=0.02*Math.exp(-(z*0.01-5)*(z*0.01-5)/4);
		}
		for (int z=0;z<l;z++){
			if (z<100 )
				z_rtab[z]=0;
			else if( z<200){
				z_rtab[z]=0.2*(z-100)*(300-z)/10000;
			}
			else if(z<452){
				z_rtab[z]=z_rtab[199];
			}
			else if(z<552){
				z_rtab[z]=0.2*(z-352)*(552-z)/10000;
			}
			else{
				z_rtab[z]=0;
			}
			System.out.println(z_rtab[z]);
		}
		
		double [] vit = new double[4000];   //sur la base des temps
		int t=vit.length;
		for (int v=0;v<t;v++){
			vit[v]=10;
		}
		int max=0;
		double[][] tab_var_cin=new double[4000][7];
		tab_var_cin[0][0]=0;
		tab_var_cin[0][1]=0;
		tab_var_cin[0][2]=0;
		tab_var_cin[0][3]=0;
		tab_var_cin[0][4]=0;
		tab_var_cin[0][5]=0;
		tab_var_cin[0][6]=0;
		for (int i=0;i<t-1;i++){
			if (tab_var_cin[i][6] < l*delta_x){
				tab_var_cin[i+1]=implement(tab_var_cin[i],z_rtab,vit[i],vit[i+1]);
				max=i+1;
			}
		}
		double[][] accel=new double[2][4000];
		double[][] profil=new double[2][1000];
		for (int i=0;i<max;i++){
			accel[1][i]=(tab_var_cin[i][2]);
			accel[0][i]=i*delta_t;
		}
		for (int i=0;i<1000;i++){
			profil[0][i]=i*delta_x;
			profil[1][i]=z_rtab[i];
		}
		//ChartViewer.launch(accel,"temps","accel","profil");
		//ChartViewer.launch(profil,"temps","route","profil");
	}
}

