
package ija.ija2017.project.cards;

import ija.ija2017.project.klondike.Assets;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Represent target pack for stacking cards of the same color 
 * going from Ace upwards
 * @author xhruba08
 */
public class TargetPack implements Serializable{
    private ArrayList<Card> cards;
    private Card.Color color;
    private boolean hint = false;
    
    public TargetPack(Card.Color color){
        cards = new ArrayList<>();     
        this.color = color;
    }
    
    public void draw(Graphics g, int width, int height, int offset){
        if(hint){
            g.drawImage(Assets.hint_arrow, 13*width/28 + offset*width/8, -height/30, width/30, height/10, null);      
        }
        if (!this.isEmpty()){
            this.get().draw(g, (3*width/7 + offset*width/8), height/20, width/9, height/5, height/5);
        }else {
            g.setColor(Color.GRAY);
            g.drawRect((3*width/7 + offset*width/8), height/20, width/9, height/5);
            Font fnt = new Font("arial", 1 , width/12);
            g.setFont(fnt);
            switch(offset){
                case 0:
                    g.drawString("♠", (3*width/7 + offset*width/8)+width/30, height/20 +height/8);
                    break;
                case 1:
                    g.drawString("♥", (3*width/7 + offset*width/8) +width/30, height/20 +height/8);
                    break;
                case 2:
                    g.drawString("♣", (3*width/7 + offset*width/8) +width/30, height/20 +height/8);
                    break;
                case 3:
                    g.drawString("♦", (3*width/7 + offset*width/8) +width/30, height/20 +height/8);
                    break;
            }
        }
    }
    /*
    Insert new card on top of the stack
    Starting from Ace, going upwards to King
    All cards have to be same color.
    */
    public boolean put(Card card) {
        if (card.color() != this.color) {
            return false;
        }
        
        if (this.size() + 1 == card.value()){  
            if(!this.isEmpty()){
                this.get().disappear();
            }
            this.cards.add(card);
            return true;
        }
        return false;
    }

    /*
    Removes card from the top of the stack
    @returns the removed card
    */
   public HeldDeck pop() { 
        if (this.isEmpty()) return null;
        HeldDeck deck = new HeldDeck();
        deck.push(this.cards.get(cards.size()-1));
        this.cards.remove(cards.size()-1);
        return deck;
        
    }
    /**
    * @return top card from the stack
    */
    public Card get() {
        if (this.isEmpty()) return null;
        Card card =  this.cards.get(cards.size()-1);   
        return card;
    }

    /*
    @returns card on index
    @param index index of the card
    @TODO is there actually any usage for this function?
    */
    public Card get(int index) {
        if (this.isEmpty() || index > this.size()-1) return null;
        Card card =  this.cards.get(index);        
        return card;
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
    
    public int size() {
        return this.cards.size();
    }
    
    /**
     * Returns color of the target pack
     * @return Card.color color
     */
    public Card.Color color(){
        return this.color;
    }
    public void setHint(){
        this.hint = true;
    }
    public void clearHint(){
        this.hint = false;
    }
    
    public void push(Card card){
        this.cards.add(card);
    }

}
