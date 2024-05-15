package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Game;
import model.Season;

public class CurrentRoundTeamsController extends Controller<Season> 
{
    @FXML private Label roundLabel;
    @FXML private TableView<Game> gamesTableView;
    @FXML private ObservableList<Game> currentGames;

    @FXML private TableColumn<Game, String> team1Column;
    @FXML private TableColumn<Game, String> team2Column;
    @FXML private TableColumn<SimpleStringProperty, String> vsColumn;

    @FXML private Button closeButton;

    public Season getSeasonModel() 
    {
        return this.model;
    }

    public void initialize() 
    {
        roundLabel.setText("Round: " + (getSeasonModel().round() + 1));
        currentGames = getSeasonModel().getCurrentSchedule();
        gamesTableView.setItems(currentGames);
        team1Column.setCellValueFactory(game -> game.getValue().team1());
        team2Column.setCellValueFactory(game -> game.getValue().team2());
        vsColumn.setCellValueFactory(game -> new SimpleStringProperty("VS"));
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
