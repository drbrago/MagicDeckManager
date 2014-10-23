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
    
    private static final Logger theLogger = Logger.getLogger(FXMLDeckManagerController.class
            .getName());
    
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
        final ObservableList series = manaCostSeries.getData();
        Integer highestCost = 0;
        for (Object data1 : series) {
            XYChart.Data chartData = (XYChart.Data) data1;
            Integer yValue = (Integer) chartData.getYValue();
            if(yValue > highestCost)
            {
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

}
