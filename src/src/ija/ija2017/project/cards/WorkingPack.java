
package ija.ija2017.project.cards;



import ija.ija2017.project.klondike.Assets;
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author xhruba08
 */
public class WorkingPack implements Serializable{
    private ArrayList<Card> cards;
    private boolean hint = false;
    
    public WorkingPack(){
        this.cards = new ArrayList<>();
    }
    /**
     * draws working pack
     * @param g Graphics
     * @param width width of the game window
     * @param height height of the game window
     * @param offset offset of drawn pack (from 0 to 6)
     */
    public void draw(Graphics g, int width, int height, int offset){
        if(hint){
            g.drawImage(Assets.hint_arrow, offset*width/8 + 2*width/19, 6*height/23, width/30, height/10, null);            
        }
        if (this.isEmpty()){
            g.setColor(Color.gray);
            g.drawRect(offset*width/8 + width/15, height/3, width/9, height/5);
        }else {
            for(int i = 0; i < cards.size(); i++){
                int hitbox;
                if(i < cards.size() - 1){
                    hitbox = height/25;
                }else{
                    hitbox = height/5;
                }
                cards.get(i).draw(g, offset*width/8 + width/15, height/3 + i*width/40, width/9, height/5, hitbox);
            }
        }
    
    }
    /**
    Puts HeldDeck given as parametr on top of working pack
    @param deck 
    @return true on success, false when card cannot be put into the pack
    */
    public boolean put(HeldDeck deck) {
        if (deck == null) return false;
        int i = 0;
        while(i<deck.size()){
            Card card = deck.get(i);
            // this check can only fail on the first card
            // so no need to care about taking the cards out
            if(!this.put(card)) {                
                return false;
            }
            i++;
        }/*
        while(!deck.isEmpty()){
            deck.pop();
        }*/
        return true;
    }

    /**
    Takes cards from top of working pack to given card and returns
    them as helddeck (including the card given as parametr)
    @param card card to be taken to
    @return taken carddeck, null if the card isn't in the deck
    */
    public HeldDeck pop(Card card) {
        HeldDeck takenStack = new HeldDeck();
        int i;
        for (i = this.size()-1; i >= 0; i--){
            if(this.cards.get(i).equals(card)){
                if(!this.cards.get(i).isTurnedFaceUp()){
                    return null;
                }
                break;
            }
        }
        // given card wasn't found, returning null
        if (i == -1) return null;
        
        
        while (this.size() > i){
           takenStack.push(this.cards.get(i));
           this.cards.remove(i);
        }
        return takenStack;
    }

    /**
     *  Calculates number of cards in the deck
     * @return size of the deck 
     */
    public int size() {
        return this.cards.size();
    }


    public boolean put(Card card) {
        if (this.isEmpty() && card.value() == 13){
            this.cards.add(card);
            return true;
        } else if (this.isEmpty()){
            return false;       
        } else if (!this.get().similarColorTo(card)&& this.get().value()-1 == card.value()){ // && this.size() < 13  REMOVED
            this.cards.add(card);
            return true;
        }
        return false;
    }


    public Card pop() {
        if (this.cards.isEmpty()) return null;
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
    public void turnTopFaceDown(){
        if(!this.isEmpty()){
            this.get().turnFaceDown();
        }
    }
    /**
     * Find cards that's highest placed in the deck and is facing up
     * @return null when card not found, otherwise the found card
     */
    public Card getTopFacingUp(){
        Card top = null;
        for(int i =cards.size()-1; i >= 0; i--){
            if(cards.get(i).isTurnedFaceUp()){
                top = cards.get(i);
            }else{
                break;
            }
        }
        return top;
    }
    
    /*
    * Puts card on top of the working pack
    * Only used when dealing cards -> standard rules don't apply, 
    * any card can be put in
    */
    public void push(Card card){
        this.cards.add(card);
    }
    public void setHint(){
        this.hint = true;
    }
    public void clearHint(){
        this.hint = false;
    }

}
