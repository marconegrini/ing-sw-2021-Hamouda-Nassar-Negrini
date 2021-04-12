package it.polimi.ingsw.model.devCardsDecks;

import it.polimi.ingsw.model.cards.DevelopmentCard;

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
        return deck.peek();
    }

    public void removeCard(){
        deck.pop();
    }
}
