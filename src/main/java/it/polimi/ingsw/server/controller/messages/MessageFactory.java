package it.polimi.ingsw.server.controller.messages;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class MessageFactory {

    public Message messageFactory(String recievedMessage)
    {
        Message returnMessage = null;
        Gson gson = new Gson();
        JsonElement json = gson.fromJson(recievedMessage, JsonElement.class);
        JsonObject messageObject = json.getAsJsonObject();
        String messageTypeString = messageObject.get("messageType").getAsString();
        MessageType messageType = MessageType.getMessageType(messageTypeString);

        System.out.println(messageTypeString);
        System.out.println(messageType);

        if (messageType.equals(MessageType.PICKRESOURCES) ){
            returnMessage = new PickResourcesMessage("nome",true,2);
        }
    return returnMessage;
    }
}
