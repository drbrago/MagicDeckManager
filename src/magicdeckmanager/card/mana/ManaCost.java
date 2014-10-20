/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card.mana;

import java.util.ArrayList;
import java.util.List;
import magicdeckmanager.card.Color;

/**
 *
 * @author drbra_000
 */
public class ManaCost {

    private List<ManaPart> cost;

    public ManaCost(String manaCostString) {
        cost = new ArrayList<>();
        if (manaCostString == null) {
            cost.add(new ManaPartColorless(0));
        } else {
            String[] split = manaCostString.split("}");
            for (String partString : split) {
                partString = partString.substring(1);
                ManaPart part = getManaPartFromString(partString);
                cost.add(part);
            }
        }
    }

    private ManaPart getManaPartFromString(String partString) {
        //Check for colorless.
        int parseInt = -1;
        try {
            parseInt = Integer.parseInt(partString);
        } catch (NumberFormatException e) {
            //Ignore
        }
        if (parseInt != -1) {
            return new ManaPartColorless(parseInt);
        }
        //Check for variable cost.
        if (partString.contains("P")) {
            String[] splitPartStrings = partString.split("/");
            Color color = Color.getColorFromString(splitPartStrings[0]);
            return new ManaPartPhyrexian(color);
        } else if (partString.contains("/")) {
            List<ManaPart> splitManaParts = new ArrayList<>();
            String[] splitPartStrings = partString.split("/");
            for (String splitPartString : splitPartStrings) {
                ManaPart splitManaPart = getManaPartFromString(splitPartString);
                splitManaParts.add(splitManaPart);
            }
            return new ManaPartSplit(splitManaParts);
        } else if (partString.equals("X")) {
            return new ManaPartVariable();
        } else {
            Color color = Color.getColorFromString(partString);
            if (color == null) {
                return new ManaPart();
            }
            return new ManaPartColor(color);
        }
    }
    
    public List<ManaPart> getCost() {
        return cost;
    }

    public void setCost(List<ManaPart> cost) {
        this.cost = cost;
    }
}
