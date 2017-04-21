
package ija.ija2017.project.klondike;

import ija.ija2017.project.cards.Card;
import ija.ija2017.project.cards.CardDeck;
import ija.ija2017.project.cards.TargetPack;
import ija.ija2017.project.cards.WorkingPack;

/**
 *
 * @author xhruba08
 */
public class Factory{
    /*
    Creates  a single Card object
    all newly created cards are facing down on default
    @param color color of the card
    @param value value of the card
    @returns the new card object
    */
    public Card createCard(Card.Color color, int value) {
        if (value > 0 && value < 14)
            return new Card(color, value, false);
        else
            return null;
    }

    /*
    Creates new standard deck with 52 cards 
    Card are ordered by color and value, need to be shuffled before use
    @returns new CardDeck object
    */
    public CardDeck createCardDeck() {
        CardDeck cardDeck = new CardDeck(1);
        for (Card.Color c : Card.Color.values()){
             for (int i = 1; i <= 13; i++){
                 cardDeck.put(new Card(c,i,false));
             }
        }
        Launcher.log("New cardDeck created.");
        return cardDeck;
    }

    /*
    Creates new target pack of given color
    @param color color of the pack
    @returns new TargetPack object
    */
    public TargetPack createTargetPack(Card.Color color) {
        TargetPack targetPack = new TargetPack(color);  
        Launcher.log("New targetpack of "+color.toString()+" created.");
        return targetPack;
    }

 
    public WorkingPack createWorkingPack() {
        WorkingPack workingPack = new WorkingPack();
        Launcher.log("New workingpack created.");
        return workingPack;
    }

}
