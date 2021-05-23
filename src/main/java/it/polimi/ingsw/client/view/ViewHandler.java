package it.polimi.ingsw.client.view;

import it.polimi.ingsw.messages.fromClient.ClientMessage;

public class ViewHandler {

    private View view;

    public ViewHandler(boolean isCli){
        if(isCli)
            this.view = new CLIView();
        else this.view = new GUIView();
    }

    public ClientMessage logClient(){
        return view.logClient();
    }

}
