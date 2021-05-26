package it.polimi.ingsw.messages.fromServer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.fromServer.update.*;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.parser.LeaderCardFactory;

import java.util.List;


/**
 * message factory USED BY the CLIENT, to receive the messages of the server.
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
                case CALAMAIO:
                    returnMessage = gson.fromJson(receivedMessage, InitializeCalamaioMessage.class);
                    break;
                case CHOOSELEADERCARDS:
                    JsonArray jsonLeaderCards = messageObject.getAsJsonArray("leaderCards");
                    LeaderCardFactory factory = new LeaderCardFactory();
                    List<LeaderCard> leaderCards = factory.create(jsonLeaderCards);
                    returnMessage = new ChooseLeaderCardMessage(leaderCards);
                    break;
                case UPDATELEADERCARDS:
                    returnMessage = gson.fromJson(receivedMessage, UpdateLeaderCardsMessage.class);
                    break;
                case UPDATEMARKETBOARD:
                    returnMessage = gson.fromJson(receivedMessage, UpdateMarkeboardMessage.class);
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
                case SELECTACTION:
                    returnMessage = gson.fromJson(receivedMessage, SelectActionMessage.class);
                    break;
                case END:
                    returnMessage = gson.fromJson(receivedMessage, EndMessage.class);
                    break;
                default:
                    System.err.println("Server message type not found inside Server factory");
                    break;
            }

        return returnMessage;
    }




}
