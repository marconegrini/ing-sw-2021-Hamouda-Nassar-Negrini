package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.server.model.MarketBoard;
import it.polimi.ingsw.server.model.cards.DevelopmentCard;
import it.polimi.ingsw.server.model.enumerations.CardColor;
import it.polimi.ingsw.server.model.enumerations.Level;
import it.polimi.ingsw.server.model.enumerations.Resource;


import java.util.*;

public class ClientCLI {

    public String initialScreen(){

        String nickName= "";
        String serverIp= "";
        String serverPort= "";
        String multiOrSingle= "";

        Scanner sc=new Scanner(System.in);
        System.out.println("\tEnter your Nick Name: ");
        nickName = sc.nextLine();
        System.out.println("\tEnter server Ip: ");
        serverIp = sc.nextLine();
        System.out.println("\tEnter server port: ");
        serverPort = sc.nextLine();
        System.out.println("\tChoose game type: \n1.Multiplayer game \n2.Single player game ");
        multiOrSingle = sc.nextLine();

        if (multiOrSingle.equals("1")){
            multiOrSingle="MultiPlayer";
        }else if (multiOrSingle.equals("2"))
            multiOrSingle="SinglePlayer";

        return nickName + "," + serverIp + "," + serverPort + "," + multiOrSingle;
    }





    public String multiPlayerScreen(){


return null;
    }

    public String singlePlayerScreen(){

        System.out.println("\n--------------------------------------------------------------------------------------\n");

return null;
    }







    public void printFaithPath(String player1,int pos1){

        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        str1.append(" .1 .2 .3 .4 .5 .6 .7 .8 .9 .10 .11 .12 .13 .14 .15 .16 .17 .18 .19 .20 .21 .22 .23 .24");
        str1.append(" .1 .2 ^ADEL^  .4 .5 .6 .7 .8 .9 .10 .11 .12 .13 .14 .15 .16 .17 .18 .19 .20 .21 .22 .23 .24");

        str2.append("                                                                                         ");

        System.out.printf("%2s %2s %2s %2s %2s %2s %2s %2s 2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s %2s",
                ".1", ".2", ".3", ".4", ".5", ".6", ".7", ".8", ".9" ,".10" ,".11" ,".12" ,".13" ,".14" ,".15" ,".16" ,".17" ,".18" ,".19" ,".20" ,".21" ,".22" ,".23" ,".24");

    }


    //ONLY FOR TESTING PURPOSE
    public static void main(String[] args) {
        ClientCLI clientCLI = new ClientCLI();

         HashMap<Resource, Integer> productionIn = new HashMap<>();
         productionIn.put(Resource.COIN,5);
         HashMap<Resource, Integer> productionOut = new HashMap<>();
         productionOut.put(Resource.COIN,5);
        HashMap<Resource, Integer> cardCost = new HashMap<>();
        cardCost.put(Resource.COIN,5);
        DevelopmentCard dvCard = new DevelopmentCard(11,CardColor.GREEN,Level.SECOND,cardCost,productionIn,productionOut);


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
