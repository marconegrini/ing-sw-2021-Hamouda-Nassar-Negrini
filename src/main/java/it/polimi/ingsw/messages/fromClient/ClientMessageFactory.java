package it.polimi.ingsw.messages.fromClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import java.util.MissingFormatArgumentException;

public class ClientMessageFactory {

    Gson gson;
    ClientMessage returnMessage = null;

    public ClientMessageFactory (){
        gson = new Gson();
    }

    /**
     * Method that takes every message (as String) received, and converts it into the corresponding message type from the enumeration.
     * @param receivedMessage message received in format of String
     * @return instantiate and return a message of the type send by the sender (client or server)
     */

    public ClientMessage returnMessage(String receivedMessage) {

            JsonElement json = gson.fromJson(receivedMessage, JsonElement.class);
            JsonObject messageObject = json.getAsJsonObject();
            String messageTypeString = messageObject.get("type").getAsString();
            ClientMessageType messageType = ClientMessageType.getMessageType(messageTypeString);

            //verifies the type of the sent message to instantiate the correct message.
            switch (messageType) {
                case LOGIN:
                    returnMessage = gson.fromJson(receivedMessage, LoginMessage.class);
                    break;
                case PING:
                    returnMessage = gson.fromJson(receivedMessage, ClientPing.class);
                    break;
                case SELECTLEADERCARDS:
                    returnMessage = gson.fromJson(receivedMessage, SelectLeaderCardMessage.class);
                    break;
                case ACTIVATEPERSONALPRODUCTION:
                    returnMessage = gson.fromJson(receivedMessage, ActivatePersonalProductionMessage.class);
                    break;
                case BUYDEVELOPMENTCARD:
                    returnMessage = gson.fromJson(receivedMessage, BuyDevCardMessage.class);
                    break;
                case ACTIVATEPRODUCTION:
                    returnMessage = gson.fromJson(receivedMessage, ActivateProductionMessage.class);
                    break;
                case PICKRESOURCES:
                    returnMessage = gson.fromJson(receivedMessage, PickResourcesMessage.class);
                    break;
                case DISCARDLEADERCARD:
                    returnMessage = gson.fromJson(receivedMessage, DiscardLeaderCardMessage.class);
                    break;
                case ACTIVATELEADERCARD:
                    returnMessage = gson.fromJson(receivedMessage, ActivateLeaderCardMessage.class);
                    break;
                case INSERTRESOURCESINWAREHOUSE:
                    returnMessage = gson.fromJson(receivedMessage, InsertResourcesInWarehouseMessage.class);
                    break;
                case MOVEWAREHOUSERESOURCES:
                    returnMessage = gson.fromJson(receivedMessage, MoveWarehouseResourcesMessage.class);
                    break;
                default:
                    System.err.println("Client message type not found inside Client factory");
                    break;
            }

        return returnMessage;
    }


}
