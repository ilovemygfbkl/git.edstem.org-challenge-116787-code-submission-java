package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Player;
import model.Team;
import model.Teams;

public class ViewPlayersController extends Controller<Teams> 
{

    @FXML private TableView<Player> playersTableView;
    @FXML private TableColumn<Player, String> teamColumn;
    @FXML private TableColumn<Player, String> nameColumn;
    @FXML private TableColumn<Player, Number> creditColumn;
    @FXML private TableColumn<Player, Number> ageColumn;
    @FXML private TableColumn<Player, Number> numberColumn;
    @FXML private TableColumn<Player, String> levelColumn;

    @FXML private TextField levelFilter;
    @FXML private TextField nameFilter;
    @FXML private TextField ageFromFilter;
    @FXML private TextField ageToFilter;
    
    @FXML private Button closeButton;

    private ObservableList<Player> allPlayersList;

    public Teams getTeamsModel() 
    {
        return this.model;
    }

    public void initialize() 
    {
        allPlayersList = getTeamsModel().allPlayersList();
        playersTableView.setItems(allPlayersList);
        
        teamColumn.setCellValueFactory(player -> player.getValue().getTeamNameProperty());
        nameColumn.setCellValueFactory(player -> player.getValue().nameProperty());
        creditColumn.setCellValueFactory(player -> player.getValue().getPlayerCreditProperty());
        ageColumn.setCellValueFactory(player -> player.getValue().getPlayerAgeProperty());
        numberColumn.setCellValueFactory(player -> player.getValue().getPlayerNoProperty());
        levelColumn.setCellValueFactory(player -> player.getValue().levelProperty());

        nameFilter.textProperty().addListener((obs, oldValue, newValue) -> applyFilter());
        levelFilter.textProperty().addListener((obs, oldValue, newValue) -> applyFilter());
        ageFromFilter.textProperty().addListener((obs, oldValue, newValue) -> applyFilter());
        ageToFilter.textProperty().addListener((obs, oldValue, newValue) -> applyFilter());
        
        applyFilter();
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    private void applyFilter() 
    {
        int fromAge = convertToInt(ageFromFilter);
        int toAge = convertToInt(ageToFilter);
        ObservableList<Player> filteredList = FXCollections.observableArrayList();
        
        for (Team team : getTeamsModel().currentTeams())
         {
            team.filterPlayersList(nameFilter.getText(), levelFilter.getText(), fromAge, toAge);
            filteredList.addAll(team.getPlayers().getFilteredPlayersList());
        }
        playersTableView.setItems(filteredList);
    }

    private int convertToInt(TextField textField) 
    {
        try 
        {
            return Integer.parseInt(textField.getText().trim());
        } 
        catch (NumberFormatException e) 
        {
            return 0;
        }
    }
}
