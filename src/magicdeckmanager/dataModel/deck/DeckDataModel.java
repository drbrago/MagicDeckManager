/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.dataModel.deck;

import java.util.EnumSet;
import javafx.beans.property.SimpleStringProperty;
import magicdeckmanager.card.Color;

/**
 *
 * @author drbra_000
 */
public class DeckDataModel {
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty color = new SimpleStringProperty("");

    public DeckDataModel() {
        this("", "");
    }

    public DeckDataModel(String name, String color) {
        setName(name);
        setColor(color);
    }

    public DeckDataModel(String name, EnumSet<Color> color) {
        setName(name);
        String colorString = "";
        for (Color c : color) {
            colorString += c.toString() + " ";
        }
        setColor(colorString);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String deckName) {
        name.set(deckName);
    }

    public String getColor() {
        return color.get();
    }

    public void setColor(String deckColor) {
        color.set(deckColor);
    }
}