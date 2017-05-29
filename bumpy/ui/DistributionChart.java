package bumpy.ui;

import java.util.Comparator;
import java.util.LinkedList;

import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.util.Pair;

public class DistributionChart extends BarChart<Number,Number>{

	public DistributionChart(NumberAxis xAxis, NumberAxis yAxis) {
		super(xAxis, yAxis);
	}
	public DistributionChart(LinkedList<Double> data,double delta){
		this(new NumberAxis(),new NumberAxis());
		class DoubleComparator<Double> implements Comparator<Double> {
		    public int compare(Double a, Double b){
		        if (a instanceof Comparable) 
		            if (a.getClass().equals(b.getClass()))
		                return ((Comparable<Double>)a).compareTo(b);        
		        throw new UnsupportedOperationException();
		    }
		}
		data.sort(new DoubleComparator<Double>());
		double delta=(data.getLast()-data.getFirst())/categories;
		LinkedList<Pair<Double,Double>> categ;
		final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> barChart = 
            new BarChart<>(xAxis,yAxis);
        barChart.setCategoryGap(0);
        barChart.setBarGap(0);
         
        xAxis.setLabel("Range");       
        yAxis.setLabel("Population");
         
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Histogram");       
        series1.getData().add(new XYChart.Data("0-10", group[0]));
        series1.getData().add(new XYChart.Data("10-20", group[1]));
        series1.getData().add(new XYChart.Data("20-30", group[2]));
        series1.getData().add(new XYChart.Data("30-40", group[3]));
        series1.getData().add(new XYChart.Data("40-50", group[4])); 
         
        series1.getData().add(new XYChart.Data("50-60", group[5]));
        series1.getData().add(new XYChart.Data("60-70", group[6]));
        series1.getData().add(new XYChart.Data("70-80", group[7]));
        series1.getData().add(new XYChart.Data("80-90", group[8]));
        series1.getData().add(new XYChart.Data("90-100", group[9]));
         
        barChart.getData().addAll(series1);
       
     
    //count data population in groups
    private void groupData(){
        for(int i=0; i<10; i++){
            group[i]=0;
        }
        for(int i=0; i<DATA_SIZE; i++){
            if(data[i]<=10){
                group[0]++;
            }else if(data[i]<=20){
                group[1]++;
            }else if(data[i]<=30){
                group[2]++;
            }else if(data[i]<=40){
                group[3]++;
            }else if(data[i]<=50){
                group[4]++;
            }else if(data[i]<=60){
                group[5]++;
            }else if(data[i]<=70){
                group[6]++;
            }else if(data[i]<=80){
                group[7]++;
            }else if(data[i]<=90){
                group[8]++;
            }else if(data[i]<=100){
                group[9]++;
            }
        }
    }
	}
}
