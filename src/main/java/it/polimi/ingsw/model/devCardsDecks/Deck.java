package it.polimi.ingsw.model.devCardsDecks;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.enumerations.Resource;

import java.util.Collections;
import java.util.HashMap;
import java.util.Stack;

/**
 * Class that contains development cards. This class is used by the development cards decks.
 */
public class Deck {

    private Stack<DevelopmentCard> deck;

    public Deck(){
        deck = new Stack<>();
    }

    /**
     * @return the first card on top of the deck. Removes the card from deck
     */
    public DevelopmentCard popCard(){
        return deck.pop();
    }

    /**
     * @return first card on top of the deck. Keeps the card on the deck
     */
    public DevelopmentCard peekCard(){
        if(deck.isEmpty()){
            return null;
        }
        else return deck.peek();
    }

    /**
     * Insert a card in the deck
     * @param card
     */
    public void pushCard(DevelopmentCard card){
        deck.push(card);
    }

    /**
     * shuffles the deck
     */
    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    /**
     * @return true if the deck is empty, false otherwise
     */
    public boolean isEmptyDeck(){
        return deck.isEmpty();
    }

    /**
     * @return an hashmap containing the cost of the top card in the deck. Needed when the user wants to buy
     * the development card and has to check wether the cost is affordable or not.
     */
    public HashMap<Resource, Integer> getCardCost(){
        return (HashMap<Resource, Integer>) deck.peek().getCardCost().clone();
    }
}
