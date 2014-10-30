/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deck;

import java.util.List;
import magicdeckmanager.card.Card;

/**
 *
 * @author drbra_000
 */
public class Deck {

    private DeckData deckData;
    private List<Card> main;
    private List<Card> sideboard;

    public Deck(DeckData deckData, List<Card> main, List<Card> sideboard) {
        this.deckData = deckData;
        this.main = main;
        this.sideboard = sideboard;
    }
    
    public DeckData getDeckData() {
        return deckData;
    }

    public List<Card> getMain() {
        return main;
    }

    public void setMain(List<Card> main) {
        this.main = main;
    }

    public List<Card> getSideboard() {
        return sideboard;
    }

    public void setSideboard(List<Card> sideboard) {
        this.sideboard = sideboard;
    }

}
