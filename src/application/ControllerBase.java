package application;

import application.handlers.InputHandler;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ControllerBase {
    
    protected Stage stage;

    protected SceneController sceneController;

    protected InputHandler inputHandler = new InputHandler();

    protected void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }

    protected SceneController getSceneController() {
        return this.sceneController;
    }

    protected void setStage(Stage stage, Modality modality) {
        this.stage = stage;
        this.stage.initModality(modality);
    }

    protected void setStage(Stage stage) {
        this.stage = stage;
    }

    protected Stage getStage() {
        return this.stage;
    }

    protected Scene getScene() {
        return this.stage.getScene();
    }

    protected Class<?> getType() {
        return this.getClass();
    }

}
