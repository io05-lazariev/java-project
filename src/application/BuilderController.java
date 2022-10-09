package application;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BuilderController {

    private boolean editMode = false;

    private Stage stage;

    @FXML
    Label modeLabel;

    @FXML
    ImageView profileImage;

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    public void uploadImage(MouseEvent e) {
        FileChooser.ExtensionFilter allowedExtensions = new FileChooser.ExtensionFilter("Images (*.jpg, *.png)", "*.jpg", "*.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(allowedExtensions);
        fileChooser.setTitle("Select profile picture");
        File imageFile = fileChooser.showOpenDialog(this.stage);
        Image image = new Image(imageFile.toURI().toString());
        if (image != null) {
            profileImage.setImage(image);
        }
    }

    protected void enableEditMode() {
        this.editMode = true;
    }

    protected void displayMode() {
        String modeString = editMode ? "Edit mode" : "Create mode";
        this.modeLabel.setText(modeString);
    }

}
