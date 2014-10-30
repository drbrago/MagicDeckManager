/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.dataModel.card;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author drbra_000
 */
public class CardProbabilityDataModel {
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleIntegerProperty quantity = new SimpleIntegerProperty(0);
    private final SimpleDoubleProperty probability = new SimpleDoubleProperty(0.0);
    
    public CardProbabilityDataModel(String name, int quantity, double probability) {
        setName(name);
        setQuantity(quantity);
        setProbability(probability);
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String cardName) {
        name.set(cardName);
    }
    
    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int cardQuantity) {
        quantity.set(cardQuantity);
    }
    
    public double getProbability() {
        return probability.get();
    }

    public void setProbability(double cardProbability) {
        probability.set(cardProbability);
    }
    
}
