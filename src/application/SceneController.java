package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private FXMLLoader loader;

    private BuilderController builderController = new BuilderController();

    public void start(ActionEvent e) {
        this.setStageFromEvent(e);
        this.loadBuilder();
        this.openBuilder();
    }

    public void edit(ActionEvent e) {
        this.setStageFromEvent(e);
        this.loadBuilder();
        this.builderController.enableEditMode();
        this.openBuilder();
    }

    protected void openBuilder() {
        this.builderController.setStage(this.stage);
        this.scene = new Scene(this.root);
        this.stage.setScene(this.scene);
        this.stage.setResizable(false);
        this.stage.show();
    }
    
    protected void setStageFromEvent(ActionEvent e) {
        this.stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    }

    protected void loadBuilder() {
        String resourceName = "Builder.fxml";
        try {
            this.loader = new FXMLLoader(getClass().getResource(resourceName));
            this.root = this.loader.load();
            this.builderController = this.loader.getController();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
