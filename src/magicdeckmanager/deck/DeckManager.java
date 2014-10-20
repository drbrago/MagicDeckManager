/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deck;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import magicdeckmanager.card.Card;
import magicdeckmanager.card.CardManager;
import magicdeckmanager.card.Color;
import magicdeckmanager.dataModel.deck.DeckDataModel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author drbra_000
 */
public class DeckManager {

    private static final Logger theLogger = Logger.getLogger(DeckManager.class
            .getName());
    
    private static final String decksFolderPath = "./data/decks";

    private List<Deck> decks;
    private CardManager cardManager;

    public DeckManager(CardManager cardManager) {
        this.cardManager = cardManager;
        decks = loadDecks();
    }
    
    private List<Deck> loadDecks() {
        theLogger.info("load decks from path: " + decksFolderPath);
        List<Deck> result = new ArrayList<>();
        final File folder = new File(decksFolderPath);
        for (final File fileEntry : folder.listFiles()) {
            Deck deck = createDeckFromFile(fileEntry);
            result.add(deck);
        }
        return result;
    }

    public Deck createDeckFromFile(File deckFile) {
        String deckName = deckFile.getName();
        //String[] split = deckName.split(".");
        //deckName = split[0];
        Deck deck = new Deck(deckName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document deckDoc = dBuilder.parse(deckFile);
            NodeList nodeList = deckDoc.getElementsByTagName("Cards");
            List<String> main = new ArrayList<>();
            List<String> sideboard = new ArrayList<>();
            int length = nodeList.getLength();
            for (int i = 0; i < length; i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String quantityString = element.getAttribute("Quantity");
                    int quantity = Integer.parseInt(quantityString);
                    String inSideboardString = element
                            .getAttribute("Sideboard");
                    Boolean inSideboard = Boolean.valueOf(inSideboardString);
                    String name = element.getAttribute("Name");
                    
                    //Add cards color to deck color set.
                    Card card = cardManager.getCardFromName(name);
                    EnumSet<Color> color = card.getColorSet();
                    deck.addColors(color);
                    
                    for (int j = 0; j < quantity; j++) {
                        if (inSideboard) {
                            sideboard.add(name);
                        } else {
                            main.add(name);
                        }
                    }
                }
            }

            deck.setMain(main);
            deck.setSideboard(sideboard);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            theLogger.log(Level.SEVERE, null, e);
        }

        return deck;
    }
    
    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> deck) {
        this.decks = deck;
    }

    public List<DeckDataModel> getDeckTableData() {
        ArrayList<DeckDataModel> result = new ArrayList();
        for (Deck deck : decks) {
            DeckDataModel dataModel = new DeckDataModel(deck.name, deck.color);
            result.add(dataModel);
        }
        return result;
    }

    public Deck getDeckFromIndex(int selectedIndex) {
        return decks.get(selectedIndex);
    }
}
