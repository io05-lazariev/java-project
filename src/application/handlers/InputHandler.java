package application.handlers;

import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class InputHandler {
    
    public TextField getInput(String inputId, Scene scene) {
        TextField input = (TextField) scene.lookup(inputId);
        return input;
    }
}
