package application;

import java.io.File;

import application.objects.Experience;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BuilderController extends ControllerBase {

    private boolean editMode = false;

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

    @FXML
    VBox experiencesBox;

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

    @Override
    protected void setStage(Stage stage) {
        this.setDefaults();
        this.stage = stage;
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

    public void addExperience(ActionEvent e) {
        try {
            this.sceneController.openExperienceForm();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    protected void doAddExperience(Experience experience) {
        VBox expBox = new VBox();

        Label company = new Label(experience.getCompany());
        Font companyFont = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 18);
        company.setFont(companyFont);

        Label position = new Label(experience.getPosition());
        Font positionFont = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 16);
        position.setFont(positionFont);

        Label years = new Label(experience.getYears());
        Font yearsFont = Font.font("System", FontWeight.THIN, FontPosture.REGULAR, 12);
        years.setFont(yearsFont);
        
        Label description = new Label(experience.getShortDescription());
        expBox.getChildren().addAll(company, position, years, description);
        expBox.setStyle(
            "-fx-padding: 5;" +
            "-fx-border-style: solid inside;" + 
            "-fx-border-width: 1;" +
            "-fx-border-insets: 5;" +
            "-fx-border-color: black;"
        );
        experiencesBox.getChildren().add(expBox);
    }

    protected void addToList(ListView<String> list, String item) {
        list.getItems().add(item);
    }

}
