package it.polimi.ingsw.messages.fromServer;

import it.polimi.ingsw.client.ServerHandler;
import it.polimi.ingsw.messages.fromClient.ClientMessage;
import it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.server.handlers.ClientHandler;

 /** message sent by the server TO THE CLIENT, in response of the initialiseCalamaio message
  * it contains the the wareHouse structure updated
  */
public class UpdateWarehouseMessage extends ServerMessage{

    Warehouse warehouse;

    public UpdateWarehouseMessage(Warehouse warehouse){
        super(ServerMessageType.UPDATEWAREHOUSE);
        this.warehouse = warehouse;
    }
     @Override
     public void clientProcess(ServerHandler serverHandler) {
         System.out.println(serverHandler.getPlayer().getWarehouseResource());
     }
 }
