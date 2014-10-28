/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deckmanagerview;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import magicdeckmanager.MagicDeckManagerPresenter;
import magicdeckmanager.dataModel.card.CardDataModel;
import magicdeckmanager.deck.DeckStatisticsData;

/**
 * FXML Controller class
 *
 * @author drbra_000
 */
public class FXMLDeckManagerController implements Initializable {

    private static final Logger theLogger = Logger.getLogger(FXMLDeckManagerController.class
            .getName());

    @FXML
    private Label deckNameLabel;
    @FXML
    private TableView<CardDataModel> tableView;
    @FXML
    private BarChart manaBarChart;
    @FXML
    private PieChart manaDistPieChart;

    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    public void initDeck(MagicDeckManagerPresenter aThis, DeckStatisticsData deckStats) {
        deckNameLabel.setText(deckStats.getName());
        ObservableList<CardDataModel> data = tableView.getItems();
        final List<CardDataModel> cardTable = deckStats.getCardTable();
        for (CardDataModel deckData : cardTable) {

            data.add(deckData);
        }
        populateManaCostBarChart(deckStats.getManaCostSeries());
        populateManaDistPieChart(deckStats.getManaDistributionList());
    }

    private void populateManaCostBarChart(XYChart.Series manaCostSeries) {
        final ObservableList series = manaCostSeries.getData();
        Integer highestCost = 0;
        for (Object data1 : series) {
            XYChart.Data chartData = (XYChart.Data) data1;
            Integer yValue = (Integer) chartData.getYValue();
            if (yValue > highestCost) {
                highestCost = yValue;
            }
        }
        manaBarChart.getData().add(manaCostSeries);
        Axis yAxis = manaBarChart.getYAxis();
        NumberAxis numberAxis = (NumberAxis) yAxis;
        numberAxis.setAutoRanging(false);
        numberAxis.setTickUnit(1);
        numberAxis.setUpperBound(highestCost);
    }

    private void populateManaDistPieChart(ObservableList<PieChart.Data> manaDistChartData) {
        manaDistPieChart.setTitle("Mana Distribution");
        manaDistPieChart.setData(manaDistChartData);
    }

}
