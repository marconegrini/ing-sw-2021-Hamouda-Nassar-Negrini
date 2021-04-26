package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

public class DevCardSlots {

    private ArrayList<Stack<DevelopmentCard>> cardSlot;

    public DevCardSlots(){
        cardSlot = new ArrayList<>();
        cardSlot.add(new Stack<>());
        cardSlot.add(new Stack<>());
        cardSlot.add(new Stack<>());
    }

    public void addCard(int slotNumber, DevelopmentCard developmentCard) throws IllegalInsertionException{

        if(slotNumber < 0 || slotNumber > 2) throw new IndexOutOfBoundsException();

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

    public HashMap<Resource, Integer> resourcesProductionIn(int slotNumber) throws EmptySlotException {
        if(slotNumber < 0 || slotNumber > 2) throw new IndexOutOfBoundsException();
        if(cardSlot.get(slotNumber).size() != 0){
            return (HashMap<Resource, Integer>) cardSlot.get(slotNumber).peek().getProductionIn().clone();
        } else throw new EmptySlotException();
    }

    public HashMap<Resource, Integer> resourcesProductionOut(int slotNumber) throws EmptySlotException {
        if(slotNumber < 0 || slotNumber > 2) throw new IndexOutOfBoundsException();
        if(!cardSlot.get(slotNumber).isEmpty()){
            return (HashMap<Resource, Integer>) cardSlot.get(slotNumber).peek().getProductionOut().clone();
        } else throw new EmptySlotException();
    }


    public int getVictoryPoints(){
        int result = 0;
        Stack serviceDeck = new Stack();
        DevelopmentCard developmentCard;

        for(Stack deck : cardSlot) {
            while (!deck.isEmpty()) {
                developmentCard = (DevelopmentCard) deck.pop();
                result += developmentCard.getVictoryPoints();
                serviceDeck.push(developmentCard);
            }
            while (!serviceDeck.isEmpty())
                deck.push(serviceDeck.pop());
        }

        return result;
    }

    public List<DevelopmentCard> peekCards(){
        List<DevelopmentCard> peekCards = new ArrayList<DevelopmentCard>();
        for(Stack deck : cardSlot){
            peekCards.add((DevelopmentCard) deck.peek());
        }

        List<DevelopmentCard> result = peekCards.stream().map(x -> new DevelopmentCard(x.getVictoryPoints(), x.getColor(), x.getLevel(), x.getCardCost(), x.getProductionIn(), x.getProductionOut())).collect(Collectors.toList());
        /*
        oppure
        List<LeaderCardCost> cardCosts = peekCards.stream().map(x -> new LeaderCardCost(x.getColor(), x.getLevel())).collect(Collectors.toList());

         */

        return (ArrayList<DevelopmentCard>) result;
    }



}
