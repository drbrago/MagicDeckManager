/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.deck;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import magicdeckmanager.card.Color;
import magicdeckmanager.dataModel.card.CardDataModel;

/**
 *
 * @author drbra_000
 */
public class Deck {

    public String name;
    public EnumSet<Color> color;

    private List<String> main;
    private List<String> sideboard;

    public Deck(String name) {
        this.name = name;
        main = new ArrayList<String>();
        sideboard = new ArrayList<String>();
        color = EnumSet.of(Color.Colorless);
    }

    public Deck(String name, List<String> main, List<String> sideboard) {
        this.name = name;
        this.main = main;
        this.sideboard = sideboard;
        color = EnumSet.of(Color.Colorless);
    }

    public List<String> getMain() {
        return main;
    }

    public void setMain(List<String> main) {
        this.main = main;
    }

    public List<String> getSideboard() {
        return sideboard;
    }

    public void setSideboard(List<String> sideboard) {
        this.sideboard = sideboard;
    }

    public void addColors(EnumSet<Color> colorToAdd) {
        for (Color c : colorToAdd) {
            if(color.contains(c) == false)
            {
                color.add(c);
            }
        }
    }
}
