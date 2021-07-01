package it.polimi.ingsw.model.multiplayer;

import it.polimi.ingsw.model.GameInstance;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.devCardsDecks.CardsDeck;
import it.polimi.ingsw.model.MarketBoard;
import it.polimi.ingsw.exceptions.MaxPlayersException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that extends game instance and overwrites methods for multiplayer game
 */
public class MultiPlayerGameInstance extends GameInstance {

    private static final Logger logger = Logger.getLogger(MultiPlayerGameInstance.class.getName());
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
            logger.log(Level.CONFIG,"\nPlayer: " + player.getNickname());
            player.printPlayer();
        }
    }
}
