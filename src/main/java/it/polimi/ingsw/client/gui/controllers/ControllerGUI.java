package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ServerHandler;

/**
 * GUI controller that contains a static reference to server handler
 */
public class ControllerGUI {

    private static ServerHandler serverHandler;

    public ControllerGUI (ServerHandler handler){
        serverHandler = handler;
    }

    public static ServerHandler getServerHandler (){
        return serverHandler;
    }

    public static void setServerHandler (ServerHandler handler){
        serverHandler = handler;
    }
}
