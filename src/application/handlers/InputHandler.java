package application.handlers;

import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextInputControl;

public class InputHandler {
    
    public TextInputControl getInput(String inputId, Scene scene) {
        TextInputControl input = (TextInputControl) scene.lookup(inputId);
        return input;
    }

    public <T> T getChoice(ChoiceBox<T> choiceBox) {
        return choiceBox.getValue();
    }
}
