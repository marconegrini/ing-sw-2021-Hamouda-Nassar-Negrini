package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.MalformedJsonException;
import it.polimi.ingsw.messages.requestFromClient.*;
import it.polimi.ingsw.messages.updateFromServer.OkMessage;
import it.polimi.ingsw.messages.updateFromServer.ResourcesFromMarketMessage;
import it.polimi.ingsw.messages.updateFromServer.UpdateLeaderCardMessage;
import it.polimi.ingsw.messages.updateFromServer.UpdateMarketboardMessage;

public class MessageFactory {

    Gson gson;
    Message returnMessage = null;

    public MessageFactory(){
        gson = new Gson();
    }

    /**
     * Method that takes every message (as String) received, and converts it into the corresponding message type from the enumeration.
     * @param receivedMessage message received in format of String
     * @return instantiate and return a message of the type send by the sender (client or server)
     */

    public Message returnMessage(String receivedMessage) throws MalformedJsonException {

        //Checks the type of the message send to be then inserted into the switch to be instantiated.
        JsonElement json = gson.fromJson(receivedMessage, JsonElement.class);
        if(json.isJsonObject()) {
            JsonObject messageObject = json.getAsJsonObject();
            String messageTypeString = messageObject.get("messageType").getAsString();
            MessageType messageType = MessageType.getMessageType(messageTypeString);

            //verifies the type of the sent message to instantiate the correct message.

            switch (messageType) {

                case PICKRESOURCES:
                    returnMessage = gson.fromJson(receivedMessage, PickResourcesMessage.class);
                    break;
                case BUYDEVELOPMENTCARD:
                    returnMessage = gson.fromJson(receivedMessage, BuyDevelopmentCardMessage.class);
                    break;
                case ACTIVATEPRODUCTION:
                    returnMessage = gson.fromJson(receivedMessage, ActivateProductionMessage.class);
                    break;
                case ACTIVATEPERSONALPRODUCTION:
                    returnMessage = gson.fromJson(receivedMessage, ActivatePersonalProductionMessage.class);
                    break;
                case INSERTRESOURCESINWAREHOUSE:
                    returnMessage = gson.fromJson(receivedMessage, InsertResourcesInWarehouseMessage.class);
                    break;
                case MOVEWAREHOUSERESOURCES:
                    returnMessage = gson.fromJson(receivedMessage, MoveWarehouseResources.class);
                    break;
                case CHOOSELEADERCARD:
                    returnMessage = gson.fromJson(receivedMessage, ChooseLeaderCardMessage.class);
                    break;
                case ACTIVATELEADERCARD:
                    returnMessage = gson.fromJson(receivedMessage, ActivateLeaderCardMessage.class);
                    break;
                case DISCARDLEADERCARD:
                    returnMessage = gson.fromJson(receivedMessage, DiscardLeaderCardMessage.class);
                    break;

                case UPDATEMARKETBOARD:
                    returnMessage = gson.fromJson(receivedMessage, UpdateMarketboardMessage.class);
                    break;
                case UPDATELEADERCARD:
                    returnMessage = gson.fromJson(receivedMessage, UpdateLeaderCardMessage.class);
                    break;
                case RESOURCESFROMMARKET:
                    returnMessage = gson.fromJson(receivedMessage, ResourcesFromMarketMessage.class);
                    break;
                case OK:
                    returnMessage = gson.fromJson(receivedMessage, OkMessage.class);
                    break;


                case ERROR:
                    break;
                case PING:
                    returnMessage = gson.fromJson(receivedMessage, PingMessage.class);
                    break;
                case MESSAGE_TYPEMESSAGE:
                    break;
                case EXCEPTION:
                    break;
                case LEADERCARD:
                    break;
            }
        }

    return returnMessage;
    }



}
