package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.FaithPath;
import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.exceptions.MaxPlayersException;
import it.polimi.ingsw.model.multiplayer.MultiPlayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MultiPlayerGameInstance extends GameInstance {

    private List<MultiPlayer> players;

    public MultiPlayerGameInstance(Integer gameId){
        this.gameId = gameId;
        players = new ArrayList<>();
        this.cardsDeck = new CardsDeck();
        this.marketBoard = new MarketBoard();
    }

    @Override
    public void addPlayer(String nickname, Integer userId, DataOutputStream dos, DataInputStream dis) throws MaxPlayersException {

        if(players.size() <= 4) {
            players.add(new MultiPlayer(nickname, userId, dos, dis));
        } else throw new MaxPlayersException();
    }

    @Override
    public Integer getGameId() {
        return this.gameId;
    }

    public List<MultiPlayer> getPlayer(){
        return this.players;
    }

    public void printGamePlayers(){
        for(MultiPlayer player : players){
            System.out.println("\nPlayer: " + player.getNickname() + "\nUserId: " + player.getUserId());
            player.printPlayer();
        }
    }
}
