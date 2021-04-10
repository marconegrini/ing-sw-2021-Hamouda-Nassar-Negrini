package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.enumerations.LorenzoCardsType;

public class LorenzoCards {

    private LorenzoCardsType cardType;


    public LorenzoCards(LorenzoCardsType cardType){
        this.cardType=cardType;
    }


    public void onPool(){

    switch (cardType){

        case DISCARD2BLUEDVCARDS:

            break;

        case DISCARD2GREENDVCARDS:

            break;

        case DISCARD2VIOLETDVCARDS:

            break;

        case DISCARD2YELLOWDVCARDS:

            break;

        case TWOFAITHPOINTSCARD:

            break;

        case FAITHANDSHUFFLECARD:

            break;

    }

    }
}
