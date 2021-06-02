package it.polimi.ingsw.model;

import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.parser.DevelopmentCardParser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CardsDeckTest {
    private CardsDeck cardsDeck;
    private DevelopmentCardParser parser;

    @Before
    public void setUp(){
        cardsDeck = new CardsDeck();
    }

    @Test
    public void testDeckInitialization(){
        cardsDeck.initializeCardsDeck();
        DevelopmentCard card = cardsDeck.peekCard(0, 0);
        assertEquals(card.getLevel(), Level.FIRST);

        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 4; j++){
                while(!cardsDeck.emptyDeck(i, j)) {
                    card = cardsDeck.popCard(i, j);
                    if(i == 0) assertEquals(card.getLevel(), Level.FIRST);
                    if(i == 1) assertEquals(card.getLevel(), Level.SECOND);
                    if(i == 2) assertEquals(card.getLevel(), Level.THIRD);
                    if(j == 0) assertEquals(card.getColor(), CardColor.GREEN);
                    if(j == 1) assertEquals(card.getColor(), CardColor.BLUE);
                    if(j == 2) assertEquals(card.getColor(), CardColor.YELLOW);
                    if(j == 3) assertEquals(card.getColor(), CardColor.VIOLET);
                }
            }
        }
    }


}
