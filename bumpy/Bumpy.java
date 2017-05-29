package bumpy;

import bumpy.Manager;
import javafx.application.Application;
import javafx.stage.Stage;


public class Bumpy extends Application{
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage theStage) {
	    new Manager(theStage);
	}
}
