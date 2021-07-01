package it.polimi.ingsw.server.handlers;

import it.polimi.ingsw.enumerations.ANSITextFormat;
import it.polimi.ingsw.messages.fromServer.*;
import it.polimi.ingsw.messages.fromServer.update.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LorenzoCard;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.LorenzoCardType;
import it.polimi.ingsw.model.parser.LeaderCardParser;
import it.polimi.ingsw.model.singleplayer.SinglePlayer;
import it.polimi.ingsw.model.singleplayer.SinglePlayerGameInstance;
import it.polimi.ingsw.server.controller.TurnManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class SinglePlayerGameHandler extends Thread {

    private static final Logger logger = Logger.getLogger(SinglePlayerGameHandler.class.getName());
    private ClientHandler clientHandler;
    private TurnManager turnManager;
    private final SinglePlayerGameInstance game;
    private Stack<LorenzoCard> actionCards;
    private Stack<LorenzoCard> poppedActionsCard;
    private SinglePlayer player;
    boolean gameEnded = false;

    /**
     * @param clientHandler
     */
    public SinglePlayerGameHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        game = new SinglePlayerGameInstance();
        turnManager = new TurnManager(game.getCardsDeck(), game.getMarketBoard());
        turnManager.setMultiplayer(false);
        SinglePlayer player = new SinglePlayer(clientHandler.getNickname());
        turnManager.setPlayer(player);
        game.addPlayer(player);
        clientHandler.setPlayer(player);
        clientHandler.setTurnManager(turnManager);
        this.player = player;
        this.actionCards = player.getActionCards();
        this.poppedActionsCard = player.getPoppedLorenzosCard();
    }

    /**
     * administers game logic. Sends to client action messages to choose an action to perform in the turn, at
     * the end of which it performs a 'Lorenzo' action. Checks each time at the end of the turn if the player
     * or Lorenzo reached the end of faith path or the player bought seven development cards.
     */
    @Override
    public void run() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/java/it/polimi/ingsw/Logger/logging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
        logger.log(Level.INFO,ANSITextFormat.BOLD + "\nSingle player game started\n" + ANSITextFormat.RESET);
        SinglePlayer player = (SinglePlayer) clientHandler.getPlayer();
        player.printPlayer();

        sendLeaderCards();
        turnManager.lock();
        sendToClient(new GameStartedMessage(false));
        updateClient();

        while (!gameEnded) {
            sendToClient(new SelectActionMessage());
            turnManager.lock();
            clientHandler.sendJson(pickActionCard());
            updateClient();
            if (player.lorenzoWins()) {
                gameEnded = true;
                clientHandler.sendJson(new EndGameMessage("Lorenzo reached the end of the faith path! You lost!"));
            }
            if (player.getFaithPathPosition().equals(player.faithPathEnd()) || player.sevenDevCardBought()) {
                gameEnded = true;
                clientHandler.sendJson(new EndGameMessage("You win!\nTotal Victory points: " + player.getTotalVictoryPoints() + " victory points."));
            }
        }
    }

    /**
     * Parses leader cards and puts them in a Stack. After shuffling the stack, sends an
     * ArrayList of 4 leader cards to all clients.
     */
    public void sendLeaderCards() {
        LeaderCardParser parser = new LeaderCardParser();
        Stack<LeaderCard> deck = new Stack<>();
        deck = parser.getLeaderCardsDeck();
        Collections.shuffle(deck);
        List<LeaderCard> leaderCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if (!deck.isEmpty())
                leaderCards.add(deck.pop());
        }
        clientHandler.getPlayer().setLeaderCards(leaderCards);
        sendToClient(new ChooseLeaderCardMessage(leaderCards));
    }

    /**
     * Send a ServerMessage to client
     *
     * @param message
     */
    public void sendToClient(ServerMessage message) {
        clientHandler.sendJson(message);
    }

    /**
     * Update client's light model structures
     */
    public void updateClient() {
        sendToClient(new UpdateMarketboardMessage(game.getMarketBoard()));
        sendToClient(new UpdateDevCardsDeckMessage(game.peekCardsDeck()));
        HashMap<String, Integer> faithPathPositions = this.getLorenzoPosition();
        sendToClient(new UpdateFaithPathMessage(faithPathPositions, clientHandler.getPlayer().getFaithPathPosition()));
        sendToClient(new UpdateWarehouseCofferMessage(clientHandler.getPlayer().getClonedWarehouse(), clientHandler.getPlayer().getClonedCoffer()));
        sendToClient(new UpdateDevCardsSlotMessage(clientHandler.getPlayer().getPeekCardsInDevCardSLots()));
        sendToClient(new UpdateVaticanSectionsMessage(clientHandler.getPlayer().getVaticanSections()));
        sendToClient(new UpdateLeaderCardStatusMessage(clientHandler.getPlayer().getLeaderCards()));
    }

    /**
     * returns lorenzo position in faith path.
     *
     * @return
     */
    public HashMap<String, Integer> getLorenzoPosition() {
        HashMap<String, Integer> faithPathPositions = new HashMap<>();
        String nickname = "Lorenzo";
        Integer lorenzoPosition = ((SinglePlayer) clientHandler.getPlayer()).getLorenzoPosition();
        faithPathPositions.put(nickname, lorenzoPosition);
        return faithPathPositions;
    }

    /**
     * Pick a card from action cards deck to perform a 'Lorenzo' action.
     *
     * @return
     */
    public ServerMessage pickActionCard() {
        try {
            LogManager.getLogManager().readConfiguration(new FileInputStream("src/main/java/it/polimi/ingsw/Logger/logging.properties"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        LorenzoCard lorenzoCard = actionCards.pop();
        Integer lorenzoPosition;
        ServerMessage messageToReturn = null;
        boolean ended = false;

        switch (lorenzoCard.getType()) {

            case DISCARD2BLUEDVCARDS:
                logger.log(Level.INFO,"Picked Up a " + LorenzoCardType.DISCARD2BLUEDVCARDS);
                ended = turnManager.discardDevelopmentCards(CardColor.BLUE);
                if (ended) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "You picked all Blue cards!" + ANSITextFormat.RED_COLOR + "You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                messageToReturn = new SinglePlayerActionMessage(ANSITextFormat.BOLD + "Lorenzo Action card picked:\nYou discarded 2 Blue development cards." + ANSITextFormat.RESET, LorenzoCardType.DISCARD2BLUEDVCARDS);
                break;

            case DISCARD2GREENDVCARDS:
                logger.log(Level.INFO,"Picked Up a " + LorenzoCardType.DISCARD2GREENDVCARDS);
                ended = turnManager.discardDevelopmentCards(CardColor.GREEN);
                if (ended) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "You picked all Green cards!" + ANSITextFormat.RED_COLOR + "You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                messageToReturn = new SinglePlayerActionMessage(ANSITextFormat.BOLD + "Lorenzo Action card picked:\nYou discarded 2 Green development cards." + ANSITextFormat.RESET, LorenzoCardType.DISCARD2GREENDVCARDS);
                break;

            case DISCARD2VIOLETDVCARDS:
                logger.log(Level.INFO,"Picked Up a " + LorenzoCardType.DISCARD2VIOLETDVCARDS);
                ended = turnManager.discardDevelopmentCards(CardColor.VIOLET);
                if (ended) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "You picked all Violet cards!" + ANSITextFormat.RED_COLOR + "You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                messageToReturn = new SinglePlayerActionMessage(ANSITextFormat.BOLD + "Lorenzo Action card picked:\nYou discarded 2 Violet development cards." + ANSITextFormat.RESET, LorenzoCardType.DISCARD2VIOLETDVCARDS);
                break;

            case DISCARD2YELLOWDVCARDS:
                logger.log(Level.INFO,"Picked Up a " + LorenzoCardType.DISCARD2YELLOWDVCARDS);
                ended = turnManager.discardDevelopmentCards(CardColor.YELLOW);
                if (ended) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "You picked all Yellow cards!" + ANSITextFormat.RED_COLOR + "You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                messageToReturn = new SinglePlayerActionMessage(ANSITextFormat.BOLD + "Lorenzo Action card picked:\nYou discarded 2 Yellow development cards." + ANSITextFormat.RESET, LorenzoCardType.DISCARD2YELLOWDVCARDS);
                break;

            case TWOFAITHPOINTSCARD:
                logger.log(Level.INFO,"Picked Up a " + LorenzoCardType.TWOFAITHPOINTSCARD);
                player.incrementLorenzoPosition();
                lorenzoPosition = player.getLorenzoPosition();
                player.updateFaithPath(lorenzoPosition);
                if (player.lorenzoWins()) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "Lorenzo reached the end of the faith path!" + ANSITextFormat.RED_COLOR + " You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                player.incrementLorenzoPosition();
                lorenzoPosition = player.getLorenzoPosition();
                player.updateFaithPath(lorenzoPosition);
                if (player.lorenzoWins()) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "Lorenzo reached the end of the faith path! " + ANSITextFormat.RED_COLOR + "You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                messageToReturn = new SinglePlayerActionMessage(ANSITextFormat.BOLD + "Lorenzo Action card picked:\nLorenzo has advanced of two positions in faith path." + ANSITextFormat.RESET, LorenzoCardType.TWOFAITHPOINTSCARD);
                break;

            case FAITHANDSHUFFLECARD:
                logger.log(Level.INFO,"Picked Up a " + LorenzoCardType.FAITHANDSHUFFLECARD);
                player.incrementLorenzoPosition();
                lorenzoPosition = player.getLorenzoPosition();
                player.updateFaithPath(lorenzoPosition);
                actionCards.addAll(poppedActionsCard);
                poppedActionsCard.clear();
                Collections.shuffle(this.actionCards);
                if (player.lorenzoWins()) {
                    gameEnded = true;
                    messageToReturn = new EndGameMessage(ANSITextFormat.BOLD + "Lorenzo reached the end of the faith path! " + ANSITextFormat.RED_COLOR + "You lost!\n" + ANSITextFormat.RESET);
                    break;
                }
                messageToReturn = new SinglePlayerActionMessage(ANSITextFormat.BOLD + "Lorenzo Action card picked:\nLorenzo advanced of one position in faith path. Action cards have been reshuffled.\n" + ANSITextFormat.RESET, LorenzoCardType.FAITHANDSHUFFLECARD);
                break;
        }
        poppedActionsCard.push(lorenzoCard);

        return messageToReturn;
    }

}
