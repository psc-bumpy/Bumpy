package bumpy.ui;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class CustomAlertInfo extends Alert {

	public CustomAlertInfo(String header,String content) {
		super(AlertType.INFORMATION);
		setTitle("Bumpy");
		getDialogPane().getStylesheets().add("css2.css");
		setHeaderText(header);
		setContentText(content);
		Stage stage = (Stage) getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("IconeBump.png"));
		showAndWait();
	}
}
