/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import magicdeckmanager.card.CardManager;
import magicdeckmanager.deck.DeckManager;

/**
 *
 * @author drbra_000
 */
public class MagicDeckManagerApplication extends Application {

    private DeckManager deckManager;
    private CardManager cardManager;
    private MagicDeckManagerPresenter presenter;

    @Override
    public void start(Stage stage) throws Exception {
        cardManager = new CardManager();
        deckManager = new DeckManager(cardManager);

        Scene scene = new Scene(new GridPane());

        presenter = new MagicDeckManagerPresenter(scene, deckManager, cardManager);

        stage.setTitle("Deck Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
