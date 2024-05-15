package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;

public class ErrorController 
{
    @FXML private Text messageText;
    @FXML private Button closeButton;

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    public void setErrorMessage(LinkedList<String> messages) 
    {
        StringBuilder text = new StringBuilder();
        for (String message : messages) 
        {
            text.append(message).append("\n");
        }
        messageText.setText(text.toString());
    }

    public void setErrorMessage(String message) 
    {
        messageText.setText(message);
    }
}
