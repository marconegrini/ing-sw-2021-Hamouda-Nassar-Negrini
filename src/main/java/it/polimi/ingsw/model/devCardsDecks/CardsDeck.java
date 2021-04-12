package it.polimi.ingsw.model.devCardsDecks;

public class CardsDeck {

    private Deck[][] cardsDeck;

    public CardsDeck(){
        for(int i = 0; i < 3; i++)
            //initializing development cards by row
            for(int j = 0; j < 4; j++){
                cardsDeck[i][j] = new Deck();
            }
    }

    public void initializeCardsDeck(String jsonInformationForDevCard){
        //TODO
    }
}
