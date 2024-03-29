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
import javafx.scene.Node;
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
    ListView<VBox> experienceList;

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
        this.addSkillInput.setText("");
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
        company.setId("companyField");
        Font companyFont = Font.font("System", FontWeight.BOLD, FontPosture.REGULAR, 18);
        company.setFont(companyFont);

        Label position = new Label(experience.getPosition());
        position.setId("positionField");
        Font positionFont = Font.font("System", FontWeight.NORMAL, FontPosture.REGULAR, 16);
        position.setFont(positionFont);

        Label years = new Label(experience.getYears());
        years.setId("yearsField");
        Font yearsFont = Font.font("System", FontWeight.THIN, FontPosture.REGULAR, 12);
        years.setFont(yearsFont);
        
        Label description = new Label(experience.getShortDescription());
        description.setId("descriptionField");
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
        //experiencePane.getChildren().add(expBox);
        //experiencesBox.getChildren().add(expBox);
        this.experienceList.getItems().add(expBox);
    }

    protected void updateExperience(Experience experience) {
        int experienceId = experience.getId();
        VBox experienceEntry = this.experienceList.getItems().get(experienceId);
        for(Node field : experienceEntry.getChildren()) {
            if (field.getClass() != Label.class) {
                continue;
            }
            Label fieldLabel = (Label) field;
            switch (field.getId()) {
                case "companyField":
                    fieldLabel.setText(experience.getCompany());
                    break;
                case "positionField":
                    fieldLabel.setText(experience.getPosition());
                    break;
                case "yearsField":
                    fieldLabel.setText(experience.getYears());
                    break;
                case "descriptionField":
                    fieldLabel.setText(experience.getShortDescription());
                    break;
            }
        }
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
            this.pdfSavedLabel.setText("* - required field, ** - at least one required!");
            return;
        }
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF (*.pdf)", "*.pdf");
        FileChooser saver = new FileChooser();
        saver.getExtensionFilters().add(extensionFilter);
        try {
            File document = saver.showSaveDialog(this.stage);
            if (document != null) {
                CV.bakeCV(human, document.getPath());
                this.pdfSavedLabel.setStyle("-fx-text-fill: green;");
                this.pdfSavedLabel.setText("CV saved successfully!");
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
            // Validation failed for one of those inputs.
            this.validationFailed(this.firstNameInput);
            this.validationFailed(this.lastNameInput);
            return false;
        }
        this.human.setFirstName(firstName);
        this.human.setLastName(lastName);
        String email = this.emailInput.getText().trim();
        String phone = this.phoneInput.getText().trim();
        if (email == "" && phone == "") {
            this.validationFailed(this.emailInput);
            this.validationFailed(this.phoneInput);
            return false;
        }
        String study = this.studyInput.getText().trim();
        this.human.setEmail(email);
        this.human.setPhone(phone);
        if (study == "") {
            study = "None";
        }
        this.human.setStudy(study);
        return this.validationPassed();
    }

    protected void validationFailed(TextField input) {
        String errorStyle = "-fx-text-box-border: #B22222; -fx-focus-color: #B22222;";
        String okStyle = "-fx-text-box-border: black;";
        if (input.getText().trim() == "") {
            input.setStyle(errorStyle);
        } else {
            input.setStyle(okStyle);
        }
    }

    protected boolean validationPassed() {
        String okStyle = "-fx-text-box-border: black;";
        this.firstNameInput.setStyle(okStyle);
        this.lastNameInput.setStyle(okStyle);
        this.emailInput.setStyle(okStyle);
        this.phoneInput.setStyle(okStyle);
        return true;
    }

}
