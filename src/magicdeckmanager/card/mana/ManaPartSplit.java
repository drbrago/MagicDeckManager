/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card.mana;

import java.util.List;

/**
 *
 * @author drbra_000
 */
public class ManaPartSplit extends ManaPart {

    public List<ManaPart> splitManaParts;

    ManaPartSplit(List<ManaPart> splitParts) {
        this.splitManaParts = splitParts;
    }

}
