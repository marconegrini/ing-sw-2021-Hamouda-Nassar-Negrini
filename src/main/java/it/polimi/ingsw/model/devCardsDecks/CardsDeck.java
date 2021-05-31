package it.polimi.ingsw.model.devCardsDecks;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.parser.DevelopmentCardParser;

import java.util.*;

public class CardsDeck {

    private Deck[][] cardsDeck;

    public CardsDeck(){
        cardsDeck = new Deck[3][4];
        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 4; j++){
                //initializing development cards by row
                cardsDeck[i][j] = new Deck();
            }
    }

    public void initializeCardsDeck(){
        DevelopmentCardParser parser = new DevelopmentCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/DevCardJson.json");
        ArrayList<DevelopmentCard> deck = parser.getDevelopmentCardsDeck();
        parser.close();
        for(DevelopmentCard card : deck){
            if(card.getLevel().equals(Level.FIRST)){
                if(card.getColor().equals(CardColor.GREEN))
                    cardsDeck[0][0].pushCard(card);
                if(card.getColor().equals(CardColor.BLUE))
                    cardsDeck[0][1].pushCard(card);
                if(card.getColor().equals(CardColor.YELLOW))
                    cardsDeck[0][2].pushCard(card);
                if(card.getColor().equals(CardColor.VIOLET))
                    cardsDeck[0][3].pushCard(card);
            }
            if(card.getLevel().equals(Level.SECOND)){
                if(card.getColor().equals(CardColor.GREEN))
                    cardsDeck[1][0].pushCard(card);
                if(card.getColor().equals(CardColor.BLUE))
                    cardsDeck[1][1].pushCard(card);
                if(card.getColor().equals(CardColor.YELLOW))
                    cardsDeck[1][2].pushCard(card);
                if(card.getColor().equals(CardColor.VIOLET))
                    cardsDeck[1][3].pushCard(card);
            }
            if(card.getLevel().equals(Level.THIRD)){
                if(card.getColor().equals(CardColor.GREEN))
                    cardsDeck[2][0].pushCard(card);
                if(card.getColor().equals(CardColor.BLUE))
                    cardsDeck[2][1].pushCard(card);
                if(card.getColor().equals(CardColor.YELLOW))
                    cardsDeck[2][2].pushCard(card);
                if(card.getColor().equals(CardColor.VIOLET))
                    cardsDeck[2][3].pushCard(card);
            }
            for(int i = 0; i < 3; i++)
                for(int j = 0; j < 4; j++){
                    //initializing development cards by row
                    cardsDeck[i][j].shuffleDeck();
                }
        }
    }


    /**
     * @param row
     * @param column
     * @return a copy of the card in the specified position
     */
    public DevelopmentCard peekCard(int row, int column){
        if(cardsDeck[row][column].peekCard() == null){
            return new DevelopmentCard(0,null,null,null,null,null);
        }
        else
            return cardsDeck[row][column].peekCard();
    }

    /**
     * @param row
     * @param column
     * @return the card in the specified position. Differently from peekCard, popCard removes the specified card
     */
    public DevelopmentCard popCard(int row, int column){
        if(cardsDeck[row][column].peekCard() == null){
            return new DevelopmentCard(0,null,null,null,null,null);
        }
        else
            return cardsDeck[row][column].popCard();
    }

    public boolean emptyDeck(int row, int column){
        return cardsDeck[row][column].emptyDeck();
    }

    public List<Resource> developmentCardCost(int row, int column){
        HashMap<Resource, Integer> cost = cardsDeck[row][column].getCardCost();
        List<Resource> cardCost = new ArrayList<>();

        for(Resource resource : cost.keySet()){
            Integer value = cost.get(resource);
            for(int i = 0; i < value; i++){
                cardCost.add(resource);
            }
        }

        return cardCost;

    }


    public List<DevelopmentCard> peekRow(int row){
        ArrayList<DevelopmentCard> dvCardRow = new ArrayList<>();
        for (int column = 0; column < 4; column++)
            dvCardRow.add(peekCard(row,column));

        return List.copyOf(dvCardRow);
    }

    public ArrayList<DevelopmentCard> peekDecks(){
        ArrayList<DevelopmentCard> peekDecks = new ArrayList<>();
        for(int row = 0; row < 3; row++)
            peekDecks.addAll(peekRow(row));

        return peekDecks;
    }

}
