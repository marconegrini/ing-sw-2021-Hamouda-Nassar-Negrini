package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.*;

public class MarketBoard {

    private Marble[][] marbles;
    private Marble externalMarble;


    public MarketBoard(){
        marbles = new Marble[3][4];
        setupMarketBoard();
    }


    /**
     *
     * Setting up the market board. The method put the marbles inside
     * a stack, the it shuffle that stack and pop all the marbles
     * inside marbles[][]
     *
     */

    private void setupMarketBoard(){

        Stack<Marble> marbleStack= new Stack<>();
       // Adding the 4 white marbles
        for (int i=0; i<4; i++){
            marbleStack.push(new Marble(Color.WHITE));
        }

        // Adding the 2 blue marbels
        for (int i=0; i<2; i++){
            marbleStack.push(new Marble(Color.BLUE));
        }

        // Adding the 2 grey marbels
        for (int i=0; i<2; i++){
            marbleStack.push(new Marble(Color.GREY));
        }

        // Adding the 2 yellow marbels
        for (int i=0; i<2; i++){
            marbleStack.push(new Marble(Color.YELLOW));
        }

        // Adding the 2 violet marbels
        for (int i=0; i<2; i++){
            marbleStack.push(new Marble(Color.VIOLET));
        }

        //Adding the red marble
        marbleStack.push(new Marble(Color.RED));

        //Shuffling the stack
        Collections.shuffle(marbleStack);

        //putting the marbles inside marbles[][]
        for (int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                marbles[i][j] = marbleStack.pop();
            }
        }

        externalMarble = marbleStack.pop();


    }

    /**
     *
     * Returns an ArrayList of Marbles of the selected row or column,
     * insert a marble in a row or a column, moves the other
     * marbles of one position and changes the external marbles.
     *
     * @param row  the row indicate if the parameter rowOrColNum refers to a row or a column
     * @param rowOrColNum  the rowOrColNum indicate the column or the row selected
     * @return  an ArrayList with the picked marbles
     */
    public List<Marble> insertMarble(boolean row, int rowOrColNum){

        Marble temporaryMarble;
        List<Marble> pickedMarbles;
        //ArrayList<Resource> pickedResources;

        if (row){
            pickedMarbles = new ArrayList<>(Arrays.asList(marbles[rowOrColNum])); //Create the arraylist with the entire row
            temporaryMarble = marbles[rowOrColNum][3];
            for (int i=3; i>0; i--){
                marbles[rowOrColNum][i] = marbles[rowOrColNum][i-1];
            }
            marbles[rowOrColNum][0] = externalMarble;
            externalMarble = temporaryMarble;
        } else {
            pickedMarbles = new ArrayList<>();
            temporaryMarble = marbles[0][rowOrColNum];
            for (int i=0; i<2; i++){
                pickedMarbles.add(marbles[i][rowOrColNum]);
                marbles[i][rowOrColNum] = marbles[i+1][rowOrColNum];
            }
            pickedMarbles.add(marbles[2][rowOrColNum]);
            marbles[2][rowOrColNum] = externalMarble;
            externalMarble = temporaryMarble;
        }

        //pickedResources = fromMarblesToResources(pickedMarbles);

        return pickedMarbles;
    }

    public Marble[][] getMarketBoardMarbles () {return marbles.clone();}

    public Color getExternalMarbleColor () {return externalMarble.getColor();}
}
