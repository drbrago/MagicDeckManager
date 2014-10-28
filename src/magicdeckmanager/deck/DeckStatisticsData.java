/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deck;

import java.util.List;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import magicdeckmanager.dataModel.card.CardDataModel;

/**
 *
 * @author drbra_000
 */
public class DeckStatisticsData {
    private final String name;

    public String getName() {
        return name;
    }

    public List<CardDataModel> getCardTable() {
        return cardTable;
    }

    public XYChart.Series getManaCostSeries() {
        return manaCostSeries;
    }

    public ObservableList<PieChart.Data> getManaDistributionList() {
        return manaDistributionList;
    }
    private final List<CardDataModel> cardTable;
    private final XYChart.Series manaCostSeries;
    private final ObservableList<PieChart.Data> manaDistributionList;

    public DeckStatisticsData(String name, List<CardDataModel> cardTableDataFromDeck, XYChart.Series manaCostSeries, ObservableList<PieChart.Data> manaDistData) {
        this.name = name;
        this.cardTable = cardTableDataFromDeck;
        this.manaCostSeries = manaCostSeries;
        this.manaDistributionList = manaDistData;
    }
    
}
