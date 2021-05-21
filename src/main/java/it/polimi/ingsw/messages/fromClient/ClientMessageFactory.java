package it.polimi.ingsw.messages.fromClient;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;

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
            case BUYDEVCARD:
                returnMessage = gson.fromJson(receivedMessage, BuyDevCardMessage.class);
                break;
        }

        return returnMessage;
    }


}
