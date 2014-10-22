/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deckmanagerview;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import magicdeckmanager.MagicDeckManagerPresenter;
import magicdeckmanager.dataModel.card.CardDataModel;

/**
 * FXML Controller class
 *
 * @author drbra_000
 */
public class FXMLDeckManagerController implements Initializable {

    @FXML
    private Label deckNameLabel;
    @FXML
    private TableView<CardDataModel> tableView;
    @FXML
    private BarChart manaBarChart;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initDeck(MagicDeckManagerPresenter aThis, String deckName, List<CardDataModel> cardTableData, XYChart.Series manaCostSeries) {
        deckNameLabel.setText(deckName);
        ObservableList<CardDataModel> data = tableView.getItems();
        for (CardDataModel deckData : cardTableData) {
            data.add(deckData);
        }
        manaBarChart.getData().add(manaCostSeries);
        NumberAxis xAxis = (NumberAxis) manaBarChart.getXAxis();
        xAxis.setTickUnit(1);
    }

}
