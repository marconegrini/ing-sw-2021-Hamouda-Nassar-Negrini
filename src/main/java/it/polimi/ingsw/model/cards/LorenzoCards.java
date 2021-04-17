package it.polimi.ingsw.model.cards;
import it.polimi.ingsw.model.enumerations.LorenzoCardsTypes;
public class LorenzoCards {

    private final LorenzoCardsTypes cardType;


    public LorenzoCards(LorenzoCardsTypes cardType) {
        this.cardType = cardType;
    }

    public LorenzoCardsTypes getType(){return this.cardType;}


    public void onPool(){

        switch (cardType){

            case DISCARD2BLUEDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2BLUEDVCARDS);
                break;

            case DISCARD2GREENDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2GREENDVCARDS);
                break;

            case DISCARD2VIOLETDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2VIOLETDVCARDS);
                break;

            case DISCARD2YELLOWDVCARDS:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.DISCARD2YELLOWDVCARDS);
                break;

            case TWOFAITHPOINTSCARD:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.TWOFAITHPOINTSCARD);
                break;

            case FAITHANDSHUFFLECARD:
                System.out.println("Picked Up a "+ LorenzoCardsTypes.FAITHANDSHUFFLECARD);
                break;

        }

    }

}

