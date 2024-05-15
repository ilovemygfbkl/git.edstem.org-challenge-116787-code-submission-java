package controller;

import au.edu.uts.ap.javafx.Controller;
import au.edu.uts.ap.javafx.ViewLoader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Player;
import model.Team;

import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageTeamController extends Controller<Team> 
{

    @FXML private TableView<Player> playersTableView;
    @FXML private TableColumn<Player, String> nameColumn;
    @FXML private TableColumn<Player, Number> creditColumn;
    @FXML private TableColumn<Player, Number> ageColumn;
    @FXML private TableColumn<Player, Number> numberColumn;

    @FXML private TextField teamNameField;

    @FXML private Button addButton;
    @FXML private Button updateButton;
    @FXML private Button closeButton;
    @FXML private Button deleteButton;

    public Team getTeamModel() 
    {
        return this.model;
    }

    public void initialize() 
    {
        playersTableView.setItems(getTeamModel().getPlayers().getPlayersList());
        nameColumn.setCellValueFactory(player -> player.getValue().nameProperty());
        creditColumn.setCellValueFactory(player -> player.getValue().getPlayerCreditProperty());
        ageColumn.setCellValueFactory(player -> player.getValue().getPlayerAgeProperty());
        numberColumn.setCellValueFactory(player -> player.getValue().getPlayerNoProperty());

        teamNameField.setText(getTeamModel().getName());
        updateButton.setDisable(true);
        deleteButton.setDisable(true);
        playersTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldPlayer, newPlayer) -> {
                    updateButton.setDisable(newPlayer == null);
                    deleteButton.setDisable(newPlayer == null);
                    addButton.setDisable(newPlayer != null);
                });

        playersTableView.setRowFactory(tableView -> {
            TableRow<Player> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    if (row.isSelected()) {
                        playersTableView.getSelectionModel().clearSelection();
                    }
                }
            });
            return row;
        });
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        String teamName = teamNameField.getText();
        Validator validator = new Validator();
        if (validator.isValid(teamName)) 
        {
            getTeamModel().setName(teamName);
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.close();
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
            Logger.getLogger(ManageTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void openPlayerWindow(String title, Player player) 
    {
        try 
        {
            Stage stage = new Stage();
            stage.setX(ViewLoader.X + 601);
            stage.setY(ViewLoader.Y);
            stage.getIcons().add(new Image("/view/nba.png"));
            ViewLoader.showStage(player, "/view/PlayerUpdateView.fxml", title, stage);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(ManageTeamController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleAdd(ActionEvent event) 
    {
        Player player = new Player("", -1.0, -1, -1);
        player.setTeam(getTeamModel());
        openPlayerWindow("Adding New Player", player);
    }

    @FXML
    private void handleDelete(ActionEvent event) 
    {
        getTeamModel().getPlayers().removePlayer(playersTableView.getSelectionModel().getSelectedItem());
    }

    @FXML
    private void handleUpdate(ActionEvent event) 
    {
        openPlayerWindow("Updating Player: " + playersTableView.getSelectionModel().getSelectedItem().getName(), playersTableView.getSelectionModel().getSelectedItem());
    }


}
