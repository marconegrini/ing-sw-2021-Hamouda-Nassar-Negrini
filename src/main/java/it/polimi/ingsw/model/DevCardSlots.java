package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

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
        if(cardSlot.get(slotNumber).size() != 0) {
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
            return cardSlot.get(slotNumber).peek().getProductionIn();
        } else throw new EmptySlotException();
    }

    public HashMap<Resource, Integer> resourcesProductionOut(int slotNumber) throws EmptySlotException {
        if(slotNumber < 0 || slotNumber > 2) throw new IndexOutOfBoundsException();
        if(cardSlot.get(slotNumber).size() != 0){
            return cardSlot.get(slotNumber).peek().getProductionOut();
        } else throw new EmptySlotException();
    }

    public int getVictoryPoints(){
        int result = 0;
        for(int i=0; i<3; i++){
            while(cardSlot.get(i).size() != 0)
                result += cardSlot.get(i).pop().getVictoryPoints();
        }

        return result;
    }
}
