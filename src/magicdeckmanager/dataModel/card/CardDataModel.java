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
    
    public CardDataModel() {
        this("");
    }

    public CardDataModel(String name) {
        setName(name);
    }
    
    public String getName() {
        return name.get();
    }

    public void setName(String cardName) {
        name.set(cardName);
    }
}