/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card;

import magicdeckmanager.card.mana.ManaCost;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;
import magicdeckmanager.card.mana.ManaPart;
import magicdeckmanager.card.mana.ManaPartColor;
import magicdeckmanager.card.mana.ManaPartPhyrexian;
import magicdeckmanager.card.mana.ManaPartSplit;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author drbra_000
 */
public class Card {

    private static final String TOUGHNESS_DATA_KEY = "toughness";

    private static final String POWER_DATA_KEY = "power";

    private static final String TEXT_DATA_KEY = "text";

    private static final String RARITY_DATA_KEY = "rarity";

    private static final String TYPES_DATA_KEY = "types";

    private static final String TYPE_DATA_KEY = "type";

    private static final String CONVERTED_MANA_COST_DATA_KEY = "cmc";

    private static final String MANA_COST_DATA_KEY = "manaCost";

    private static final String NAME_DATA_KEY = "name";

    public static final String SUBTYPES_DATA_KEY = "subtypes";

    public static final String TYPE_LAND = "Land";
    
    private static Logger theLogger = Logger.getLogger(Card.class
            .getName());

    public String name;
    public String[] types;
    public String type;
    public String[] subtypes;
    public int cmc;
    public Object power;
    public Object toughness;
    public String text;
    public String manaCostString;
    public String rarity;

    private EnumSet<Color> colorSet;
    private ManaCost manaCost;

    private JSONObject originalData;

    public Card(JSONObject cardData) {
        theLogger.fine(cardData.toString());

        originalData = cardData;

        name = cardData.getString(NAME_DATA_KEY);

        if (cardData.has(MANA_COST_DATA_KEY)) {
            manaCostString = cardData.getString(MANA_COST_DATA_KEY);
        }
        if (cardData.has(CONVERTED_MANA_COST_DATA_KEY)) {
            cmc = cardData.getInt(CONVERTED_MANA_COST_DATA_KEY);
        }

        if (cardData.has(TYPE_DATA_KEY)) {
            type = cardData.getString(TYPE_DATA_KEY);
        }
        if (cardData.has(TYPES_DATA_KEY)) {
            JSONArray typesData = (JSONArray) cardData.get(TYPES_DATA_KEY);
            types = extractTypes(typesData);
        }
        if (cardData.has(SUBTYPES_DATA_KEY)) {
            JSONArray subtypesData = (JSONArray) cardData
                    .get(SUBTYPES_DATA_KEY);
            subtypes = extractTypes(subtypesData);
        }

        rarity = cardData.getString(RARITY_DATA_KEY);

        if (cardData.has(TEXT_DATA_KEY)) {
            text = cardData.getString(TEXT_DATA_KEY);
        }

        if (cardData.has(POWER_DATA_KEY)) {
            power = extractStringOrInteger(cardData, POWER_DATA_KEY);
        }
        if (cardData.has(TOUGHNESS_DATA_KEY)) {
            toughness = extractStringOrInteger(cardData, TOUGHNESS_DATA_KEY);
        }

        calculateManaCost(manaCostString);
        colorSet = calculateCardColor();
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private Object extractStringOrInteger(JSONObject cardData, String key) {
        String string = cardData.getString(key);
        if (isInteger(string)) {
            return new Integer(string);
        } else {
            return string;
        }
    }

    private String[] extractTypes(JSONArray typesData) {
        int length = typesData.length();
        String[] result = new String[length];
        for (int i = 0; i < length; i++) {
            String string = typesData.getString(i);
            result[i] = string;
        }
        return result;
    }

    private void calculateManaCost(String manaCostString) {
        manaCost = new ManaCost(manaCostString);
    }

    private EnumSet<Color> calculateCardColor() {
        EnumSet<Color> result = EnumSet.of(Color.Colorless);
        List<ManaPart> cost = manaCost.getCost();
        extractColorsFromManaCost(cost, result);
        return result;
    }

    private void extractColorsFromManaCost(List<ManaPart> cost, EnumSet<Color> result) {
        for (ManaPart manaPart : cost) {
            if (manaPart instanceof ManaPartColor) {
                ManaPartColor manaPartColor = (ManaPartColor) manaPart;
                result.add(manaPartColor.color);
            } else if (manaPart instanceof ManaPartPhyrexian) {
                ManaPartPhyrexian manaPartPhyrexian = (ManaPartPhyrexian) manaPart;
                result.add(manaPartPhyrexian.color);
            } else if (manaPart instanceof ManaPartSplit) {
                ManaPartSplit manaPartSplit = (ManaPartSplit) manaPart;
                extractColorsFromManaCost(manaPartSplit.splitManaParts, result);
            }
        }
    }
    
    boolean isLand() {
        return (type.contains(TYPE_LAND));
    }
    
    public EnumSet<Color> getColorSet() {
        return colorSet;
    }
    
    public ManaCost getManaCost() {
        return manaCost;
    }
}
