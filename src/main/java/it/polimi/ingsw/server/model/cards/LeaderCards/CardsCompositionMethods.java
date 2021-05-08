package it.polimi.ingsw.server.model.cards.LeaderCards;


import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.server.model.enumerations.CardColor;
import it.polimi.ingsw.server.model.enumerations.Level;

import java.util.*;


public class CardsCompositionMethods {
    private HashMap<LeaderCardCost, Integer> activationCost;

    public CardsCompositionMethods(HashMap<LeaderCardCost, Integer> activationCost) {
        this.activationCost = activationCost;
    }

    /**
     * @param cardsIn they are cards passed by the user to activate the leader card
     * @return true if the player have all the necessary DV cards to activate the leader cards otherwise returns false.
     */
    public boolean verifyToActivate(List<LeaderCardCost> cardsIn) throws NullPointerException, IndexOutOfBoundsException {

        //the number of cards passed mustn't be greater than 3
        if ((cardsIn.size()) > 3) {
            throw new IndexOutOfBoundsException("Index " + (cardsIn.size() - 1) + " is out of bounds!");
        }

        boolean result = false;
        HashMap<LeaderCardCost, Integer> inCardsOccurrences = cardsOccurrencesMethod(cardsIn);

        //Lambda expression
        Set<LeaderCardCost> actCardKeys = activationCost.keySet();
        Set<LeaderCardCost> inCardsOccurrencesKeys = inCardsOccurrences.keySet();
        int v1 = 0;
        int v2 = 0;
        int counter = 0;

        for (LeaderCardCost k1 : actCardKeys) {

            for (LeaderCardCost k2 : inCardsOccurrencesKeys) {

                v1 = activationCost.get(k1);
                v2 = inCardsOccurrences.get(k2);
                if (k1.getColor() == k2.getColor() && (k1.getLevel() == k2.getLevel() | k1.getLevel() == Level.ANY)) {

                    //in case the level is indifferent, will be considered the occurrences of all the cards of the same color of any level.
                    if (k1.getLevel() == Level.ANY) {

                        v2 = inCardsOccurrences.entrySet().stream()
                                .filter(x -> x.getKey().getColor().equals(k2.getColor()))
                                .mapToInt(Map.Entry::getValue)
                                .sum();
                    }

                    if (v1 > v2) {
                        result = false;
                    } else result = true;
                }
            }

            if (result == false) {
                break;
            }
        }


        return result;
    }

    /**
     * This method convert the arrayList coming from the personal slots of a player into a hashMap to be able to make the confront and verification to activate.
     *
     * @param cards an array of development cards to be put into a hashMap that have a key the class LeaderCardCost and as a value it have the number of occurrences.
     * @return a HashMap  that have the class LeaderCardCost that contains
     * the two attributes CardColor and Level, and as a value of respective (Color,Level) the number of
     * occurrences of this card color&Level values.
     */
    public HashMap<LeaderCardCost, Integer> cardsOccurrencesMethod(List<LeaderCardCost> cards) {
        /*
         *  cardsOccurrences: A hashMap that indicates the occurrences of each color of the cards of the activationCost.
         */
        HashMap<CardColor, Integer> cardsOccurrences = new HashMap<>();

        cardsOccurrences.put(CardColor.GREEN, 0);
        cardsOccurrences.put(CardColor.BLUE, 0);
        cardsOccurrences.put(CardColor.VIOLET, 0);
        cardsOccurrences.put(CardColor.YELLOW, 0);

        /*
         *   cardsOccurrencesReturn:A hashMap the contains as a key an object with the values (CardColor,Level) the are as a "primaryKey"
         *       and as a value the number of cards of a specific color&Level of the cards.
         */
        HashMap<LeaderCardCost, Integer> cardsOccurrencesReturn = new HashMap<>();

        //Do the control on each level separately-- and at the end combining all the data into cardsOccurrencesReturn
        for (Level currentLevel : Level.values()) {

            for (LeaderCardCost cardActivationCost : cards) {

                CardColor cardColor = cardActivationCost.getColor();
                Level cardLevel = cardActivationCost.getLevel();

                /*
                 *Lambda expression - Write into the temporary HashMap "cardsOccurrences" the occurrences of all the cardColor
                 *  of the cards and of a specific.
                 */
                cardsOccurrences.forEach((k, v) -> {
                    if (k == cardColor && cardActivationCost.getLevel() == currentLevel) {
                        v += 1;
                        cardsOccurrences.put(k, v);
                    }
                });
            }
            /*
             * for any Level add the information collected in the temporary HashMap "cardsOccurrences" into the "cardsOccurrencesReturn"
             *  with the info about the current Level.
             */
            cardsOccurrences.forEach((k, v) -> {
                if (v > 0)
                    cardsOccurrencesReturn.put(new LeaderCardCost(k, currentLevel), v);
            });


            cardsOccurrences.forEach((k, v) -> cardsOccurrences.replace(k, 0));
        }


        return cardsOccurrencesReturn;

    }


}
