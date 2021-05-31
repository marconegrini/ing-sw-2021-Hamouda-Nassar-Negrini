package it.polimi.ingsw.model.devCardsDecks;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

public class Deck {

    private Stack<DevelopmentCard> deck;

    public Deck(){
        deck = new Stack<>();
    }

    public DevelopmentCard popCard(){
        return deck.pop();
    }

    public DevelopmentCard peekCard(){
        if(deck.isEmpty()){
            return null;
        }
        else return deck.peek();
    }

    /*public void removeCard(){
        deck.pop();
    }*/

    public void pushCard(DevelopmentCard card){
        deck.push(card);
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public boolean emptyDeck(){
        return deck.isEmpty();
    }

    public HashMap<Resource, Integer> getCardCost(){
        return (HashMap<Resource, Integer>) deck.peek().getCardCost().clone();
    }
}
