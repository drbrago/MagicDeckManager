/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager;

import magicdeckmanager.deckselectview.FXMLDeckSelectController;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import magicdeckmanager.card.CardManager;
import magicdeckmanager.dataModel.card.CardDataModel;
import magicdeckmanager.deck.Deck;
import magicdeckmanager.deck.DeckManager;
import magicdeckmanager.deckmanagerview.FXMLDeckManagerController;

/**
 *
 * @author drbra_000
 */
public class MagicDeckManagerPresenter {

    private static final Logger theLogger = Logger.getLogger(MagicDeckManagerPresenter.class
            .getName());

    private final Scene scene;
    private final DeckManager deckManager;
    private final CardManager cardManager;
    private final Stage stage;

    MagicDeckManagerPresenter(Stage stage, Scene scene, DeckManager deckManager, CardManager cardManager) {
        this.stage = stage;
        this.scene = scene;
        this.deckManager = deckManager;
        this.cardManager = cardManager;

        showSelectDeckListView();
    }

    public void deckSelected(int selectedIndex) {
        Deck deck = deckManager.getDeckFromIndex(selectedIndex);
        showDeckManagerView(deck);
    }
    
    private void showSelectDeckListView() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("deckselectview/FXMLDeckSelectView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            FXMLDeckSelectController controller
                    = loader.<FXMLDeckSelectController>getController();
            controller.initDeckList(this, deckManager.getDeckTableData());
        } catch (IOException ex) {
            theLogger.log(Level.SEVERE, null, ex);
        }
    }

    private void showDeckManagerView(Deck deck) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("deckmanagerview/FXMLDeckManagerView.fxml")
            );
            scene.setRoot((Parent) loader.load());
            stage.setWidth(1024);
            stage.setHeight(700);
            stage.setY(0);
            stage.setX(0);
            FXMLDeckManagerController controller
                    = loader.<FXMLDeckManagerController>getController();
            final List<CardDataModel> cardTableDataFromDeck = cardManager.getCardTableDataFromDeck(deck);
            final XYChart.Series manaCostSeries = cardManager.getManaCostBarChartData(deck);
            final ObservableList<PieChart.Data> manaDistData = cardManager.getManaDistPieChartData(deck);
            controller.initDeck(this, deck.name, cardTableDataFromDeck, manaCostSeries, manaDistData);
        } catch (IOException ex) {
            theLogger.log(Level.SEVERE, null, ex);
        }
    }
}