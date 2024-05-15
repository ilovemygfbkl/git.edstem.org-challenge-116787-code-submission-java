package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Player;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PlayerUpdateController extends Controller<Player> 
{
    @FXML private TextField nameField;
    @FXML private TextField creditField;
    @FXML private TextField ageField;
    @FXML private TextField numberField;

    @FXML private Button updateButton;
    @FXML private Button closeButton;
    @FXML private Button addButton;

    public Player getPlayerModel() 
    {
        return this.model;
    }

    public void initialize() 
    {
        nameField.setText(getPlayerModel().getName());
        creditField.setText(String.valueOf(getPlayerModel().getCredit()));
        ageField.setText(String.valueOf(getPlayerModel().getAge()));
        numberField.setText(String.valueOf(getPlayerModel().getNo()));

        if (creditField.getText().equals("-1.0") && ageField.getText().equals("-1") && numberField.getText().equals("-1")) 
        {
            updateButton.setDisable(true);
        } 
        else 
        {
            addButton.setDisable(true);
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) 
    {
        Validator validator = new Validator();
        if (validator.isValid(nameField.getText(), creditField.getText(), ageField.getText(), numberField.getText())) 
        {
            getPlayerModel().update(nameField.getText(), Double.parseDouble(creditField.getText()), Integer.parseInt(ageField.getText()), Integer.parseInt(numberField.getText()));
            getPlayerModel().getTeam().getPlayers().addPlayer(getPlayerModel());
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        } 
        else 
        {
            validator.generateErrors(nameField.getText(), creditField.getText(), ageField.getText(), numberField.getText());
            showError(validator.errors(), "Input Errors!");
        }
    }

    @FXML
    private void handleUpdate(ActionEvent event) 
    {
        Validator validator = new Validator();
        if (validator.isValid(nameField.getText(), creditField.getText(), ageField.getText(), numberField.getText())) 
        {
            getPlayerModel().update(nameField.getText(), Double.parseDouble(creditField.getText()), Integer.parseInt(ageField.getText()), Integer.parseInt(numberField.getText()));
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
        } 
        else 
        {
            validator.generateErrors(nameField.getText(), creditField.getText(), ageField.getText(), numberField.getText());
            showError(validator.errors(), "Input Errors!");
        }
    }


    @FXML
    private void handleClose(ActionEvent event)
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    private void showError(LinkedList<String> errorMessages, String title) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Parent root = loader.load();
            ErrorController errorController = loader.getController();
            errorController.setErrorMessage(errorMessages);

            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/view/error.png"));
            stage.setTitle(title);
            stage.show();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(PlayerUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    
