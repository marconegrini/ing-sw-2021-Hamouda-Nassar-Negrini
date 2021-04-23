package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MarketBoard {

    private Marble[][] marbles;
    private Marble externalMarble;


    public MarketBoard(){
        marbles = new Marble[3][4];
        externalMarble = new Marble(Color.RED);
        setupMarketBoard();
        shuffleMarketBoard();
    }


    /*
     *
     * Setting up the market board putting the colored marbles
     *
     */

    private void setupMarketBoard(){

       // Adding the 4 white marbles
        for (int i=0; i<4; i++){
            marbles[0][i] = new Marble(Color.WHITE);
        }

        // Adding the 2 blue marbels
        for (int i=0; i<2; i++){
            marbles[1][i] = new Marble(Color.BLUE);
        }

        // Adding the 2 grey marbels
        for (int i=2; i<4; i++){
            marbles[1][i] = new Marble(Color.GREY);
        }

        // Adding the 2 yellow marbels
        for (int i=0; i<2; i++){
            marbles[2][i] = new Marble(Color.YELLOW);
        }

        // Adding the 2 violet marbels
        for (int i=2; i<4; i++){
            marbles[2][i] = new Marble(Color.VIOLET);
        }
    }

    /*
     *
     * Returns an ArrayList of Marbles of the selected row or column,
     * insert a marble in a row or a column, moves the other
     * marbles of one position and changes the external marbles.
     *
     */
    public ArrayList<Marble> insertMarble(boolean row, int rowOrColNum){

        Marble temporaryMarble;
        ArrayList<Marble> pickedResources;

        if (row){
            pickedResources = new ArrayList<>(Arrays.asList(marbles[rowOrColNum])); //Create the arraylist with the entire row
            temporaryMarble = marbles[rowOrColNum][3];
            for (int i=3; i>0; i--){
                marbles[rowOrColNum][i] = marbles[rowOrColNum][i-1];
            }
            marbles[rowOrColNum][0] = externalMarble;
            externalMarble = temporaryMarble;
        } else {
            pickedResources = new ArrayList<>();
            temporaryMarble = marbles[0][rowOrColNum];
            for (int i=0; i<2; i++){
                pickedResources.add(marbles[i][rowOrColNum]);
                marbles[i][rowOrColNum] = marbles[i+1][rowOrColNum];
            }
            pickedResources.add(marbles[2][rowOrColNum]);
            marbles[2][rowOrColNum] = externalMarble;
            externalMarble = temporaryMarble;
        }

        return pickedResources;
    }

    public Marble[][] getMarketBoard (){return marbles.clone();}

    /*
     *
     * This method use the insertMarble() to shuffle the marketBoard.
     * It generate a random number (maximum 20) that will be use to shuffle the
     * marketBoard.
     *
     */
    public void shuffleMarketBoard(){

        Random random = new Random();
        System.out.println("Inside market board shuffle");
        for (int i=0; i< (int)Math.floor(Math.random()*(20-5+1)+5); i++){
            if (i%2 == 0)   insertMarble(true, random.nextInt(3));
            else            insertMarble(false, random.nextInt(4));
         }

    }

}
