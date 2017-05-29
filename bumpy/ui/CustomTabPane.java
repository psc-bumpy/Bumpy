package bumpy.ui;

import bumpy.DownloadManager.Request;
import data.Bump;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import BdD.GPS_position;
import BdD.Measure;
import javafx.scene.paint.Color;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 * Cette classe met en forme les onglets du programme gérant l'importation, l'affichage des accélérations, la carte,
 * et (à implémenter) les informations générales sur les données enregistrées.
 * @author Benoît Gallouëdec
 *
 */
public class CustomTabPane extends TabPane
{
	Tab importation;
	Tab accelTab;
	Tab carte;
	Tab macroData;
	Tab distribution;
	GraphicManager manager;
	
	CustomTabPane(GraphicManager m){
		super();
		manager=m;
		addMacroDataTab();
        addImportTab();
        addAccelTab();
        addMapTab();
        addDistributionTab();
	}
	
	/**
	 * Création de l'onglet d'importation
	 */
	void addImportTab(){
		if(importation!=null)
			return;
		importation = new Tab("Importer des données");
        importation.setOnClosed(new EventHandler<Event>(){
        	@Override public void handle(Event e){importation=null;}});
        BorderPane pane=new BorderPane();
		VBox importationGroup=new VBox();
		Button importButton = new Button("Importer nouvelles données");
		Button importAllButton = new Button("Importer toutes les données");
		Button stateButton = new Button("Etat de la base de données distante");
		importAllButton.setOnMouseClicked(new EventHandler<Event>(){
			@Override public void handle(Event e){
				LinkedList<String> data;
				String info="";
				try{data=manager.manager.downloadManager.download(Request.GETALL);}
				catch(IOException f){data=null;}
		/*for(String s:data){
			Measure m=Measure.Recuperer(s);
			m.save();
		}*/
		if(data==null)
			info="Téléchargement impossible";
		else if(data.size()>1)
			info=data.size()+" fichiers téléchargés";
		else if(data.size()==1)
			info="1 fichier téléchargé";
		else
			info="Aucun fichier téléchargé";
		CustomAlertInfo alert=new CustomAlertInfo("Fin du téléchargement",info);}});
        importationGroup.getChildren().add(importButton);
        
        stateButton.setOnMouseClicked(new EventHandler<Event>(){
		@Override public void handle(Event e){
			String info;
			LinkedList<String> data;
			try{
				data=manager.manager.downloadManager.download(Request.GETSTATE);
				info="Nombre total de mesures : "+data.getFirst()+System.lineSeparator()+"Nombre d'obstacles : "+data.get(1);
			}
			catch(IOException f){data=null;info="Téléchargement impossible";}
			CustomAlertInfo alert=new CustomAlertInfo("Fin du téléchargement",info);
	}});
        
        importationGroup.getChildren().add(importAllButton);
        
        importButton.setOnMouseClicked(new EventHandler<Event>(){
			@Override public void handle(Event e){
				LinkedList<String> data;
				String info;
				try{data=manager.manager.downloadManager.download(Request.GETUNTREATED);}
				catch(IOException f){data=null;}
		/*for(String s:data){
			Measure m=Measure.Recuperer(s);
			m.save();
		}*/
		
		if(data==null)
			info="Téléchargement impossible";
		else if(data.size()>1)
			info=data.size()+" fichiers téléchargés";
		else if(data.size()==1)
			info="1 fichier téléchargé";
		else
			info="Aucun fichier téléchargé";
		CustomAlertInfo alert=new CustomAlertInfo("Fin du téléchargement",info);}});
        importationGroup.getChildren().add(stateButton);
        importationGroup.setAlignment(Pos.CENTER);
        pane.setCenter(importationGroup);
	    importation.setContent(pane);
	    getTabs().add(importation);
	}
	
	/**
	 * Création de l'onglet accélérations
	 */
	void addAccelTab(){
		if(accelTab!=null) return;
		accelTab = new Tab("Accélérations");
		accelTab.setOnClosed(new EventHandler<Event>(){
        	@Override public void handle(Event e){accelTab=null;}});
		refreshAccelTab(null);
		getTabs().add(accelTab);
	}
	 void refreshAccelTab(GPS_position gps){
		 if(accelTab==null) return;
			BorderPane chart=new BorderPane();
			if(manager.manager.bumps==null||manager.manager.bumps.size()==0)
			{
				Text text=new Text("Aucune donnée à afficher");
				text.setFont(new Font("Calibri",48));
				text.setFill(Color.WHITE);
				chart.setCenter(text);
				accelTab.setContent(chart);
				return;
			}
			LinkedList<String> locations=new LinkedList<String>();
			for(Bump b:manager.manager.bumps.values()){
				if(b.getLocation()!=null)
					locations.add(b.getLocation());
				else
					locations.add(String.valueOf(b.getLatitude()));
			}
	        accelTab.setContent(chart);
	        BorderPane pane=new BorderPane();
	        Chart chartView=new Chart("Données de l'enregistrement","Temps (s)","Accélération (m/s²)",loadChart(gps),locations);
	        pane.setCenter(chartView);
	        final ComboBox<String> location = new ComboBox<String>();
	        location.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	            public void changed(ObservableValue<? extends String> ov,
	               final String oldvalue, final String newvalue)
	                {
	            	for(Bump b:manager.manager.bumps.values()){
	    				if(b.getLocation().equals(newvalue))
	    					refreshAccelTab(new GPS_position(b.getLongitude(),b.getLatitude()));
	    			}
	                }});
	        location.getItems().addAll(locations);
	        location.setValue("Emplacement du dos-d'âne");
	       
	        HBox hbox=new HBox();
	        hbox.getChildren().addAll(location);
	        VBox box=new VBox();
	        box.getChildren().addAll(hbox,new Separator());
	        chart.setTop(box);
	        chart.setCenter(chartView);
	        chart.setBottom(new Separator());
	        accelTab.setContent(chart);
	 }
	/**
	 * Création de l'onglet carte
	 */
	void addMapTab()
	{
		BorderPane chart=new BorderPane();
		if(carte!=null)
			return;
		carte = new Tab("Carte des mesures");
	    carte.setOnClosed(new EventHandler<Event>(){
	    	@Override public void handle(Event e){carte=null;}});
		getTabs().add(carte);
		if(!Map.netIsAvailable()){
			Text text=new Text("Connexion internet nécessaire");
			text.setFont(new Font("Calibri",48));
			text.setFill(Color.WHITE);
			chart.setCenter(text);
			carte.setContent(chart);
			return;
		}
		if(manager.manager.getMap().initialized!=true){
	        VBox box=new VBox();
	        ProgressIndicator progress=new ProgressIndicator();
	        progress.setPrefSize(100, 100);
	        Text text=new Text("   Chargement...");
			text.setFont(new Font("Calibri",48));
			text.setFill(Color.WHITE);
			box.getChildren().addAll(progress,text);
			box.setAlignment(Pos.CENTER);
			carte.setContent(box);
	        return;
		}
		carte.setContent(manager.manager.getMap().mapView);
	}
	
	/**
	 * Création de l'onglet informations générales
	 */
	void addMacroDataTab(){
			if(macroData!=null) return;
			macroData = new Tab("Macrodonnées");
			macroData.setOnClosed(new EventHandler<Event>(){
	        	@Override public void handle(Event e){macroData=null;}});
			refreshMacroDataTab(null);
			getTabs().add(macroData);
		}
	
	void refreshMacroDataTab(String s)
	{
		BorderPane pane=new BorderPane();
		LinkedList<String> dataOfInterest=new LinkedList<String>();
		dataOfInterest.add("Etat général");
		dataOfInterest.add("Profil des vitesses");
        final ComboBox<String> location = new ComboBox<String>();
        location.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
               final String oldvalue, final String newvalue)
                {
            	for(Bump b:manager.manager.bumps.values()){
    				if(b.getLocation().equals(newvalue))
    					refreshAccelTab(new GPS_position(b.getLongitude(),b.getLatitude()));
    			}
                }});
        location.getItems().addAll(dataOfInterest);
        location.setValue("Données à afficher");
        HBox hbox=new HBox();
        hbox.getChildren().addAll(location);
        VBox box=new VBox();
        box.getChildren().addAll(hbox,new Separator());
        pane.setTop(box);
        pane.setBottom(new Separator());
        if(s!=null && s.equals("Profil des vitesses"))
        	;
        else pane.setCenter(mainPieChart());
        macroData.setContent(pane);
	}
	
	/**
	 * Teste les accélérations (à supprimer)
	 * @return
	 */
	LinkedList<LinkedList<Point>> loadChart(GPS_position gps)
	{
		Measure m=Measure.Une_mesure(gps);
		double[][] data=m.accel_et_temps();
		LinkedList<LinkedList<Point>> p=new LinkedList<LinkedList<Point>>();
		LinkedList<Point> a=new LinkedList<Point>();
		for(int i=0;i<data.length;i++){
			Point pt=new Point(data[i][0],data[i][3]);
			a.add(pt);
		}
		p.add(a);
		return p;
	}
	/*
	void addSpeedTab()
	{
		if(speedTab!=null) return;
		speedTab = new Tab("Vitesses");
		speedTab.setOnClosed(new EventHandler<Event>(){
        	@Override public void handle(Event e){speedTab=null;}});
		refreshSpeedTab(null);
		getTabs().add(speedTab);
	}
	 void refreshSpeedTab(GPS_position gps){
		 if(accelTab==null) return;
			BorderPane chart=new BorderPane();
			if(manager.manager.bumps==null||manager.manager.bumps.size()==0)
			{
				Text text=new Text("Aucune donnée à afficher");
				text.setFont(new Font("Calibri",48));
				text.setFill(Color.WHITE);
				chart.setCenter(text);
				accelTab.setContent(chart);
				return;
			}
			LinkedList<String> locations=new LinkedList<String>();
			for(Bump b:manager.manager.bumps.values()){
				if(b.getLocation()!=null)
					locations.add(b.getLocation());
				else
					locations.add(String.valueOf(b.getLatitude()));
			}
	        accelTab.setContent(chart);
	        BorderPane pane=new BorderPane();
	        Chart chartView=new Chart("Données de l'enregistrement","Temps (s)","Accélération (m/s²)",loadChart(gps),locations);
	        pane.setCenter(chartView);
	        final ComboBox<String> location = new ComboBox<String>();
	        location.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
	            public void changed(ObservableValue<? extends String> ov,
	               final String oldvalue, final String newvalue)
	                {
	            	for(Bump b:manager.manager.bumps.values()){
	    				if(b.getLocation().equals(newvalue))
	    					refreshAccelTab(new GPS_position(b.getLongitude(),b.getLatitude()));
	    			}
	                }});
	        location.getItems().addAll(locations);
	        location.setValue("Emplacement du dos-d'âne");
	       
	        HBox hbox=new HBox();
	        hbox.getChildren().addAll(location);
	        VBox box=new VBox();
	        box.getChildren().addAll(hbox,new Separator());
	        chart.setTop(box);
	        chart.setCenter(chartView);
	        chart.setBottom(new Separator());
	        accelTab.setContent(chart);
	 }*/
	
	VBox mainPieChart(){
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        /*if(Bump.stats!=null){
        for(Pair<String,Integer> p:Bump.stats){
        	pieChartData.add(new PieChart.Data(p.getKey(), p.getValue()/Bump.getSum()));
        }
        }*/
		pieChartData.add(new PieChart.Data("tet",10));
        final PieChart chart = new PieChart(pieChartData);
        chart.setMaxSize(500, 500);
        chart.setTitle("Types d'obstacle majoritairement utilisés");
        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(chart);
        return vbox;
	}
	
	VBox speeds(){
		ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList();
        /*if(Bump.stats!=null){
        for(Pair<String,Integer> p:Bump.stats){
        	pieChartData.add(new PieChart.Data(p.getKey(), p.getValue()/Bump.getSum()));
        }
        }*/
		pieChartData.add(new PieChart.Data("tet",10));
        final PieChart chart = new PieChart(pieChartData);
        chart.setMaxSize(500, 500);
        chart.setTitle("Types d'obstacle majoritairement utilisés");
        VBox vbox=new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().add(chart);
        return vbox;
	}
	
	void refreshDistributionTab(){
		BorderPane pane=new BorderPane();
		LinkedList<String> dataOfInterest=new LinkedList<String>();
		dataOfInterest.add("Etat général");
		dataOfInterest.add("Profil des vitesses");
        final ComboBox<String> location = new ComboBox<String>();
        location.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> ov,
               final String oldvalue, final String newvalue)
                {
            	for(Bump b:manager.manager.bumps.values()){
    				if(b.getLocation().equals(newvalue))
    					refreshDistributionTab();
    			}
                }});
        location.getItems().addAll(dataOfInterest);
        location.setValue("Données à afficher");
        HBox hbox=new HBox();
        hbox.getChildren().addAll(location);
        VBox box=new VBox();
        box.getChildren().addAll(hbox,new Separator());
        pane.setTop(box);
        pane.setBottom(new Separator());
        LinkedList<Double> data=new LinkedList<Double>();
        for(int i=0;i<40;i++)
        	data.add(Math.random()*50);
        pane.setCenter(histogramme(data,"Vitesse","Nb de passages","Distribution des vitesses"));
        
        distribution.setContent(pane);
	}
	
	BarChart histogramme(LinkedList<Double> datas,String xLabel, String yLabel,String title){
		double min=datas.getFirst();
		double max=min;
		for(Double d:datas){
			if(d<min)min=d;
			if(d>max)max=d;
			min=Math.floor(min);
			max=Math.ceil(max);
		}
		double delta=Math.ceil((max-min)/10);
		int[] distrib=new int[10];
		for(Double d:datas){
			distrib[(int)((d-min)/delta)]++;
		}
	 
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        final BarChart<String,Number> chart = 
	            new BarChart<String,Number>(xAxis,yAxis);
	        chart.setTitle(title);
	       // xAxis.setLabel(xLabel);       
	        yAxis.setLabel(yLabel);
	 
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName(xLabel);    
	        for(int i=0;i<distrib.length;i++)
	        	series1.getData().add(new XYChart.Data(String.valueOf((int)(min+i*delta)),Integer.valueOf(distrib[i])));
	        
	        chart.getData().addAll(series1);
        return chart;
	}
	void addDistributionTab(){
		if(distribution!=null) return;
		distribution = new Tab("Distribution des vitesses");
		distribution.setOnClosed(new EventHandler<Event>(){
        	@Override public void handle(Event e){distribution=null;}});
		refreshDistributionTab();
		getTabs().add(distribution);
	}
	
}
