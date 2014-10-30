/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.optimization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import magicdeckmanager.card.Card;
import magicdeckmanager.card.CardManager;
import magicdeckmanager.dataModel.card.CardProbabilityDataModel;
import magicdeckmanager.deck.Deck;
import magicdeckmanager.utils.NumberUtils;
import magicdeckmanager.utils.ProbabilityUtils;

/**
 *
 * @author drbra_000
 */
public class DeckOptimizationManager {

    private final CardManager cardManager;

    public DeckOptimizationManager(CardManager cardManager) {
        this.cardManager = cardManager;
    }

    public List<CardProbabilityDataModel> calculateCardProbability(Deck deck) {
        List<CardProbabilityDataModel> cardProbabilities = new ArrayList<>();
        List<Card> main = deck.getMain();
        List<Card> calculatedCards = new ArrayList<>();
        final int size = main.size();
        for (Card card : main) {
            if (!calculatedCards.contains(card)) {
                int quantity = Collections.frequency(main, card);
                double probability = ProbabilityUtils.probabilityDrawingAtLeastOneCard(7, size, quantity);
                probability = NumberUtils.round(probability * 100, 1);
                cardProbabilities.add(new CardProbabilityDataModel(card.name, quantity, probability));
                calculatedCards.add(card);
            }
        }
        return cardProbabilities;
    }

}
