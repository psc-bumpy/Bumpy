package bumpy.ui;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Bouton extends ImageView{
	Bouton(Group group,String text,int i)
	{
		super(new Image("Ressources\\btn_frontend.png"));
		setViewport(new Rectangle2D(0,0,getImage().getWidth()/4,getImage().getHeight()));
        group.getChildren().add(this);
        setTranslateX(group.getBoundsInLocal().getWidth()/2-getImage().getWidth()/8);
        setTranslateY(group.getBoundsInLocal().getHeight()/2-getImage().getHeight()/8+20+i*50);
        
        Text t=new Text(text);
        t.setFont(Font.font ("Garamond", 18));
        t.setFill(Color.WHITE);
        t.setDisable(true);
        group.getChildren().add(t);
        t.setTranslateX(this.getBoundsInParent().getMinX()+getImage().getWidth()/8-t.getBoundsInLocal().getWidth()/2);
        t.setTranslateY(this.getBoundsInParent().getMinY()+getImage().getHeight()-t.getBoundsInLocal().getHeight()/2);
        
        setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				setViewport(new Rectangle2D(getImage().getWidth()/4,0,getImage().getWidth()/4,getImage().getHeight()));
			};});
        setOnMouseDragged(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				setViewport(new Rectangle2D(getImage().getWidth()/4,0,getImage().getWidth()/4,getImage().getHeight()));
			};});
        setOnMouseExited(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				setViewport(new Rectangle2D(0,0,getImage().getWidth()/4,getImage().getHeight()));
			};});
        setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				setViewport(new Rectangle2D(getImage().getWidth()/4*3,0,getImage().getWidth()/4,getImage().getHeight()));
				/*if(g.gameEngine.connection!=null)
					g.gameEngine.connection.disconnect();*/
    			Platform.exit();
    			System.exit(0);
			};});
	}
}
