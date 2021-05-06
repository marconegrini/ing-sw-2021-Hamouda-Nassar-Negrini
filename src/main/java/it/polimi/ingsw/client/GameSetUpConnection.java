package it.polimi.ingsw.client;

import com.google.gson.Gson;
import it.polimi.ingsw.controller.Message;
import it.polimi.ingsw.model.cards.LeaderCard;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameSetUpConnection extends Connection{

    public GameSetUpConnection(Socket socket) throws IOException {
        super(socket);
        this.chooseLeaderCards();
        this.checkCalamaio();
        this.closeConnection();
    }

    public void chooseLeaderCards(){
        try {
            boolean received = false;
            String message = fromServer.readUTF();
            List<LeaderCard> leaderCardsDeck = new ArrayList();
            while(!received){
                String leaderCard = fromServer.readUTF();
                if(!leaderCard.equals("OK")){
                    Gson gson = new Gson();
                    LeaderCard lc = gson.fromJson(leaderCard, LeaderCard.class);
                    leaderCardsDeck.add(lc);
                } else received = true;
            }
        } catch(IOException e){
            System.out.println("Exception occurred while receiving file");
        }
    }

    public void checkCalamaio(){

    }


}
