package it.polimi.ingsw.messages;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.messages.requestFromClient.*;
import it.polimi.ingsw.server.controller.messages.requestFromClient.*;
import it.polimi.ingsw.messages.updateFromServer.OkMessage;

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

    public Message returnMessage(String receivedMessage)
    {

        //Checks the type of the message send to be then inserted into the switch to be instantiated.
        JsonElement json = gson.fromJson(receivedMessage, JsonElement.class);
        JsonObject messageObject = json.getAsJsonObject();
        String messageTypeString = messageObject.get("messageType").getAsString();
        MessageType messageType = MessageType.getMessageType(messageTypeString);

        //verifies the type of the sent message to instantiate the correct message.

        switch (messageType){

            case PICKRESOURCES:
                returnMessage = gson.fromJson(receivedMessage, PickResourcesMessage.class);
                break;
            case BUYDEVELOPMENTCARD:
                returnMessage = gson.fromJson(receivedMessage, BuyDevelopmentCardMessage.class);
            case OK:
                returnMessage = gson.fromJson(receivedMessage, OkMessage.class);
            case ACTIVATEPRODUCTION:
                returnMessage = gson.fromJson(receivedMessage, ActivateProductionMessage.class);
            case INSERTRESOURCESINWAREHOUSE:
                returnMessage = gson.fromJson(receivedMessage, InsertResourcesInWarehouseMessage.class);
            case MOVEWAREHOUSERESOURCES:
                returnMessage = gson.fromJson(receivedMessage, MoveWarehouseResources.class);
            case ACTIVATELEADERCARD:
                returnMessage = gson.fromJson(receivedMessage, ActivateLeaderCardMessage.class);
            case CHOOSELEADERCARD:
                returnMessage = gson.fromJson(receivedMessage, ChooseLeaderCardMessage.class);

            case ERROR:
                break;
            case PING:
                break;
            case MESSAGE_TYPEMESSAGE:
                break;
            case EXCEPTION:
                break;
            case LEADERCARD:
                break;
        }

        /*
        if (messageType.equals(MessageType.PICKRESOURCES) ){
            returnMessage = gson.fromJson(receivedMessage, PickResourcesMessage.class) ;
        }
         */
    return returnMessage;
    }



}