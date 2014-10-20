/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import magicdeckmanager.json.JSONReader;
import magicdeckmanager.rulesformats.PlayFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author drbra_000
 */
public class CardManager {

    private static final Logger theLogger = Logger.getLogger(CardManager.class
            .getName());

    private static final String JSON_CARD_DATA_FILE_PATH = "./data/AllSets.json";

    private JSONObject originalCardData;
    private Map<String, Card> allCards;
    private Map<String, CardSet> allCardSets;
    private PlayFormat standardFormat;

    public CardManager() {
        allCardSets = new HashMap<>();
        allCards = new HashMap<>();
        originalCardData = loadJSONCardData();
        createCardsAndSetsFromCardJSONData(originalCardData);
        createStandardFormat();
    }

    private JSONObject loadJSONCardData() {
        JSONObject data = new JSONObject();
        try {
            data = JSONReader.readJsonFromFile(JSON_CARD_DATA_FILE_PATH);//.readJsonFromUrl("http://mtgjson.com/json/AllSets.json");
        } catch (JSONException | IOException e) {
            theLogger.severe(e.toString());
        }
        return data;
    }

    private void createCardsAndSetsFromCardJSONData(JSONObject cardData) {
        Iterator<?> keys = cardData.keys();

        while (keys.hasNext()) {
            String key = (String) keys.next();
            if(key.equals("UNH") || key.equals("UGL"))
            {
                continue;
            }
            
            JSONObject cardSetData = (JSONObject) cardData.get(key);
            if (cardSetData instanceof JSONObject) {
                JSONArray cardsData = (JSONArray) cardSetData.get("cards");
                Card[] cards = createCards(cardsData);
                CardSet cardSet = createCardSet(cardSetData, cards);
                allCardSets.put(key, cardSet);
            }
        }
    }

    private Card[] createCards(JSONArray cardsData) {
        int length = cardsData.length();
        Card[] cards = new Card[length];
        for (int i = 0; i < length; i++) {
            JSONObject cardData = (JSONObject) cardsData.get(i);
            Card card = new Card(cardData);
            cards[i] = card;
            allCards.put(card.name, card);
        }
        return cards;
    }

    private CardSet createCardSet(JSONObject cardSetData, Card[] cards) {
        CardSet cardSet = new CardSet(cardSetData, cards);
        return cardSet;
    }

    private void createStandardFormat() {
        String[] standardSetsIds = PlayFormat.standardSets;
        int length = standardSetsIds.length;
        CardSet[] standardSets = new CardSet[length];
        for (int i = 0; i < length; i++) {
            String setId = standardSetsIds[i];
            CardSet set = getCardSetFromId(setId);
            standardSets[i] = set;
        }

        setStandardFormat(new PlayFormat(PlayFormat.STANDARD, standardSets, 60, 15));
    }

    public Card getCardFromName(String name) {
        return allCards.get(name);
    }

    public CardSet getCardSetFromId(String id) {
        return allCardSets.get(id);
    }

    public Map<String, CardSet> getAllCardSets() {
        return allCardSets;
    }

    public void setAllCardSets(Map<String, CardSet> cardSets) {
        this.allCardSets = cardSets;
    }

    public PlayFormat getStandardFormat() {
        return standardFormat;
    }

    public void setStandardFormat(PlayFormat standardFormat) {
        this.standardFormat = standardFormat;
    }

}