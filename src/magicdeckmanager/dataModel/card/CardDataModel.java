/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.dataModel.card;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author drbra_000
 */
public class CardDataModel {
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty type = new SimpleStringProperty("");
    private final SimpleStringProperty cost = new SimpleStringProperty("");
    
    public CardDataModel() {
        this("", "", "");
    }

    public CardDataModel(String name, String type, String cost) {
        setName(name);
        setType(type);
        setCost(cost);
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String cardName) {
        name.set(cardName);
    }
    
    public String getCost() {
        return cost.get();
    }

    public void setCost(String cardCost) {
        cost.set(cardCost);
    }
    
    public String getType() {
        return type.get();
    }

    public void setType(String cardType) {
        type.set(cardType);
    }
}