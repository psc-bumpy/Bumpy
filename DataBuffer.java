package com.psc.bumpy;

import java.util.Date;
import java.util.LinkedList;

public class DataBuffer {

    private static int MAXDURACY=4000;
    private static int MAXDURACYINBUFFER=1000;
    private static double MINIMALSHAKE = 12.2;
    private static int MAXSPEED=50;
    private static int MINSPEED=5;

    GPS gps;
    public LinkedList<Double> time;
    private LinkedList<Double> x;
    private LinkedList<Double> y;
    private LinkedList<Double> z;
    public Double speed;
    private boolean isAnObstacle;
    double timeBeginShake;
    double lastUpdate;


    public DataBuffer(GPS g)
    {
        gps = g;
        lastUpdate=new  Date().getTime();
        this.time = new LinkedList();
        this.x = new LinkedList();
        this.y = new LinkedList();
        this.z = new LinkedList();
        this.speed = 0.;
        this.isAnObstacle = false;

    }

    public void add(double t, double x, double y, double z) {


        if (3.6*gps.getSpeed()>40){
            MINIMALSHAKE = 0.1*gps.getSpeed()+8.0;
        }


        if (z > MINIMALSHAKE || z<-MINIMALSHAKE && !this.isAnObstacle){
            timeBeginShake=t;
            this.isAnObstacle = true;
        }

        if ((t - timeBeginShake)>MAXDURACY && this.isAnObstacle && (3.6*gps.getSpeed())<MAXSPEED && (3.6*gps.getSpeed())>MINSPEED){
            this.save();
            this.time = new LinkedList();
            this.x = new LinkedList();
            this.y = new LinkedList();
            this.z = new LinkedList();
            this.isAnObstacle = false;
        }

        else if (this.isAnObstacle == false)                                                             //si on n'est pas sur un obstacle, on ne garde pas un enregistrement plus long que MAXDURACYINBUFFER
        {
            if (!this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
                while ((this.time.getLast() - this.time.getFirst()) > MAXDURACYINBUFFER && !this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
                    this.time.remove();
                    this.x.remove();
                    this.y.remove();
                    this.z.remove();
                }
            }
            this.time.add(t);
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
        }

        else if (this.isAnObstacle == true) {
            this.time.add(t);
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
        }


        /*if (this.isAnObstacle == true){

            if ((t - timeBeginShake)>MAXDURACY && (3.6*gps.getSpeed())<MAXSPEED && (3.6*gps.getSpeed())>MINSPEED){
                this.save();
                this.time = new LinkedList();
                this.x = new LinkedList();
                this.y = new LinkedList();
                this.z = new LinkedList();
                this.isAnObstacle = false;
            }
            else {
                this.time.add(t);
                this.x.add(x);
                this.y.add(y);
                this.z.add(z);
            }
        }


        else {
            if (!this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
                while ((this.time.getLast() - this.time.getFirst()) > MAXDURACYINBUFFER && !this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
                    this.time.remove();
                    this.x.remove();
                    this.y.remove();
                    this.z.remove();
                }
            }
            this.time.add(t);
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
        }*/

    }

    public void save() {
        String res = "";
        res+=gps.getSpeed()+"#"+gps.getLatitude()+"#"+gps.getLongitude()+"#";
        while (!this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
            res += this.time.remove();
            res += " " + this.x.remove();
            res += " " + this.y.remove();
            res += " " + this.z.remove();
            res += "#";
        }
        Save.saveText(res);
    }

    public LinkedList<Double> getTime()
    {return time;}

    public LinkedList<Double> getX()
    {return x;}

    public LinkedList<Double> getY()
    {return y;}

    public LinkedList<Double> getZ()
    {return z;}

    boolean isAnObstacle()
    {return isAnObstacle;}


}

/*


    public void add(double t, double x, double y, double z) {
        if(t-lastUpdate<MINDURACYBETWEENUPDATES)
            return;

        lastUpdate=new Date().getTime();
        if (z > MINIMALSHAKE || z<-MINIMALSHAKE && !this.isAnObstacle)                              //on check un début d'obstacle
        {
            timeBeginShake=t;
            this.isAnObstacle = true;
        }

        detector.update(this);

        if (t - timeBeginShake > MAXDURACYAFTERBUMP && this.isAnObstacle){
            this.save();
            this.time = new LinkedList();
            this.x = new LinkedList();
            this.y = new LinkedList();
            this.z = new LinkedList();
            this.isAnObstacle = false;
        }

        else if (this.isAnObstacle == false)                                                             //si on n'est pas sur un obstacle, on ne garde pas un enregistrement plus long que MAXDURACYINBUFFER
        {
            if (!this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
                while ((this.time.getLast() - this.time.getFirst()) > MAXDURACYINBUFFER && !this.time.isEmpty()&& !this.x.isEmpty()&& !this.y.isEmpty()&& !this.z.isEmpty()) {
                    this.time.remove();
                    this.x.remove();
                    this.y.remove();
                    this.z.remove();
                }
            }
            this.time.add(t);
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
        }

        else if (this.isAnObstacle == true) {
            this.time.add(t);
            this.x.add(x);
            this.y.add(y);
            this.z.add(z);
        }
    }

}


 */
