package com.psc.bumpy;

import java.util.Date;


public class AxisDetector
{
    double[][] changementDeRepere;								                                    //changementDeRepere[i][j] est la ième composante du vecteur colonne j de la matrice 3*3
    double lastUpdate;
    boolean updated;
    private static double FREQUENCYUPDATE=5000;
    AxisDetector()
    {
        updated=false;
        changementDeRepere=new double[3][3];
        lastUpdate=new Date().getTime();
    }

    //return true si le calcul des axes a pu se faire,false sinon
    boolean update(DataBuffer datas)
    {
        if(new Date().getTime()-lastUpdate<FREQUENCYUPDATE)
            return false;


        if(datas.getTime().size()==0 || datas.isAnObstacle()||datas.getTime().getLast()-datas.getTime().getFirst()<2000) //Si on est sur un obstacle, ou si l'échantillon est moins long que 2s, calculer le repère sol n'a pas d'intérêt
        {
            return false;
        }
        lastUpdate=new Date().getTime();
        updated=true;
        double moyX=0;
        double moyY=0;
        double moyZ=0;
        int dataSize=datas.getTime().size();
        for(int i=0;i<dataSize;i++)
        {																	                        //on moyenne l'entrée pour éviter des aberrations

            if(!datas.getX().isEmpty()&&!datas.getY().isEmpty()&&!datas.getZ().isEmpty())
            {
                moyX+=datas.getX().removeFirst();
                moyY+=datas.getY().removeFirst();
                moyZ+=datas.getZ().removeFirst();
            }


        }
        moyX/=dataSize;
        moyY/=dataSize;
        moyZ/=dataSize;
        Vector3d xMobile=new Vector3d(1,0,0);							                            //détermination z
        Vector3d yMobile=new Vector3d(0,1,0);
        Vector3d zMobile=new Vector3d(0,0,1);
        Vector3d[] rep={xMobile,yMobile,zMobile};
        Vector3d gravity=new Vector3d(moyX,moyY,moyZ);
        gravity.normalize();
        //On cherche un deuxième vecteur pour la base
        Vector3d vector2=new Vector3d(0,0,0);
        Vector3d vector3=new Vector3d(0,0,0);
        if(gravity.x<Vector3d.MINI)
            vector2=new Vector3d(1,0,0);
        if(gravity.y<Vector3d.MINI)
            vector2=new Vector3d(0,1,0);
        if(gravity.z<Vector3d.MINI)
            vector2=new Vector3d(0,0,1);
        else
        {
            //vector2=new Vector3d(-gravity.y/gravity.x,1,0);
            vector2=xMobile.vect(gravity);
            vector2.normalize();
        }
        //le troisième vecteur est perpendiculaire aux deux autres
        vector3=gravity.vect(vector2);
        vector3.normalize();
        for(int i=0;i<3;i++)
        {
            changementDeRepere[0][i]=rep[i].scalar(vector2);
            changementDeRepere[1][i]=rep[i].scalar(vector3);
            changementDeRepere[2][i]=rep[i].scalar(gravity);
        }
        return true;
    }

    double[] convertInGroundCoord(double[] mobileCoord)
    {
        if(mobileCoord.length!=3)
        {
            System.out.println("AxisDetector : vecteur d'entrée de dimension incorrecte, dimension = "+mobileCoord.length);
            return mobileCoord;
        }
        double[] groundCoord=new double[3];
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                groundCoord[i]+=changementDeRepere[i][j]*mobileCoord[j];
            }
        }
        return groundCoord;
    }
}

class Vector3d
{
    static double MINI =  0.0000001;
    double x;
    double y;
    double z;
    Vector3d(double x1,double y1,double z1)
    {
        x=x1;
        y=y1;
        z=z1;
    }

    Vector3d(Vector3d v)
    {
        x=v.x;
        y=v.y;
        z=v.z;
    }

    double scalar(Vector3d v)
    {
        return x*v.x+y*v.y+z*v.z;
    }

    double standard()
    {
        return Math.sqrt(x*x+y*y+z*z);
    }

    void normalize()
    {
        double standard=standard();
        x/=standard;
        y/=standard;
        z/=standard;
    }

    Vector3d vect(Vector3d v)
    {
        return new Vector3d(y*v.z-z*v.y,z*v.x-x*v.z,x*v.y-y*v.x);
    }

    Vector3d plus(Vector3d v)
    {
        return new Vector3d(x+v.x,y+v.y,z+v.z);
    }

    Vector3d minus(Vector3d v)
    {
        return new Vector3d(x-v.x,y-v.y,z-v.z);
    }

    Vector3d time(double m)
    {
        return new Vector3d(x*m,y*m,z*m);
    }
    static double angle(Vector3d v1, Vector3d v2)
    {
        double standardV1=v1.standard();
        double standardV2=v2.standard();
        if(standardV1<MINI||standardV2<MINI)
            return 0;
        else
            return  Math.acos(v1.scalar(v2)/standardV1/standardV2);

    }

    @Override
    public String toString()
    {
        return "x="+x+",y="+y+",z="+z;
    }
}