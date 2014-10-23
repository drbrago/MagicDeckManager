/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import magicdeckmanager.card.mana.ManaCost;
import magicdeckmanager.card.mana.ManaPart;
import magicdeckmanager.card.mana.ManaPartColor;
import magicdeckmanager.card.mana.ManaPartColorless;
import magicdeckmanager.card.mana.ManaPartPhyrexian;
import magicdeckmanager.card.mana.ManaPartSplit;
import magicdeckmanager.dataModel.card.CardDataModel;
import magicdeckmanager.deck.Deck;
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
            if (key.equals("UNH") || key.equals("UGL")) {
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

    public List<CardDataModel> getCardTableDataFromDeck(Deck deck) {
        ArrayList<CardDataModel> result = new ArrayList();
        final List<String> main = deck.getMain();
        for (String cardName : main) {
            Card card = getCardFromName(cardName);
            result.add(new CardDataModel(cardName, card.type, card.manaCostString));
        }
        return result;
    }

    public XYChart.Series getManaCostBarChartData(Deck deck) {
        XYChart.Series result = new XYChart.Series();
        result.setName("Mana Cost");
        Map<Integer, Integer> costQuantity = new HashMap<>();
        final List<String> main = deck.getMain();
        for (String cardName : main) {
            Card card = getCardFromName(cardName);
            if (!card.isLand()) {
                Integer cmc = card.cmc;
                Integer quantity = costQuantity.get(cmc);
                if (quantity != null) {
                    quantity++;
                } else {
                    quantity = 1;
                }
                costQuantity.put(cmc, quantity);
            }
        }
        for (Map.Entry<Integer, Integer> entrySet : costQuantity.entrySet()) {
            Integer key = entrySet.getKey();
            Integer value = entrySet.getValue();
            result.getData().add(new XYChart.Data("CC" + key.toString(), value));
        }
        return result;
    }

    public ObservableList<PieChart.Data> getManaDistPieChartData(Deck deck) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        final List<String> main = deck.getMain();
        Map<Color, Integer> colorDistribution = new HashMap<>();
        Integer totalManaCost = 0;
        for (String cardName : main) {
            Card card = getCardFromName(cardName);
            if (!card.isLand()) {
                totalManaCost += card.cmc;
                final ManaCost manaCost = card.getManaCost();
                final List<ManaPart> cost = manaCost.getCost();
                addManaPartsToColorDistribution(cost, colorDistribution);
            }
        }
        for (Map.Entry<Color, Integer> entrySet : colorDistribution.entrySet()) {
            Color key = entrySet.getKey();
            Integer value = entrySet.getValue();
            double percent = (value.doubleValue() / totalManaCost.doubleValue());
            percent *= 100;
            Integer percentInteger = (int)Math.round(percent);
            final String percentString = key.toString() + " " + percentInteger.toString() + "%";
            pieChartData.add(new PieChart.Data(percentString, value));
        }
        return pieChartData;
    }

    private void addManaPartsToColorDistribution(List<ManaPart> cost, Map<Color, Integer> colorDistribution) {
        for (ManaPart manaPart : cost) {
            if (manaPart instanceof ManaPartColor) {
                ManaPartColor manaPartColor = (ManaPartColor) manaPart;
                Integer quantity = colorDistribution.get(manaPartColor.color);
                if (quantity == null) {
                    quantity = 0;
                }
                quantity++;
                colorDistribution.put(manaPartColor.color, quantity);
            } else if (manaPart instanceof ManaPartPhyrexian) {
                ManaPartPhyrexian manaPartPhyrexian = (ManaPartPhyrexian) manaPart;
                Integer quantity = colorDistribution.get(manaPartPhyrexian.color);
                if (quantity == null) {
                    quantity = 0;
                }
                quantity++;
                colorDistribution.put(manaPartPhyrexian.color, quantity);
            } else if (manaPart instanceof ManaPartColorless) {
                ManaPartColorless manaPartColorless = (ManaPartColorless) manaPart;
                Integer quantity = colorDistribution.get(Color.Colorless);
                if (quantity == null) {
                    quantity = 0;
                }
                quantity += manaPartColorless.amount;
                colorDistribution.put(Color.Colorless, quantity);
            } else if (manaPart instanceof ManaPartSplit) {
                ManaPartSplit manaPartSplit = (ManaPartSplit) manaPart;
                addManaPartsToColorDistribution(manaPartSplit.splitManaParts, colorDistribution);
            }
        }
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
