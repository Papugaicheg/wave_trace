package com.wave.trace.wave_trace.controllers;

import com.wave.trace.wave_trace.FieldDrawer;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class InsertSceneController {

    @FXML
    private TextField firstTextField;

    @FXML
    private TextField secondTextField;

    @FXML
    protected void onInputButtonClick() throws IOException {
        int height = Integer.parseInt(firstTextField.getCharacters().toString());
        int width = Integer.parseInt(secondTextField.getCharacters().toString());
        FieldDrawer.drawField(height,width);
    }

}
