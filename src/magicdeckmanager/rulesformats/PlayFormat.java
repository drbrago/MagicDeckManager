package magicdeckmanager.rulesformats;

import magicdeckmanager.card.CardSet;

public class PlayFormat {

    public static final String STANDARD = "standard";
    public static final String MODERN = "modern";
    public static final String[] standardSets = {"THS", "BNG", "JOU", "M15", "KTK"};

    public String name;
    public int sideboardSize;
    public int deckSize;

    private CardSet[] sets;

    public PlayFormat(String name, CardSet[] sets, int deckSize, int sideboardSize) {
        this.name = name;
        this.setSets(sets);
        this.deckSize = deckSize;
        this.sideboardSize = sideboardSize;
    }

    public CardSet[] getSets() {
        return sets;
    }

    public void setSets(CardSet[] sets) {
        this.sets = sets;
    }

}
