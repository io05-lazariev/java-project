package application;

import java.io.File;
import java.nio.file.Path;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import application.objects.Experience;
import application.objects.Human;
import application.objects.Language;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BuilderController extends ControllerBase {

    @FXML
    ImageView profileImage;

    @FXML
    TextField addSkillInput;

    @FXML
    ListView<String> skillsList;

    @FXML
    TextField addLanguageInput;
    
    @FXML
    ChoiceBox<String> languageLevel;

    @FXML
    ListView<String> languageList;

    @FXML
    VBox experiencesBox;

    @FXML
    AnchorPane experiencePane;

    @FXML
    Label pdfSavedLabel;

    private String[] languageLevels = {
        "Beginner (A1)",
        "Pre-intermediate (A2)",
        "Intermediate (B1)",
        "Upper-intermediate (B2)",
        "Advanced (C1)",
        "Native",
    };

    protected Human human = new Human();

    private void setDefaults() {
        this.languageLevel.getItems().addAll(this.languageLevels);
        this.languageLevel.setValue(this.languageLevels[0]);
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
        this.human.setProfileImage(imageFile);
        Image image = new Image(imageFile.toURI().toString(), profileImage.getFitWidth(), profileImage.getFitHeight(), false, true);
        if (image != null) {
            profileImage.setImage(image);
        }
    }

    public void addSkill(ActionEvent e) {
        String skill = this.addSkillInput.getText();
        this.human.addSkill(skill);
        this.addToList(this.skillsList, skill);
    }

    public void addLangauge(ActionEvent e) {
        String lang = this.addLanguageInput.getText();
        String languageLevel = (this.languageLevel.getValue() != null) ? this.languageLevel.getValue() : "";
        Language language = new Language(lang, languageLevel);
        this.human.addLangauge(language);
        this.addToList(this.languageList, language.getLanguageLevel());
    }

    public void startLanguageEdit(ActionEvent e) {
        this.languageList.setDisable(true);
    }

    public void updateLanguage(ActionEvent e) {
        String lang = this.addLanguageInput.getText();
        String languageLevel = this.languageLevel.getValue();
    }

    public void addExperience(ActionEvent e) {
        try {
            this.sceneController.openExperienceForm();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    
    protected void doAddExperience(Experience experience) {
        this.human.addExperience(experience);
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
        expBox.setOnMouseClicked((e) -> {
            this.editExperience(experience);
        });
        experiencesBox.getChildren().add(expBox);
    }

    protected void editExperience(Experience experience) {
        try {
            this.sceneController.openExperienceForm(experience);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    protected void addToList(ListView<String> list, String item) {
        list.getItems().add(item);
    }

    public void saveCV() {
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(extensionFilter);
        try {
            File document = saver.showSaveDialog(this.stage);
            if (document != null) {
                Path documentPath = document.toPath();
                String savePath = documentPath.toString();
                PdfWriter writer = new PdfWriter(document);
                PdfDocument cv = new PdfDocument(writer);
                cv.addNewPage();
                Document cvDoc = new Document(cv);
                this.pdfSavedLabel.setText("CV saved successfully!");
                cvDoc.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
