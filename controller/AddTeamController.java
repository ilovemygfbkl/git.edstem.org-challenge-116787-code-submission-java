package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Team;
import model.Teams;
import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddTeamController extends Controller<Teams> {
    @FXML private TextField teamNameField;
    @FXML private Button button;
    @FXML private GridPane gridPane;

    @FXML
    public void initialize() 
    {
        GridPane.setMargin(teamNameField, new Insets(0, 0, 0, 75));
    }

    public Teams getTeamsModel() 
    {
        return model;
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleAddTeam(ActionEvent event) 
    {
        String teamName = teamNameField.getText();
        Validator validator = new Validator();
        if (validator.isValid(teamName)) 
        {
            if (getTeamsModel().hasTeam(teamName)) {
                validator.addError("Team " + teamName + " already exists");
                showError(validator.errors());
            } else {
                getTeamsModel().addTeam(new Team(teamName));
                handleClose(event);
            }
        } 
        else 
        {
            validator.generateErrors(teamName);
            showError(validator.errors());
        }
    }

    @FXML
    private void showError(LinkedList<String> errorMessages) 
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
            stage.setTitle("Error");
            stage.show();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(AddTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
