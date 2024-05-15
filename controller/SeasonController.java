package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Season;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SeasonController extends Controller<Season> 
{
    @FXML private Button closeButton;

    public Season getSeasonModel() 
    {
        return this.model;
    }

    @FXML
    private void handleRound(ActionEvent event) 
    {
        openNewWindow("/view/SeasonRoundView.fxml", "Season Rounds", getSeasonModel());
    }

    @FXML
    private void handleCurrentRound(ActionEvent event) 
    {
        openNewWindow("/view/CurrentRoundTeams.fxml", "Tournament", getSeasonModel());
    }

    @FXML
    private void handleGame(ActionEvent event) 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/error.fxml"));
            Parent root = loader.load();
            ErrorController errorController = loader.getController();
            errorController.setErrorMessage(getSeasonModel().playGame());

            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/view/nba.png"));
            stage.setTitle("All Games Played!");
            stage.show();
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(SeasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleResult(ActionEvent event) 
    {
        openNewWindow("/view/RecordView.fxml", "Season Record", getSeasonModel());
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void openNewWindow(String fxmlPath, String title, Season model) 
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
            Logger.getLogger(SeasonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
