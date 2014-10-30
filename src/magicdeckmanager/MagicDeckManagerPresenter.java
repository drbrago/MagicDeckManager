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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import magicdeckmanager.card.CardManager;
import magicdeckmanager.dataModel.card.CardProbabilityDataModel;
import magicdeckmanager.deck.Deck;
import magicdeckmanager.deck.DeckData;
import magicdeckmanager.deck.DeckManager;
import magicdeckmanager.optimization.DeckOptimizationManager;
import magicdeckmanager.deck.DeckStatisticsData;
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
    private final DeckOptimizationManager deckOptimizationManager;
    private final Stage stage;

    MagicDeckManagerPresenter(Stage stage, Scene scene, DeckManager deckManager, CardManager cardManager, DeckOptimizationManager deckOptimizationManager) {
        this.stage = stage;
        this.scene = scene;
        this.deckManager = deckManager;
        this.cardManager = cardManager;
        this.deckOptimizationManager = deckOptimizationManager;

        showSelectDeckListView();
    }

    public void deckSelected(int selectedIndex) {
        DeckData deckData = deckManager.getDeckFromIndex(selectedIndex);
        showDeckManagerView(deckData);
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

    private void showDeckManagerView(DeckData deckData) {
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
            
            Deck deck = cardManager.createDeckFromDeckData(deckData);
            DeckStatisticsData deckStats = cardManager.getDeckStatistics(deckData);
            List<CardProbabilityDataModel> probabilityTableData = deckOptimizationManager.calculateCardProbability(deck);            
            
            controller.initDeck(this, deckStats, probabilityTableData);
        } catch (IOException ex) {
            theLogger.log(Level.SEVERE, null, ex);
        }
    }
}