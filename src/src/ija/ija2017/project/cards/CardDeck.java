

package ija.ija2017.project.cards;

import ija.ija2017.project.klondike.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


/**
 * Represents deck of cards from which player takes new cards.
 * Only one card is face up at the time.
 * @author xhruba08
 */
public class CardDeck implements Serializable{
    private ArrayList<Card> cards;
    private boolean hint = false;
    /**
     * two types of working pack - maincarddeck(1) and discard deck(2)
     */
    private int id;
    
    public CardDeck(int id){
        cards = new ArrayList<>();
        this.id = id;
    }
    
   
    /**
     * Draws carddeck to the game window
     * @param g
     * @param width
     * @param height
     */
    public void draw(Graphics g, int width, int height){
        if(hint){     
            if(id == 1){
                g.drawImage(Assets.hint_arrow, width/11,-height/30 , width/30, height/10, null);               
            }else{
                g.drawImage(Assets.hint_arrow, width/4 - width/60,-height/30 , width/30, height/10, null);
            }
        }
        if (cards.size() >= 1){
            if(id ==1){                
                g.drawImage(Assets.back, width/20, height/20, width/9, height/5, null);
            }else  {
                this.get().turnFaceUp();
                this.get().draw(g, width/5, height/20, width/9, height/5, height/5);
            }
        }else {
            if (id ==1){
                g.setColor(Color.gray);
                g.drawRect(width/20, height/20, width/9, height/5);
                g.drawImage(Assets.arrow, width/13, height/10, width/15, height/10, null);
            }
        }
    }
    public int size() {
        return cards.size();
    }
    
    public boolean put(Card card) {
        return this.cards.add(card);         
    }

    public Card pop() {
        if (this.isEmpty()) return null;
        Card card =  this.cards.get(cards.size()-1);
        this.cards.remove(cards.size()-1);
        return card;
    }

    public Card get() {
        if (this.isEmpty()) return null;
        Card card =  this.cards.get(cards.size()-1);        
        return card;
    }

    public Card get(int index) {
        if (this.isEmpty() || index > this.size()-1) return null;
        Card card =  this.cards.get(index);        
        return card;
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }
    
    public void shuffle(){
        Collections.shuffle(cards, new Random(System.nanoTime()));
    }
    public void setHint(){
        this.hint = true;
    }
    public void clearHint(){
        this.hint = false;
    }
}
