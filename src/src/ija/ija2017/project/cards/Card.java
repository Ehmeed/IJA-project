
package ija.ija2017.project.cards;

import ija.ija2017.project.klondike.Assets;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author xhruba08
 */
public class Card implements Serializable{
    /**
     * value of the card
     */
    private final int value;
    /**
     * color of the card
     */
    private final Color color;
    /**
     * indicates which way is the card facing
     */
    private boolean faceUp;
    
    private int x,y = -100;
    private int width,height = 0;
    
    /**
     * Enum Color defining possible colors of card
     */
    public static enum Color {
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES;    
        
        @Override
        public String toString(){
            return this.name().charAt(0)+"";           
        }
    
    }
    // end of enum
    
    public Card(Card.Color color, int value, boolean faceUp){
        this.color = color;
        this.value = value;  
        this.faceUp = faceUp;
    }
    
    public void draw(Graphics g, int x, int y, int width, int height, int hitbox){        
        if(this.faceUp){
            g.drawImage(Assets.get(this.value, this.color), x, y, width, height, null);
        }else {
            g.drawImage(Assets.back, x, y, width, height, null);
        }      
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = hitbox;
    }

    /**
     * Sets card coords to 0, when new card is put over it
     */
    public void disappear(){
        this.x = 0;
        this.y = 0;
        this.width = 0;
        this.height = 0;
    }
    
    /**
     * Checks if cards was clicked
     * @param mouseX mouse coordinate X
     * @param mouseY mouse coordinate Y
     * @return true if give coords are where cards is drawn
     */
    public boolean clicked(int mouseX, int mouseY){
        return ((mouseX >= x) && (mouseY >= y) && (mouseX < x + width) && (mouseY < y + height));        
    }
    
    
    
     public boolean similarColorTo(Card c) {
            if (c.color() ==Color.CLUBS || c.color() == Color.SPADES){
                if (this.color ==Color.CLUBS || this.color == Color.SPADES){
                    return true;
                }
            } 
            if (c.color() ==Color.HEARTS || c.color() == Color.DIAMONDS){
                if (this.color ==Color.HEARTS || this.color == Color.DIAMONDS){
                    return true;
                }
            } 
            return false;
    }
     /*
     Compares value of two cards.
     Returns positive difference if this cards is higher value, otherwise return negative difference.
     Returns zero on equal cards.
     */
     public int compareValue(Card c) {
           return value - c.value();
    }
       
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.value != other.value) {
            return false;
        }
        if (this.color != other.color) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.value;
        hash = 41 * hash + Objects.hashCode(this.color);
        return hash;
    }
    
    @Override
    public String toString(){
        String card = "";
        if (this.value > 1 && this.value < 11){
            card += this.value;
        } else {
            switch(this.value){               
                case 11:
                    card += "J";
                    break;
                case 12:
                    card += "Q";
                    break;
                case 13:
                    card += "K";
                    break;
                case 1:
                    card += "A";
                    break;
            }        
        }        
        card += "(" + color.toString() + ")";
        return card;
    }
    
    //getters and setters
    
    public Card.Color color(){
        return this.color;
    }
    
    public int value(){
        return this.value;
    }
    
    public boolean isTurnedFaceUp() {
        return this.faceUp;
    }
    /*
     Turns the card face up. Returns true if suceeded, false if the card was already facing up.
    */
    public boolean turnFaceUp() {
        if (this.faceUp){ 
            return false;
        }
        else {
            this.faceUp = true;
            return true;
        }        
    }
    
    public void turnFaceDown(){
        this.faceUp = false;
    }

}
