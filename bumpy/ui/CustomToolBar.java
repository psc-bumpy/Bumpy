package bumpy.ui;

import java.util.Optional;

import bumpy.DownloadManager;
import data.Bump;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * Cette classe met en forme la barre d'outil du programme ; celle-ci permet de quitter le programme,
 * mais aussi de paramétrer la vue (onglets affichés) ainsi que les paramètres de connexion pour l'importation de données.
 * @author Benoît Gallouëdec
 *
 */
public class CustomToolBar extends ToolBar
{
	GraphicManager manager;
	CustomToolBar(GraphicManager m)
	{
		super();
		manager=m;
		final Button quit = new Button("Quitter");
		quit.addEventHandler(MouseEvent.MOUSE_CLICKED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            manager.theStage.close();
			        }});
		final Button settings = new Button("Paramètres");
		
		settings.setOnAction(
			    new EventHandler<ActionEvent>() {
	        	@Override 
	        	public void handle(ActionEvent e) {
	        		Dialog<DownloadManager> dialog = new Dialog<>();
	        		dialog.getDialogPane().getStylesheets().add("css2.css");
		    		//dialog.getDialogPane().getStyleClass().add("myDialog");
		    		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		    		stage.getIcons().add(new Image("IconeBump.png"));
	        		dialog.setTitle("Paramètres proxy");
	        		dialog.setHeaderText("Paramètres proxy");
	        		dialog.setResizable(true);
	        		Label label1 = new Label("Nom du proxy: ");
	        		Label label2 = new Label("Port utilisé: ");
	        		TextField text1 = new TextField(manager.manager.downloadManager.getProxyHost());
	        		TextField text2 = new TextField(String.valueOf(manager.manager.downloadManager.getProxyPort()));
	        		GridPane grid = new GridPane();
	        		grid.add(label1, 1, 1);
	        		grid.add(text1, 2, 1);
	        		grid.add(label2, 1, 2);
	        		grid.add(text2, 2, 2);
	        		CheckBox cb = new CheckBox("Passer par le proxy");
	        		cb.setSelected(manager.manager.downloadManager.isProxyActive());
	        		grid.add(cb,1,3);
	        		dialog.getDialogPane().setContent(grid);
	        		ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
	        		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
	        		dialog.setResultConverter(new Callback<ButtonType,DownloadManager>() {
	        		    @Override
	        		    public DownloadManager call(ButtonType b) {
	        		    	System.out.println(text2.getText());
	        		        if (b == buttonTypeOk &&!text2.getText().equals("") && !text1.getText().equals("")) {
	        		            return new DownloadManager(cb.isSelected(), Integer.parseInt(text2.getText()),text1.getText());
	        		        }
	        		        return null;
	        		    }
	        		});
	        		Optional<DownloadManager> result = dialog.showAndWait();
	        		if (result.isPresent()) {
	        		    manager.manager.downloadManager=result.get();
	        		    result.get().connect();}
	        		}
	        	});
		
		
		final Button view = new Button("Vue");
		view.addEventHandler(MouseEvent.MOUSE_CLICKED, 
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			    		Dialog<Pair<String, String>> dialog = new Dialog<>();
			    		dialog.getDialogPane().getStylesheets().add("css2.css");
			    		//dialog.getDialogPane().getStyleClass().add("myDialog");
			    		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
			    		stage.getIcons().add(new Image("IconeBump.png"));
			    		dialog.setTitle("Choix des onglets à afficher");
			    		dialog.setHeaderText("Onglets visibles");
			    		ButtonType loginButtonType = new ButtonType("Valider", ButtonData.OK_DONE);
			    		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
			    		VBox box=new VBox();
			    		CheckBox importation=new CheckBox("Importation");
			    		importation.setSelected(manager.tabPane.importation!=null);
			    		CheckBox accel=new CheckBox("Accélérations");
			    		accel.setSelected(manager.tabPane.accelTab!=null);
			    		CheckBox map=new CheckBox("Carte");
			    		map.setSelected(manager.tabPane.carte!=null);
			    		CheckBox infos=new CheckBox("Statistiques");
			    		infos.setSelected(manager.tabPane.macroData!=null);
			    		box.getChildren().addAll(importation,accel,map,infos);
			    		dialog.getDialogPane().setContent(box);
			    		dialog.setResultConverter(dialogButton -> {
			    		    if (dialogButton == loginButtonType) {
			    		        if(importation.isSelected())
			    		        	manager.tabPane.addImportTab();
			    		        if(accel.isSelected())
			    		        	manager.tabPane.addAccelTab();
			    		        if(map.isSelected())
			    		        	manager.tabPane.addMapTab();
			    		        if(infos.isSelected())
			    		        	manager.tabPane.addMacroDataTab();
			    		    }
			    		    return null;
			    		});
			    		dialog.showAndWait();
			        }});
        getItems().setAll(quit, new Separator(),settings, new Separator(),view);
	}
}
