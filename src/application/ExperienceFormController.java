package application;

import java.time.Year;

import application.objects.Experience;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class ExperienceFormController extends ControllerBase {
    
    private Experience experience;

    protected BuilderController parentController;

    protected boolean editMode = false;

    @FXML
    ChoiceBox<Integer> yearsFromCB;

    @FXML
    ChoiceBox<Integer> yearsToCB;

    @FXML
    CheckBox finishedCheckBox;

    @FXML
    TextField companyField;

    @FXML
    TextField positionField;

    @FXML
    TextArea descriptionField;

    protected void setParentController(BuilderController parent) {
        this.parentController = parent;
    }

    protected BuilderController getParentController() {
        return this.parentController;
    }

    protected void fillYears() {
        int yearsGap = 70;
        int currentYear = Year.now().getValue();
        int year = currentYear-yearsGap;
        Integer[] years = new Integer[yearsGap];
        for (int i = 0; year != currentYear; i++) {
            years[i] = year;
            year++;
        }
        yearsFromCB.getItems().addAll(years);
        yearsToCB.getItems().addAll(years);
        yearsFromCB.setValue(currentYear-yearsGap);
        yearsToCB.setValue(currentYear);
        yearsToCB.setDisable(true);
    }

    public void saveExperience() {
        int yearStarted = this.inputHandler.getChoice(yearsFromCB);
        int yearFinished = this.inputHandler.getChoice(yearsToCB);
        if (yearFinished < yearStarted) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setContentText("Year finished cannot be less than year started!");
            errorAlert.showAndWait();
            return;
        }
        String company = this.companyField.getText();
        String position = this.positionField.getText();
        String description = this.descriptionField.getText();
        if (finishedCheckBox.isSelected()) {
            if (editMode) {
                this.experience.updateFields(company, position, yearStarted, yearFinished, description);
            } else {
                this.experience = new Experience(company, position, yearStarted, yearFinished, description);
            }
        }
        else {
            if (editMode) {
                this.experience.updateFields(company, position, yearStarted, description);
            } else {
                this.experience = new Experience(company, position, yearStarted, description);
            }
        }
        if (editMode) {
            this.parentController.updateExperience(this.experience);
        } else {
            this.parentController.doAddExperience(this.experience);
        }
        this.clearInputs();
    }

    private void clearInputs() {
        this.companyField.setText("");
        this.positionField.setText("");
        this.descriptionField.setText("");
    }

    public void cancel() {
        this.getStage().close();
    }

    public void checkFinished(ActionEvent e) {
        boolean finished = finishedCheckBox.isSelected();
        yearsToCB.setDisable(!finished);
    }

    public void editExperience(Experience experience) {
        this.editMode = true;
        this.experience = experience;
        this.companyField.setText(experience.getCompany());
        this.positionField.setText(experience.getPosition());
        this.descriptionField.setText(experience.getShortDescription());
        this.yearsFromCB.setValue(experience.getStartedYear());
        this.finishedCheckBox.setSelected(experience.isFinished());
        if (experience.isFinished()) {
            this.yearsToCB.setValue(experience.getFinishedYear());
            this.yearsToCB.setDisable(false);
        }
        else {
            this.yearsToCB.setValue(Year.now().getValue());
            this.yearsToCB.setDisable(true);
        }
    }

}
