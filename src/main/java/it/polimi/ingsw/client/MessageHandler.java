package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.Message;
import it.polimi.ingsw.messages.requestFromClient.ActivateLeaderCardMessage;

import java.io.DataOutputStream;

public class MessageHandler{


    public void activateLeaderCardMessageSend(String nickname, Integer indexNumber, DataOutputStream getToServer){
        Message msg = new ActivateLeaderCardMessage(nickname, indexNumber);
        msg.clientProcess(getToServer);
        }


    }








