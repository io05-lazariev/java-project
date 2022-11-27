package application;

import java.io.File;

import javax.swing.Action;

import application.handlers.InputHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
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

    @FXML
    ListView<String> skillsList;
    
    @FXML
    ChoiceBox<String> languageLevel;

    @FXML
    ListView<String> languageList;

    private String[] languageLevels = {
        "Beginner (A1)",
        "Pre-intermediate (A2)",
        "Intermediate (B1)",
        "Upper-intermediate (B2)",
        "Advanced (C1)",
        "Native",
    };

    private void setDefaults() {
        this.languageLevel.getItems().addAll(languageLevels);
        this.languageList.setCellFactory(TextFieldListCell.forListView());
        this.skillsList.setCellFactory(TextFieldListCell.forListView());
    }

    protected void setStage(Stage stage) {
        this.setDefaults();
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
        Image image = new Image(imageFile.toURI().toString(), profileImage.getFitWidth(), profileImage.getFitHeight(), false, true);
        if (image != null) {
            profileImage.setImage(image);
        }
    }

    protected void enableEditMode() {
        this.editMode = true;
    }

    public void addSkill(ActionEvent e) {
        String skill = this.extractTextFrom("#addSkillInput");
        this.addToList(this.skillsList, skill);
    }

    public void addLangauge(ActionEvent e) {
        String language = this.extractTextFrom("#addLanguageInput");
        String languageLevel = (this.languageLevel.getValue() != null) ? this.languageLevel.getValue() : "";
        language = language + " - " + languageLevel;
        this.addToList(this.languageList, language);
    }

    private String extractTextFrom(String inputId) {
        TextField input = this.inputHandler.getInput(inputId, getScene());
        String text = input.getText();
        input.setText("");
        return text;
    }

    protected void addToList(ListView<String> list, String item) {
        list.getItems().add(item);
    }

}
