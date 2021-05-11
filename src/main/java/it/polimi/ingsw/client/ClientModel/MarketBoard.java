package it.polimi.ingsw.client.ClientModel;

import it.polimi.ingsw.client.ClientModel.Marble;
import it.polimi.ingsw.server.model.enumerations.ASCII_Marbles;
import it.polimi.ingsw.server.model.enumerations.Color;

import java.util.*;

public class MarketBoard {

    private Marble[][] marbles;
    private Marble externalMarble;


    public MarketBoard(){
        marbles = new Marble[3][4];

    }


    public Marble[][] getMarketBoardMarbles () {return marbles.clone();}

    public Color getExternalMarbleColor () {return externalMarble.getColor();}


        //for testing purpose only
        public static void main(String[] args) {

        }


}
