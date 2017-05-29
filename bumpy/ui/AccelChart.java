package bumpy.ui;

import java.lang.reflect.Field;
import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class AccelChart
{
	AccelChart(Group root, String title, String xLabel,LinkedList<LinkedList<Point>> datas,LinkedList<String> name)
	{
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel(xLabel);
        //xAxis.setTickLabelFill(Color.ORANGE);
        //yAxis.setTickLabelFill(Color.ORANGE);
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
        root.getChildren().add(lineChart);
         
        lineChart.setTitle(title);
        for(LinkedList<Point> l:datas)
        {
        	XYChart.Series serie = new XYChart.Series();
        	serie.setName(name.pop());
        	for(Point p:l)
        	{
        		serie.getData().add(new XYChart.Data(p.getX(),p.getY()));
        	}
        	lineChart.getData().add(serie);
        }
        /*for (XYChart.Series<Number, Number> s : lineChart.getData()) {
            for (XYChart.Data<Number, Number> d : s.getData()) {
            	Tooltip t=new Tooltip(d.getXValue().toString() + "\n"  + d.getYValue());
            	hackTooltipStartTiming(t);
                Tooltip.install(d.getNode(),t);

                //Adding class on hover
                d.getNode().setOnMouseEntered(event -> d.getNode().getStyleClass().add("onHover"));

                //Removing class on exit
                d.getNode().setOnMouseExited(event -> d.getNode().getStyleClass().remove("onHover"));
            }
        }*/
        
	}
	public static void hackTooltipStartTiming(Tooltip tooltip) {
	    try {
	        Field fieldBehavior = tooltip.getClass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(tooltip);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(250)));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
