package it.polimi.ingsw.server.model.multiplayer;

import it.polimi.ingsw.server.model.GameInstance;
import it.polimi.ingsw.server.model.MarketBoard;
import it.polimi.ingsw.server.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.server.model.exceptions.MaxPlayersException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
