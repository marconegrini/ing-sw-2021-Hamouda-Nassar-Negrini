package it.polimi.ingsw.client.gui.controllers;

import it.polimi.ingsw.client.ServerHandler;

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
