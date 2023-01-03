package application;

import java.time.Year;

import application.objects.Experience;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

public class ExperienceFormController extends ControllerBase {
    
    private Experience experience;

    protected BuilderController parentController;

    @FXML
    ChoiceBox<Integer> yearsFromCB;

    @FXML
    ChoiceBox<Integer> yearsToCB;

    @FXML
    CheckBox finishedCheckBox;

    protected void setParentController(BuilderController parent) {
        this.parentController = parent;
    }

    protected BuilderController getParentController() {
        return this.parentController;
    }

    protected void fillYears() {
        int yearsGap = 100;
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
        String company = this.extractTextFrom("#companyField");
        String position = this.extractTextFrom("#positionField");
        String description = this.extractTextFrom("#descriptionField");
        if (finishedCheckBox.isSelected()) {
            this.experience = new Experience(company, position, yearStarted, yearFinished, description);
        }
        else {
            this.experience = new Experience(company, position, yearStarted, description);
        }
        this.parentController.doAddExperience(experience);
    }

    public void cancel() {
        this.getStage().close();
    }

    public void checkFinished(ActionEvent e) {
        boolean finished = finishedCheckBox.isSelected();
        yearsToCB.setDisable(!finished);
    }

}
