package it.polimi.ingsw.messages.fromServer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.fromServer.activateProduction.ProductionResultMessage;
import it.polimi.ingsw.messages.fromServer.leadercard.LeaderResultMessage;
import it.polimi.ingsw.messages.fromServer.update.*;
import it.polimi.ingsw.messages.fromServer.storeResources.ErrorWarehouseMessage;
import it.polimi.ingsw.messages.fromServer.storeResources.ResourcesToStoreMessage;
import it.polimi.ingsw.messages.fromServer.warehouse.MoveResourcesResultMessage;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.parser.LeaderCardFactory;

import java.util.List;


/**
 * message factory USED BY the CLIENT, to dynamically get the correct instance the server's message.
 */
public class ServerMessageFactory {

    Gson gson;
    ServerMessage returnMessage = null;

    public ServerMessageFactory(){
        gson = new Gson();
    }

    /**
     * Method that takes every message (as String) received, and converts it into the corresponding message type from the enumeration.
     * @param receivedMessage message received in format of String
     * @return instantiate and return a message of the type send by the sender (client or server)
     */

    public ServerMessage returnMessage(String receivedMessage) {

       JsonElement json = gson.fromJson(receivedMessage, JsonElement.class);
       JsonObject messageObject = json.getAsJsonObject();
       String messageTypeString = messageObject.get("type").getAsString();
       ServerMessageType messageType = ServerMessageType.getMessageType(messageTypeString);

            //verifies the type of the sent message to instantiate the correct message.
            switch (messageType) {

                case OK:
                    returnMessage = gson.fromJson(receivedMessage, OkMessage.class);
                    break;
                case ERROR:
                    returnMessage = gson.fromJson(receivedMessage, ErrorMessage.class);
                    break;
                case PING:
                    returnMessage = gson.fromJson(receivedMessage, ServerPingMessage.class);
                    break;
                case LOGIN:
                    returnMessage = gson.fromJson(receivedMessage, ServerLoginMessage.class);
                    break;
                case LOGIN_ERROR:
                    returnMessage = gson.fromJson(receivedMessage, ServerLoginErrorMessage.class);
                    break;
                case PARTICIPANTS:
                    returnMessage = gson.fromJson(receivedMessage, ParticipantsMessage.class);
                    break;
                case INWAITINGROOM:
                    returnMessage = gson.fromJson(receivedMessage, InWaitingRoomMessage.class);
                    break;
                case ERRORSTARTGAME:
                    returnMessage = gson.fromJson(receivedMessage, ErrorStartGameMessage.class);
                    break;
                case STARTGAME:
                    returnMessage = gson.fromJson(receivedMessage, StartGameMessage.class);
                    break;
                case ENDGAME:
                    returnMessage = gson.fromJson(receivedMessage, EndGameMessage.class);
                    break;
                case CALAMAIO:
                    returnMessage = gson.fromJson(receivedMessage, InitializeCalamaioMessage.class);
                    break;
                case CALAMAIOERR:
                    returnMessage = gson.fromJson(receivedMessage, CalamaioErrorMessage.class);
                    break;
                case RESOURCESTOSTORE:
                    returnMessage = gson.fromJson(receivedMessage, ResourcesToStoreMessage.class);
                    break;
                case CHOOSELEADERCARDS:
                    JsonArray jsonLeaderCards1 = messageObject.getAsJsonArray("leaderCards");
                    LeaderCardFactory factory1 = new LeaderCardFactory();
                    List<LeaderCard> leaderCards1 = factory1.create(jsonLeaderCards1);
                    returnMessage = new ChooseLeaderCardMessage(leaderCards1);
                    break;
                case UPDATELEADERCARDS:
                    returnMessage = gson.fromJson(receivedMessage, UpdateLeaderCardsMessage.class);
                    break;
                case UPDATELEADERCARDSTATUS:
                    JsonArray jsonLeaderCards2 = messageObject.getAsJsonArray("leaderCards");
                    LeaderCardFactory factory2 = new LeaderCardFactory();
                    List<LeaderCard> leaderCards2 = factory2.create(jsonLeaderCards2);
                    returnMessage = new UpdateLeaderCardStatusMessage(leaderCards2);
                    break;
                case UPDATEMARKETBOARD:
                    returnMessage = gson.fromJson(receivedMessage, UpdateMarketboardMessage.class);
                    break;
                case UPDATEDEVCARDSDECK:
                    returnMessage = gson.fromJson(receivedMessage, UpdateDevCardsDeckMessage.class);
                    break;
                case UPDATEDEVCARDSSLOT:
                    returnMessage = gson.fromJson(receivedMessage, UpdateDevCardsSlotMessage.class);
                    break;
                case UPDATEFAITHPATH:
                    returnMessage = gson.fromJson(receivedMessage, UpdateFaithPathMessage.class);
                    break;
                case UPDATEWAREHOUSECOFFER:
                    returnMessage = gson.fromJson(receivedMessage, UpdateWarehouseCofferMessage.class);
                    break;
                case UPDATEVATICANSECTIONS:
                    returnMessage = gson.fromJson(receivedMessage, UpdateVaticanSectionsMessage.class);
                    break;
                case ERRORWAREHOUSE:
                    returnMessage = gson.fromJson(receivedMessage, ErrorWarehouseMessage.class);
                    break;
                case SELECTACTION:
                    returnMessage = gson.fromJson(receivedMessage, SelectActionMessage.class);
                    break;
                case PRODUCTIONRESULT:
                    returnMessage = gson.fromJson(receivedMessage, ProductionResultMessage.class);
                    break;
                case MOVERESOURCESRESULT:
                    returnMessage = gson.fromJson(receivedMessage, MoveResourcesResultMessage.class);
                    break;
                case LEADERCARDRESULT:
                    returnMessage = gson.fromJson(receivedMessage, LeaderResultMessage.class);
                    break;
                case END:
                    returnMessage = gson.fromJson(receivedMessage, EndGameMessage.class);
                    break;
                case BUYDVCARDERROR:
                    returnMessage = gson.fromJson(receivedMessage, BuyDVCardError.class);
                    break;
                case SINGLEPLAYERACTION:
                    returnMessage = gson.fromJson(receivedMessage, SinglePlayerActionMessage.class);
                    break;
                default:
                    System.err.println("Server message type not found inside Server factory");
                    break;
            }

        return returnMessage;
    }




}
