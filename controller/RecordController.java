package controller;

import au.edu.uts.ap.javafx.Controller;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Record;
import model.Season;

public class RecordController extends Controller<Season> 
{
    @FXML private TableView<Record> seasonTableView;
    @FXML private TableColumn<Record, Integer> roundColumn;
    @FXML private TableColumn<Record, Integer> gameColumn;
    @FXML private TableColumn<Record, String> winnerColumn;
    @FXML private TableColumn<Record, String> loserColumn;

    @FXML private Button closeButton;
    
    private ObservableList<Record> recordList;

    public Season getSeasonModel() 
    {
        return this.model;
    }

    public void initialize() 
    {
        recordList = getSeasonModel().record();
        seasonTableView.setItems(recordList);

        roundColumn.setCellValueFactory(record -> record.getValue().roundProperty().asObject());
        gameColumn.setCellValueFactory(record -> record.getValue().gameNumberProperty().asObject());
        winnerColumn.setCellValueFactory(record -> record.getValue().winTeamProperty());
        loserColumn.setCellValueFactory(record -> record.getValue().loseTeamProperty());
    }

    @FXML
    private void handleClose(ActionEvent event) 
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
