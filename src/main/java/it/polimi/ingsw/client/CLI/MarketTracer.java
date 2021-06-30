package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.enumerations.*;
import it.polimi.ingsw.model.Marble;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that contains methods to trace the market board for the CLI
 */
public class MarketTracer{
        Marble[][] marbles;


    /**
     * method that traces the market board for the CLI
     * @param marketBoard A matrix [3][4] of Marble elements
     * @return an arraylist containing the market board traced as characters
     */
    public ArrayList<String> marketTracer(MarketBoard marketBoard){
        ArrayList<String> result = new ArrayList<>();


        result.add(ANSITextFormat.BOLD_ITALIC +"\n-------------# MARKET BOARD #-------------\n"+ ANSITextFormat.RESET);

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
        result.add( String.format ("\t  ║                       %s〙 \n", ASCII_Marbles.getShape(marketBoard.getExternalMarbleColor().toString()) ) );
        result.add                ("\t  ⬆    ╔═ ════ ═══ ════ ═╗        \n");


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
 *         result.add                ("\t  ⬆    ╔═⏫════⏫═══⏫════⏫═╗        \n");

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
return result;

    }

    //ONLY FOR TESTING PURPOSE
    public static void main(String[] args) {

        HashMap<Resource, Integer> productionIn = new HashMap<>();
        productionIn.put(Resource.COIN,5);
        HashMap<Resource, Integer> productionOut = new HashMap<>();
        productionOut.put(Resource.COIN,5);
        HashMap<Resource, Integer> cardCost = new HashMap<>();
        cardCost.put(Resource.COIN,5);
        DevelopmentCard dvCard = new DevelopmentCard(11, CardColor.GREEN, Level.SECOND,cardCost,productionIn,productionOut);


//        System.out.println("⬤ ⬤ ⬤ ⬤");
//        System.out.println("⬤ ⬤ ⬤ ⬤");
//        System.out.println("⬤ ⬤ ⬤ ⬤");

        MarketBoard market = new MarketBoard();
        MarketTracer marketTracer = new MarketTracer();

        marketTracer.marketTracer(market);
        market.insertMarble(true,1);
        marketTracer.marketTracer(market);
        market.insertMarble(false,3);
        marketTracer.marketTracer(market);


//        System.out.println("    ◤ ⏫ —  ⏫ — ⏫ — ⏫ ◥        \n" +
//                           "   ⏪` ⬤ | ⬤ | ⬤ | ⬤ | < 1    \n" +
//                           "   ⏪` ⬤ | ⬤ | ⬤ | ⬤ | < 2    \n" +
//                           "   ⏪` ⬤ | ⬤ | ⬤ | ⬤ | < 3    \n" +
//                           "    ◣ ——— ——— ——— ———◢         \n" +
//                           "      ^    ^   ^   ^           \n" +
//                           "      1    2   3   4          ");


    }


}
