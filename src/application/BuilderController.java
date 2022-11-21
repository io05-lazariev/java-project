package application;

import java.io.File;

import application.handlers.InputHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BuilderController {

    private boolean editMode = false;

    private Stage stage;

    private InputHandler inputHandler = new InputHandler();

    @FXML
    Label modeLabel;

    @FXML
    ImageView profileImage;

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    protected Scene getScene() {
        return this.stage.getScene();
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

    public void addSkill(ActionEvent e) {
        TextField skill = this.inputHandler.getInput("#addSkillInput", getScene());
        String skillText = skill.getText();
        Node listNode = this.getScene().lookup("#skillsList");
        ListView<String> skillsList = ((ListView<String>)listNode);
        skillsList.getItems().add(skillText);
    }

}
