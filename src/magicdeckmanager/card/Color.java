/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package magicdeckmanager.card;

/**
 *
 * @author drbra_000
 */
public enum Color {
    Colorless(0),
    White(1),
    Green(2),
    Red(4),
    Black(8),
    Blue(16);

    private int flag = 0;
    
    public static Color getColorFromString(String partString) {
        switch (partString) {
            case "B":
                return Color.Black;
            case "U":
                return Color.Blue;
            case "G":
                return Color.Green;
            case "R":
                return Color.Red;
            case "W":
                return Color.White;
        }
        return null;
    }

    Color(int c) {
        flag = c;
    }
    
}
