package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SceneController extends ControllerBase {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private FXMLLoader loader;

    BuilderController builderController = new BuilderController();

    ExperienceFormController experienceFormController = new ExperienceFormController();

    protected void setStageFromEvent(ActionEvent e) {
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    }

    public void start(ActionEvent e) {
        this.setStageFromEvent(e);
        String resourceName = "Builder.fxml";
        this.loadController(resourceName);
        this.openFromController(builderController);
    }

    public void edit(ActionEvent e) {
        this.setStageFromEvent(e);
        String resourceName = "Builder.fxml";
        this.loadController(resourceName);
        this.builderController.enableEditMode();
        this.openFromController(builderController);
    }

    public void openExperienceForm() {
        Stage expStage = this.initExperienceFormStage();
        this.loadController("ExperienceForm.fxml");
        this.experienceFormController.setStage(expStage, Modality.APPLICATION_MODAL);
        this.openFromController(this.experienceFormController);
    }

    private Stage initExperienceFormStage() {
        Stage expStage = new Stage();
        Image icon = this.stage.getIcons().get(0);
        expStage.getIcons().add(icon);
        expStage.setResizable(false);
        expStage.setTitle("Experience form");
        expStage.initOwner(this.stage);
        return expStage;
    }

    protected void loadController(String resourceName) {
        try {
            this.loader = new FXMLLoader(getClass().getResource(resourceName));
            this.root = this.loader.load();
            ControllerBase controller = this.loader.getController();
            controller.setSceneController(this);
            this.associateController(controller);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void openFromController(ControllerBase controller) {
        if (controller.getStage() == null) {
            controller.setStage(this.stage);
        }
        Stage currentStage = controller.getStage();
        this.scene = new Scene(this.root);
        currentStage.setScene(this.scene);
        currentStage.setResizable(false);
        Modality modality = currentStage.getModality();
        if (modality == Modality.APPLICATION_MODAL) {
            currentStage.showAndWait();
        }
        else {
            currentStage.show();
        }
    }

    private void associateController(ControllerBase controller) {
        if (controller instanceof BuilderController) {
            this.builderController = (BuilderController) controller;
        }
        else if (controller instanceof ExperienceFormController) {
            this.experienceFormController = (ExperienceFormController) controller;
        }
    }

}
