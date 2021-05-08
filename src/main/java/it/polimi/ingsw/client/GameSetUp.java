package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCards.StorageLeaderCard;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameSetUp {

    public void initialSetUp(Socket socket, Scanner scanner, DataInputStream fromServer, DataOutputStream toServer, BufferedReader buffer) {
        try {
            List<String> cards = new ArrayList<>();
            List<LeaderCard> leaderCards = new ArrayList();

            //TODO update leader cards in order to work with json files
            System.out.println("Your Leader Cards: ");
            while (true) {
                try {
                    String leaderCardType = fromServer.readUTF();
                    if(leaderCardType.equals("OK")) break;
                    String leaderCardJson = fromServer.readUTF();
                    //System.out.println(leaderCardJson);



                    Gson gson = new Gson();
                    if(leaderCardType.equals("STORAGE")) {
                        StorageLeaderCard slc = gson.fromJson(leaderCardJson, StorageLeaderCard.class);
                        leaderCards.add(slc);
                    }
                    /*
                    if(leaderCardType.equals("DISCOUNT")) {
                        DiscountLeaderCard dlc = gson.fromJson(leaderCardJson, DiscountLeaderCard.class);
                        leaderCards.add(dlc);
                    }
                    if(leaderCardType.equals("MARBLE")) {
                        WhiteMarbleLeaderCard mlc = gson.fromJson(leaderCardJson, WhiteMarbleLeaderCard.class);
                        leaderCards.add(mlc);
                    }
                    if(leaderCardType.equals("PRODUCTION")) {
                        ProdPowerLeaderCard plc = gson.fromJson(leaderCardJson, ProdPowerLeaderCard.class);
                        leaderCards.add(plc);
                    }

                     */

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for(LeaderCard lc : leaderCards)
                System.out.println(lc.toString());

            //sending to server selected leader cards

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
