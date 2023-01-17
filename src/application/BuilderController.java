package application;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import application.objects.CV;
import application.objects.Experience;
import application.objects.Human;
import application.objects.Language;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    TextField firstNameInput;
    
    @FXML
    TextField lastNameInput;
    
    @FXML
    TextField emailInput;
    
    @FXML
    TextField phoneInput;

    @FXML
    TextField studyInput;

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
    Button addLanguage;

    ArrayList<Language> languagesList = new ArrayList<Language>();

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
        if (imageFile == null) {
            return;
        }
        this.human.setProfileImage(imageFile);
        Image image = new Image(imageFile.toURI().toString(), this.profileImage.getFitWidth(), this.profileImage.getFitHeight(), false, true);
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
        this.languagesList.add(language);
        String languageString = lang + " - " + languageLevel;
        this.addToList(this.languageList, languageString);
        this.addLanguageInput.setText("");
    }

    public void startLanguageEdit(Event e) {
        if (e.getSource().getClass() != this.languageList.getClass()) {
            return;
        }
        this.doLanguageEdit((ListView.EditEvent<String>) e);
    }

    protected void doLanguageEdit(ListView.EditEvent<String> entry) {
        this.languageList.setDisable(true);
        int languageIndex = entry.getIndex();
        this.addLanguage.setText("OK");
        this.addLanguage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updateLanguage(languageIndex);
            }
        });
        Language languageEntry = languagesList.get(languageIndex);
        this.addLanguageInput.setText(languageEntry.getLanguage());
        List<String> languageLevelsList = Arrays.asList(this.languageLevels);
        int langLevelIndex = languageLevelsList.indexOf(languageEntry.getLevel());
        this.languageLevel.getSelectionModel().select(langLevelIndex);
    }

    public void updateLanguage(int langIndex) {
        Language editedLanguage = this.human.getLanguageByIndex(langIndex);
        String lang = this.addLanguageInput.getText();
        String languageLevel = this.languageLevel.getValue();
        editedLanguage.setLanguage(lang);
        editedLanguage.setLevel(languageLevel);
        this.human.setLanguage(editedLanguage, langIndex);
        this.languagesList.set(langIndex, editedLanguage);
        this.languageList.setDisable(false);
        this.languageList.getItems().set(langIndex, editedLanguage.getLanguageLevel());
        this.addLanguageInput.setText("");
        this.setAddLanguageButtonDefault();
    }

    protected void setAddLanguageButtonDefault() {
        this.addLanguage.setText("+");
        this.addLanguage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addLangauge(event);
            }
        });
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
        if (!this.validateInputs()) {
            this.pdfSavedLabel.setStyle("-fx-text-fill: red;");
            this.pdfSavedLabel.setText("First name AND Last name must be set!");
            return;
        }
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(extensionFilter);
        try {
            File document = saver.showSaveDialog(this.stage);
            if (document != null) {
                CV.bakeCV(human, document.getPath());
                /*PdfWriter writer = new PdfWriter(document.getPath());
                PdfDocument cvPdf = new PdfDocument(writer);
                Document pdfDoc = new Document(cvPdf);
                CV.makeCV(pdfDoc, human);
                pdfDoc.close();
                this.pdfSavedLabel.setStyle("-fx-text-fill: green;");
                this.pdfSavedLabel.setText("CV saved successfully!");*/
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean validateInputs() {
        String firstName = this.firstNameInput.getText().trim();
        String lastName = this.lastNameInput.getText().trim();
        if (firstName == "" || lastName == "") {
            String errorStyle = "-fx-text-box-border: #B22222; -fx-focus-color: #B22222;";
            String okStyle = "-fx-text-box-border: black;";
            if (firstName == "") {
                this.firstNameInput.setStyle(errorStyle);
            } else {
                this.firstNameInput.setStyle(okStyle);
            }
            if (lastName == "") {
                this.lastNameInput.setStyle(errorStyle);
            } else {
                this.lastNameInput.setStyle(okStyle);
            }
            return false;
        }
        this.human.setFirstName(firstName);
        this.human.setLastName(lastName);
        String email = this.emailInput.getText();
        String phone = this.phoneInput.getText();
        String study = this.studyInput.getText();
        this.human.setEmail(email);
        this.human.setPhone(phone);
        this.human.setStudy(study);
        return true;
    }

}
