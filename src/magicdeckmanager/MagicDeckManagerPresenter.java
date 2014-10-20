/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager;

import magicdeckmanager.deckselectview.FXMLDeckSelectController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import magicdeckmanager.card.CardManager;
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

    MagicDeckManagerPresenter(Scene scene, DeckManager deckManager, CardManager cardManager) {
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
            FXMLDeckManagerController controller
                    = loader.<FXMLDeckManagerController>getController();
            controller.initDeck(this, deck);
        } catch (IOException ex) {
            theLogger.log(Level.SEVERE, null, ex);
        }
    }


}
