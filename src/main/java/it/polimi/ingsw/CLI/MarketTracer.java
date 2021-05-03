package it.polimi.ingsw.CLI;

import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.enumerations.ASCII_Marbles;

import java.util.ArrayList;

//A class that contains methods to trace the market board for the CLI
public class MarketTracer {
        Marble[][] marbles;


    /**
     * method that traces the market board for the CLI
     * @param marketBoard A matrix [3][4] of Marble elements
     * @return
     */
    public void marketTracer(MarketBoard marketBoard){
        ArrayList<String> result = new ArrayList<>();


        result.add("\n-------------# MARKET BOARD #-------------\n");

        marbles = marketBoard.getMarketBoardMarbles();

        //temporary arrays, every array contains a row of the matrix
        ArrayList<Marble> row1=new ArrayList<>();
        ArrayList<Marble> row2=new ArrayList<>();
        ArrayList<Marble> row3=new ArrayList<>();

        //Extracting the rows of the matrix of marbles
        for (int i=0; i<4; i++) {
            row1.add(marbles[0][i]);
            row2.add(marbles[1][i]);
            row3.add(marbles[2][i]);
        }

        ArrayList<ArrayList<Marble>> tempArrayList = new ArrayList();
        tempArrayList.add(row1);
        tempArrayList.add(row2);
        tempArrayList.add(row3);


        result.add("\n");
        result.add                ("\t  ╔═══➞═════════════➞══════╗  \n");
        result.add( String.format ("\t  ║                       %s║ \n", ASCII_Marbles.getShape(marketBoard.getExternalMarbleColor().toString()) ) );
        result.add                ("\t  ⬆    ╔═⏫════⏫═══⏫════⏫═╗        \n");


        //getting and adding the single marbles of a selected row to the string to be printed.
        for (int i=0; i<3; i++) {
            ArrayList<Marble> currentArray = tempArrayList.get(i)    ;
            String marble1 = ASCII_Marbles.getShape(currentArray.get(0).getColor().toString()).toString();
            String marble2 = ASCII_Marbles.getShape(currentArray.get(1).getColor().toString()).toString();
            String marble3 = ASCII_Marbles.getShape(currentArray.get(2).getColor().toString()).toString();
            String marble4 = ASCII_Marbles.getShape(currentArray.get(3).getColor().toString()).toString();

            result.add( String.format ("\t  ║   ⏪` %s | %s | %s | %s │ < %d    \n", marble1, marble2, marble3, marble4, i+1 ) );
        }

        result.add("\t  ⬆    ╚═══ ════ ═══ ════╝         \n" +
                   "\t         ^    ^   ^   ^           \n" +
                   "\t         1    2   3   4           \n");

/*
 *
 *
 *     ╔════════════════════════╗              ▀ ▀ ══➞══════▂▂▂▂▂▂➞══════╗
 *     ║                      ⬤║
 *     ║     ╔═⏫════⏫═══⏫═══⏫═╗
 *     ║     ⏪ ⬤ | ⬤ | ⬤ | ⬤ │ < 1
 *     ║     ⏪ ⬤ | ⬤ | ⬤ | ⬤ │ < 2
 *     ║     ⏪ ⬤ | ⬤ | ⬤ | ⬤ │ < 3
 *     ║     ╚═══ ════ ═══ ════╝
 *             ^    ^   ^   ^
 *             1    2   3   4
 *
 */


//TRACE
result.forEach(System.out::print);

row1.clear();
row2.clear();
row3.clear();
result.clear();

    }
}
