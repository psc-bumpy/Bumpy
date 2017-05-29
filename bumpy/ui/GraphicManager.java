package bumpy.ui;

import bumpy.Manager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GraphicManager {
	Scene theScene;
	Stage theStage;
	BorderPane root;
	CustomToolBar toolBar;
	public CustomTabPane tabPane;
	Manager manager;
	
	public GraphicManager(Stage s,Manager m)
	{
		theStage=s;
		manager=m;
		root = new BorderPane();
        theStage.getIcons().add(new Image("IconeBump.png"));
		theStage.setTitle( "Bumpy" );
		theScene = new Scene(root);
	    theScene.getStylesheets().add("css2.css");
	    theStage.setScene( theScene );
	    loading();
	    theStage.setMaximized(true);
	    theStage.show();
	}
	
	public void loading(){
		toolBar = new CustomToolBar(this); 
        root.setTop(toolBar);
		tabPane=new CustomTabPane(this);
		root.setTop(toolBar);
        root.setCenter(tabPane);
        theStage.setScene(theScene);
	    theStage.getScene().heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
              tabPane.setPrefHeight(newValue.doubleValue());
            }
          });
	    theStage.getScene().widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
              tabPane.setPrefWidth(newValue.doubleValue());
            }
          });}
}



