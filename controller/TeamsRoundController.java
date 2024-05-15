package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Game;
import model.Season;
import model.Team;

public class TeamsRoundController extends Controller<Season> 
{
    @FXML private ListView<Team> teamsList;
    @FXML private Label currentRoundLabel;

    @FXML private TableColumn<Game, Integer> matchNumberColumn;
    @FXML private TableColumn<Game, String> team1Column;
    @FXML private TableColumn<Game, String> team2Column;
    @FXML private TableView<Game> GamesTable;

    @FXML private Button addTeamsButton;
    @FXML private Button arrangeButton;
    private ObservableList<Game> gameList;

    public Season getSeasonModel() 
    {
        return this.model;
    }

    public void initialize() 
    {
        gameList = getSeasonModel().getCurrentSchedule();
        GamesTable.setItems(gameList);

        teamsList.setItems(getSeasonModel().getCurrentTeams());
        currentRoundLabel.setText("Round: " + (getSeasonModel().round() + 1));
        arrangeButton.setDisable(true);
        addTeamsButton.setDisable(true);
        teamsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        matchNumberColumn.setCellValueFactory(match -> match.getValue().termProperty().asObject());
        team1Column.setCellValueFactory(match -> match.getValue().team1());
        team2Column.setCellValueFactory(match -> match.getValue().team2());

        teamsList.getSelectionModel().getSelectedItems().addListener((ListChangeListener<? super Team>) c -> {
            addTeamsButton.setDisable(teamsList.getSelectionModel().getSelectedItems().size() != 2);
        });
    }

    @FXML
    private void handleAdd(ActionEvent event) 
    {
        ObservableList<Team> selectedTeams = teamsList.getSelectionModel().getSelectedItems();
        getSeasonModel().addTeams(selectedTeams);
        teamsList.getSelectionModel().clearSelection();
        teamsList.getItems().removeAll(selectedTeams);

        if (teamsList.getItems().isEmpty() && !GamesTable.getItems().isEmpty()) 
        {
            arrangeButton.setDisable(false);
        }
    }

    @FXML
    private void handleSeason(ActionEvent event) 
    {
        Stage stage = (Stage) arrangeButton.getScene().getWindow();
        stage.close();
    }
}
