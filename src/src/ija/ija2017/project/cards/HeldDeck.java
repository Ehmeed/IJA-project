

package ija.ija2017.project.cards;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Represents stacks of cards or one card, held by player, when dragging them from one deck to another
 * @author xhruba08
 */
public class HeldDeck {
    /**
     * Position of mousepointer, updated when deck is held
     */
    private int x,y;
    
    /**
     * Arraylist holds all cards currently held by player cursor
     */
    private ArrayList<Card> cards;
    
    private Object from;
    /**
     * ID1 = other carddeck
     * ID2 = targetpack
     * ID3 = workingpack
     */
    private int id;
    
    public HeldDeck(){
        cards = new ArrayList<>();
        from = null;
        this.x = 0;
        this.y = 0;
        this.id = 0;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return this.id;
    }
    public Object getFrom(){
        return this.from;
    }
    public void setFrom(Object deck){
        this.from = deck;
    }
    public Card get(int index) {
        if (this.isEmpty() || index > this.size()-1) return null;
        Card card =  this.cards.get(index);        
        return card;
    }
    public boolean isEmpty(){
        return cards.isEmpty();
    }
    
    /**
     * Puts cards into deck
     * @param card Card added in deck.
     */
    public void push(Card card){
        this.cards.add(card);
    }
    /**
     * Removes all elements from the deck.
     * The deck will be empty after this call.
     */
    public void clear(){
        this.cards.clear();
    }
    /**
     * Counts number of cards in deck
     * @return number of cards
     */
    public int size(){
        return cards.size();
    }
    /**
     * Updates position to current position of cursor
     * @param x x coordinate
     * @param y y coordinate
     */
    public void updatePosition(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Draws held deck to position of cursor
     * @param g graphics object
     * @param width width of game window
     * @param height height of game window
     */
    public void draw(Graphics g, int width, int height){
        if(this.cards.size() > 0){
            for (int i = 0; i < cards.size(); i++){
                cards.get(i).draw(g, x - width/18, y + i*width/40 + -10, width/9, height/5, height/5);
            }
        }
    }
}
