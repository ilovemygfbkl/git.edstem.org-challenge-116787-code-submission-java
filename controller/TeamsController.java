package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Team;
import model.Teams;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TeamsController extends Controller<Teams> 
{
    @FXML private TableView<Team> teamsTableView;
    
    @FXML private TableColumn<Team, String> nameColumn;
    @FXML private TableColumn<Team, Number> numPlayersColumn;
    @FXML private TableColumn<Team, String> avgCreditColumn;
    @FXML private TableColumn<Team, String> avgAgeColumn;

    @FXML private Button manageButton;
    @FXML private Button deleteButton;
    @FXML private Button closeButton;
    @FXML private Button addButton;

    public Teams getTeamsModel() 
    {
        return this.model;
    }

    public Team getSelectedTeam() 
    {
        return teamsTableView.getSelectionModel().getSelectedItem();
    }

    public void initialize() 
    {
        nameColumn.setCellValueFactory(team -> team.getValue().nameProperty());
        numPlayersColumn.setCellValueFactory(team -> team.getValue().countPlayerProperty());
        avgCreditColumn.setCellValueFactory(team -> team.getValue().countAvgCreditProperty().asString("%.2f"));
        avgAgeColumn.setCellValueFactory(team -> team.getValue().countAvgAgeProperty().asString("%.2f"));
        teamsTableView.setItems(this.model.currentTeams());

        manageButton.setDisable(true);
        deleteButton.setDisable(true);
        teamsTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldTeam, newTeam) -> {
                    manageButton.setDisable(newTeam == null);
                    deleteButton.setDisable(newTeam == null);
                    addButton.setDisable(newTeam != null);
                });

        teamsTableView.setRowFactory(tv -> {
            TableRow<Team> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    if (row.isSelected()) {
                        teamsTableView.getSelectionModel().clearSelection();
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void handleAdd(ActionEvent event) 
    {
        openNewWindow("/view/addteam.fxml", "Adding New Team", getTeamsModel());
    }

    @FXML
    private void handleManage(ActionEvent event) 
    {
        openNewWindow("/view/ManageTeamView.fxml", "Managing Team: " + getSelectedTeam().getName(), getSelectedTeam());
    }

    @FXML
    private void handleDelete(ActionEvent event) 
    {
        getTeamsModel().remove(getSelectedTeam());
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    private void openNewWindow(String fxmlPath, String title, Object model) 
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
            Logger.getLogger(TeamsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
