package bumpy.ui;

import java.lang.reflect.Field;
import java.util.LinkedList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class Chart extends LineChart<Number,Number>
{
	Chart(String title, String xLabel,String yLabel,LinkedList<LinkedList<Point>> datas,LinkedList<String> name)
	{
		super(new NumberAxis(),new NumberAxis());
		getXAxis().setLabel(xLabel);
		getYAxis().setLabel(yLabel);

        //xAxis.setTickLabelFill(Color.ORANGE);
        //yAxis.setTickLabelFill(Color.ORANGE);
         
        setTitle(title);
        setCreateSymbols(false);
        for(LinkedList<Point> l:datas)
        {
        	XYChart.Series<Number,Number> serie = new XYChart.Series<Number,Number>();
        	serie.setName("test"/*name.pop()*/);
        	
        	for(Point p:l)
        	{
        		serie.getData().add(new XYChart.Data<Number,Number>(p.getX(),p.getY()));
        	}
        	getData().add(serie);
        }
        /*for (XYChart.Series<Number, Number> s : getData()) {
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
