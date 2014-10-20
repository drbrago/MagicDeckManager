/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deckmanagerview;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import magicdeckmanager.MagicDeckManagerPresenter;
import magicdeckmanager.dataModel.deck.DeckDataModel;
import magicdeckmanager.deck.Deck;

/**
 * FXML Controller class
 *
 * @author drbra_000
 */
public class FXMLDeckManagerController implements Initializable {

    @FXML
    private Label deckNameLabel;
    @FXML
    private TableView<DeckDataModel> tableView;
    
    /**
     * Initialises the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    public void initDeck(MagicDeckManagerPresenter aThis, Deck deck) {
        deckNameLabel.setText(deck.name);
    }
    
}
