

package ija.ija2017.project.klondike;

import ija.ija2017.project.cards.HeldDeck;

/**
 * Class represent one move
 * Moves are stored into array 
 * Used for undoing moves
 * @author xhruba08
 */
public class Command {
    /**
     * 1) maincarddeck
     * 2) othercarddeck
     * 11)targetC
     * 12)targetD
     * 13)targetH
     * 14)targetS
     * 21)work1
     * 22)work2
     * 23)work3
     * 24)work4
     * 25)work5
     * 26)work6
     * 27)work7
     */
    private int source = 0;
    private int target = 0;
    private int count = 0;
    
    
    private Object from;
    private Object to;
    
    private boolean turnedFaceUp = false;


    public boolean getTurned(){
        return this.turnedFaceUp;
    }
    public void setTurned(){
        this.turnedFaceUp = true;
    }
    public void setCount(int count){
       this.count = count;
    }
    public int getCount(){
        return this.count;
    }
   
    public Object getFrom(){
        return this.from;
    }
    public void setFrom(Object deck){
        this.from = deck;
    }
    public Object getTo(){
        return this.to;
    }
    public void setTo(Object deck){
        this.to = deck;
    }
    
    public void setSource(int source){
        this.source = source;
    }
    public int getSource(){
        return this.source;
    }
    public void setTarget(int target){
        this.target = target;
    }
    public int getTarget(){
        return this.target;
    }
}
