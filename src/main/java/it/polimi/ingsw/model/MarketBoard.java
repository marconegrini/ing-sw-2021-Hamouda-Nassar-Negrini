package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.ASCII_Marbles;
import it.polimi.ingsw.model.enumerations.Color;

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
     * a stack, then it shuffle that stack and pop all the marbles
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
     * Returns an ArrayList of Marbles of the selected isRow or column,
     * insert a marble in a isRow or a column, moves the other
     * marbles of one position and changes the external marbles.
     *
     * @param isRow  the isRow indicate if the parameter rowOrColNum refers to a row or a column
     * @param rowOrColNum  the rowOrColNum indicate the column or the row selected
     * @return  an ArrayList with the picked marbles
     */
    public List<Marble> insertMarble(boolean isRow, int rowOrColNum) throws IndexOutOfBoundsException{

        Marble temporaryMarble;
        List<Marble> pickedMarbles;

        rowOrColNum = rowOrColNum > 0 ? rowOrColNum-1 : rowOrColNum; //Convention Start from 1 instead of 0, if 0 leave it as it is

        //ArrayList<Resource> pickedResources;

        if (isRow){
            if (rowOrColNum>2){
                throw new IndexOutOfBoundsException("Number of rows inserted is greater than 3 !");
            }

            pickedMarbles = new ArrayList<>(Arrays.asList(marbles[rowOrColNum])); //Create the arraylist with the entire row
            temporaryMarble = marbles[rowOrColNum][0];
            for (int i=0; i<3; i++){
                marbles[rowOrColNum][i] = marbles[rowOrColNum][i+1];
            }
            marbles[rowOrColNum][3] = externalMarble;
            externalMarble = temporaryMarble;
        } else {
            if (rowOrColNum>3){
                throw new IndexOutOfBoundsException("Number of columns inserted is greater than 4 !");
            }

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
        for(Marble marble : pickedMarbles)
            System.out.println(marble.toString());
        return pickedMarbles;
    }

    /**
     * @return a copy of marbles contained in market
     */
    public Marble[][] getMarketBoardMarbles () {return marbles.clone();}

    public Color getExternalMarbleColor () {return externalMarble.getColor();}

    /*
    public List<Marble> getRowOrColumnMarbles(boolean isRow, Integer rowOrColNum){
        List<Marble> marblesToReturn = new ArrayList<>();
        if(isRow){
            for(int i = 0; i < 4; i++)
                marblesToReturn.add(marbles[rowOrColNum][i]);
        } else {
            for(int i = 0; i < 3; i++)
                marblesToReturn.add(marbles[i][rowOrColNum]);
        }
        return marblesToReturn;
    }
    */

    /*
        //for testing purpose only
        public static void main(String[] args) {
            MarketBoard market = new MarketBoard();

            market.printm(market);
            System.out.println(market.getExternalMarbleColor());
            market.insertMarble(false,1);
            market.printm(market);
            System.out.println(market.getExternalMarbleColor());
        }

        public void printm(MarketBoard market){
            for (int i=0; i<3; i++){
                for (int j=0; j<4; j++){
                    System.out.print(ASCII_Marbles.getShape(market.getMarketBoardMarbles()[i][j].getColor().toString())+" ");
                }
                System.out.println("\n");
            }
        }

     */


}
