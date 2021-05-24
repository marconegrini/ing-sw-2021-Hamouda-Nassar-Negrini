//package it.polimi.ingsw.messages.fromServer;
//
//import it.polimi.ingsw.client.ServerHandler;
//import it.polimi.ingsw.messages.MessageType;
//
//public class PrintMessage extends ServerMessage {
//
//    String stringToPrintByClient;
//
//    public PrintMessage(String stringToPrintByClient){
//        super(ServerMessageType.PRINT);
//        this.stringToPrintByClient = stringToPrintByClient;
//    }
//
//    @Override
//    public void clientProcess(ServerHandler serverHandler) {
//        serverHandler.getView().printToClient();
//    }
//}
