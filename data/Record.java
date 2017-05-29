package data;

import java.util.LinkedList;
import java.util.Vector;

import bumpy.ui.Point;

public class Record {
	private Vector<Double> t;
	private Vector<Double> x;
	private Vector<Double> y;
	private Vector<Double> z;
	
	public LinkedList<Point> print(int coord){
		LinkedList<Point> graph= new LinkedList<Point>();
		for(int i=0;i<t.size();i++){
			if(coord==0)
				graph.add(new Point(t.get(i),x.get(i)));
			else if(coord==1)
				graph.add(new Point(t.get(i),y.get(i)));
			else
				graph.add(new Point(t.get(i),z.get(i)));
		}
		return graph;
	}
}
