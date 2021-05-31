package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.parser.CardSlotParser;

import java.util.*;
import java.util.stream.Collectors;

public class DevCardSlots {

    private ArrayList<Stack<DevelopmentCard>> cardSlot;

    public DevCardSlots(){

        CardSlotParser parser = new CardSlotParser("src/main/java/it/polimi/ingsw/model/jsonFiles/CardSlotsWarehouse.json");
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

        System.out.println("IN: DevCardSlots/addCard():\n");

        if(slotNumber < 0 || slotNumber > (cardSlot.size()-1)) throw new IndexOutOfBoundsException();



        System.out.println(cardSlot.get(slotNumber));
        System.out.println(cardSlot.get(slotNumber).isEmpty());

        //if selected slot contains Cards
        if(!cardSlot.get(slotNumber).isEmpty()) {
            Level bottomCardLevel = cardSlot.get(slotNumber).peek().getLevel();
            Level upperCardLevel = developmentCard.getLevel();

            System.out.println("bottomCardLevel " + bottomCardLevel);
            System.out.println("upperCardLevel " + upperCardLevel);


            if (bottomCardLevel.equals(Level.FIRST) && upperCardLevel.equals(Level.SECOND))
                cardSlot.get(slotNumber).push(developmentCard);
            else if(bottomCardLevel.equals(Level.SECOND) && upperCardLevel.equals(Level.THIRD)) {
                cardSlot.get(slotNumber).push(developmentCard);
            }
            else {
                System.out.println("KO: INSERTION OF CARD WITH LOWER LEVEL ON ONE WITH HIGHER LEVEL, OR A CARD WITH HIGHER ON EMPTY SLOT");
                throw new IllegalInsertionException();
            }

        } else {
            //if selected slot is empty
            System.out.println("OK: PLAYER's selected slot is empty");
            if(developmentCard.getLevel().equals(Level.FIRST))
                cardSlot.get(slotNumber).push(developmentCard);
            else throw new IllegalInsertionException();
        }

    }

    public HashMap<Resource, Integer> resourcesProductionIn(int slotNumber) throws EmptySlotException, IndexOutOfBoundsException {
        if(slotNumber < 0 || slotNumber > (cardSlot.size()-1)) throw new IndexOutOfBoundsException();
        if(cardSlot.get(slotNumber).size() != 0){
            System.out.println(cardSlot.get(slotNumber).peek().getProductionIn());
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


    /**
     * @return List of Dev cards slot's peek cards:
     * - pos 0 first slot's peek card
     * - pos 1 second slot's peek card
     * - pos 2 third slot's peek card
     */
    public HashMap<Integer, DevelopmentCard> peekCards(){
        List<DevelopmentCard> peekCards = new ArrayList();
        HashMap<Integer, DevelopmentCard> cardsInSlot = new HashMap<>();
        for(int i = 0; i < cardSlot.size(); i++)
            if (!cardSlot.get(i).isEmpty())
                cardsInSlot.put(i, cardSlot.get(i).peek().clone());

        return cardsInSlot;
    }

    public List<Integer> slotsNumber(){
        List<Integer> slots = new ArrayList<>();
        for(int i = 0; i < cardSlot.size(); i++)
            if(!cardSlot.get(i).isEmpty())
                slots.add(i);

        return slots;
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
        return List.copyOf(cardsToReturn);
    }




}
