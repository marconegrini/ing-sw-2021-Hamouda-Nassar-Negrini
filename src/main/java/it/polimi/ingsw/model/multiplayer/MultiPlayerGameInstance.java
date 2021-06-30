package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.exceptions.MaxPlayersException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that extends game instance and overwrites methods for multiplayer game
 */
public class MultiPlayerGameInstance extends GameInstance {

    private List<MultiPlayer> players;

    public MultiPlayerGameInstance(){
        players = new ArrayList<>();
        this.cardsDeck = new CardsDeck();
        this.cardsDeck.initializeCardsDeck();
        this.marketBoard = new MarketBoard();
    }

    @Override
    public void addPlayer(Player player) throws MaxPlayersException {
        if(players.size() < 4) {
            players.add((MultiPlayer) player);
        } else throw new MaxPlayersException();
    }

    public List<MultiPlayer> getPlayer(){
        return this.players;
    }

    public Player getPlayer(Integer playerIndex){
        return players.get(playerIndex);
    }

    public void printGamePlayers(){
        for(MultiPlayer player : players){
            //System.out.println("\nPlayer: " + player.getNickname() + "\nUserId: " + player.getUserId());
            player.printPlayer();
        }
    }
}
