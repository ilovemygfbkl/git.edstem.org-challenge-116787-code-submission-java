package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Teams;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExploreTeamsController extends Controller<Teams> 
{

    @FXML private Button teamsMenuButton;
    @FXML private Button viewPlayersButton;
    @FXML private Button closeButton;

    public Teams getTeamsModel() 
    {
        return this.model;
    }

    @FXML
    private void handleTeamsMenu(ActionEvent event) 
    {
        openNewWindow("/view/TeamsTable.fxml", "Teams Menu", getTeamsModel());
    }

    @FXML
    private void handleViewPlayers(ActionEvent event) 
    {
        openNewWindow("/view/PlayersView.fxml", "Players", getTeamsModel());
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void openNewWindow(String fxmlPath, String title, Teams model) 
    {
        try 
        {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            ViewLoader.showStage(model, fxmlPath, title, stage);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ExploreTeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
