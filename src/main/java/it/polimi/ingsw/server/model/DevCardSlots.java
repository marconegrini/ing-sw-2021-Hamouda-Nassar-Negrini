package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.server.model.enumerations.Level;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.EmptySlotException;
import it.polimi.ingsw.server.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.server.model.parser.CardSlotParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class DevCardSlots {

    private ArrayList<Stack<DevelopmentCard>> cardSlot;

    public DevCardSlots(){

        CardSlotParser parser = new CardSlotParser("src/main/java/it/polimi/ingsw/server/model/jsonFiles/CardSlotsWarehouse.json");
        Integer cardSlotNumber = parser.getCardSlotsNumber();

        cardSlot = new ArrayList<>();

        while(cardSlotNumber != 0){
            cardSlot.add(new Stack<>());
            cardSlotNumber--;
        }
    }

    /**
     *
     * @param slotNumber
     * @param developmentCard card to insert
     * @throws IllegalInsertionException if
     * @throws IndexOutOfBoundsException if slot number doesn't exists
     */

    public void addCard(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException, IndexOutOfBoundsException{

        if(slotNumber < 0 || slotNumber > (cardSlot.size()-1)) throw new IndexOutOfBoundsException();

        //if selected slot contains Cards
        if(!cardSlot.get(slotNumber).isEmpty()) {
            Level bottomCardLevel = cardSlot.get(slotNumber).peek().getLevel();
            Level upperCardLevel = developmentCard.getLevel();

            if (bottomCardLevel.equals(Level.FIRST) && upperCardLevel.equals(Level.SECOND))
                cardSlot.get(slotNumber).push(developmentCard);
            else if(bottomCardLevel.equals(Level.SECOND) && upperCardLevel.equals(Level.THIRD))
                cardSlot.get(slotNumber).push(developmentCard);
            else throw new IllegalInsertionException();

        } else {
            //if selected slot is empty
            if(developmentCard.getLevel().equals(Level.FIRST))
                cardSlot.get(slotNumber).push(developmentCard);
            else throw new IllegalInsertionException();
        }

    }

    public HashMap<Resource, Integer> resourcesProductionIn(int slotNumber) throws EmptySlotException, IndexOutOfBoundsException {
        if(slotNumber < 0 || slotNumber > (cardSlot.size()-1)) throw new IndexOutOfBoundsException();
        if(cardSlot.get(slotNumber).size() != 0){
            return (HashMap<Resource, Integer>) cardSlot.get(slotNumber).peek().getProductionIn().clone();
        } else throw new EmptySlotException();
    }

    public HashMap<Resource, Integer> resourcesProductionOut(int slotNumber){
        return (HashMap<Resource, Integer>) cardSlot.get(slotNumber).peek().getProductionOut().clone();
    }


    public int getVictoryPoints(){
        int result = 0;
        Stack<DevelopmentCard> serviceDeck = new Stack<DevelopmentCard>();
        DevelopmentCard developmentCard;

        for(Stack<DevelopmentCard> deck : cardSlot) {
            while (!deck.isEmpty()) {
                developmentCard = deck.pop();
                result += developmentCard.getVictoryPoints();
                serviceDeck.push(developmentCard);
            }
            while (!serviceDeck.isEmpty())
                deck.push(serviceDeck.pop());
        }

        return result;
    }

    public List<LeaderCardCost> peekCards(){
        List<DevelopmentCard> peekCards = new ArrayList();

        for(Stack<DevelopmentCard> deck : cardSlot){
            peekCards.add(deck.peek());
        }

        //List<DevelopmentCard> result = peekCards.stream().map(x -> new DevelopmentCard(x.getVictoryPoints(), x.getColor(), x.getLevel(), x.getCardCost(), x.getProductionIn(), x.getProductionOut())).collect(Collectors.toList());


        List<LeaderCardCost> result = peekCards.stream().map(x -> new LeaderCardCost(x.getColor(), x.getLevel())).collect(Collectors.toList());

        return result;
    }

    public List<DevelopmentCard> getCardsInSlots(){
        Stack<DevelopmentCard> temporaryDeck = new Stack();
        List<DevelopmentCard> cardsToReturn = new ArrayList();
        for(Stack stack : cardSlot){
            while(!stack.isEmpty()){
                DevelopmentCard dv = (DevelopmentCard) stack.pop();
                cardsToReturn.add(dv.clone());
                temporaryDeck.push(dv);
            }
            while(!temporaryDeck.isEmpty())
                stack.push(temporaryDeck.pop());
        }
        return cardsToReturn;
    }



}
