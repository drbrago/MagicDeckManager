/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card;

import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author drbra_000
 */
public class CardSet {

    private static Logger theLogger = Logger.getLogger(CardSet.class
            .getName());

    private Card[] cards;

    public String code;
    public String name;

    CardSet(JSONObject cardSetData, Card[] cards) {
        theLogger.fine(cardSetData.toString());

        this.cards = cards;
        code = (String) cardSetData.get("code");
        name = (String) cardSetData.get("name");
    }

    public Card[] getCards() {
        return cards;
    }
}
