package it.polimi.ingsw.messages.fromClient;

import it.polimi.ingsw.client.ClientApp;
import it.polimi.ingsw.messages.fromServer.CalamaioErrorMessage;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.IllegalInsertionException;
import it.polimi.ingsw.exceptions.StorageOutOfBoundsException;
import it.polimi.ingsw.server.handlers.ClientHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static java.util.logging.Level.INFO;

/**
 * message sent by the client TO THE SERVER, in response of the initialiseCalamaio message
 * it contains the choice/s of te resources that a player will chose in the case he can.
 */
public class CalamaioResponseMessage extends ClientMessage {


    private static final Logger logger = Logger.getLogger(CalamaioResponseMessage.class.getName());
    int choice1 = 0;
    int choice2 = 0;
    int destStorage1 = 0;
    int destStorage2 = 0;

    boolean allIsWell = false;

    public CalamaioResponseMessage(int choice1, int choice2, int destStorage1, int destStorage2) {
        super(ClientMessageType.CALAMAIORESPONSE);
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.destStorage1 = destStorage1;
        this.destStorage2 = destStorage2;
    }

    @Override
    public void serverProcess(ClientHandler clientHandler) {

        Logger logger = Logger.getLogger(ClientApp.class.getName());


        if (choice1 != 0) {
            logger.log(INFO,"Here1");
            if (destStorage1 != 0) {
                //
                try {
                    clientHandler.getPlayer().putWarehouseResources(destStorage1, resourceConverter(choice1));
                    allIsWell =true; //will pass from here ONLY IF there no exception thrown
                    logger.log(Level.INFO,"Here2");

                } catch (StorageOutOfBoundsException e) {
                    clientHandler.sendJson(new CalamaioErrorMessage("StorageOutOfBoundsException"));
                    e.printStackTrace();
                } catch (IllegalInsertionException e) {
                    clientHandler.sendJson(new CalamaioErrorMessage("IllegalInsertionException"));
                    e.printStackTrace();
                }

                if (choice2 != 0) {
                    allIsWell=false;
                    if (destStorage2 != 0) {
                        try {
                            clientHandler.getPlayer().putWarehouseResources(destStorage2, resourceConverter(choice2));
                            allIsWell =true; //will pass from here ONLY IF there no exception thrown
                            logger.log(Level.INFO,"Here3");

                        } catch (StorageOutOfBoundsException e) {

                            clientHandler.getPlayer().pullWarehouseResources( resourceConverter(choice1) );
                            logger.log(Level.INFO,"Here4");

                            clientHandler.sendJson(new CalamaioErrorMessage("StorageOutOfBoundsException"));
                            e.printStackTrace();

                        } catch (IllegalInsertionException e) {

                            clientHandler.getPlayer().pullWarehouseResources( resourceConverter(choice1) );
                            logger.log(Level.INFO,"Here5");

                            clientHandler.sendJson(new CalamaioErrorMessage("IllegalInsertionException"));
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else allIsWell=true; //the case of the player with the calamaio (who doesn't choose any resource)

        //the message update is sent separately after this message.
//        clientHandler.sendJson(new UpdateWarehouseCofferMessage(clientHandler.getPlayer().getPersonalBoard().getClonedWarehouse(),clientHandler.getPlayer().getPersonalBoard().getClonedCoffer()));
//        clientHandler.sendJson(new UpdateFaithPathMessage(othersFaithPath,clientHandler.getPlayer().getFaithPathPosition()));
//        clientHandler.sendJson(new UpdateWarehouseMessage(clientHandler.getPlayer().getPersonalBoard().getWarehouse()));

        logger.log(Level.INFO,"All is well : " + allIsWell);

        logger.log(Level.INFO,"getAccesses(): " + clientHandler.getTurnManager().getAccesses());
        if (allIsWell){
            logger.log(Level.INFO,"---PASSED--clientDone()---");
        clientHandler.getTurnManager().clientDone();
        }
    }


    private List<Resource> resourceConverter(int choice) {
        List<Resource> returnList = new ArrayList<>();

        switch (choice) {
            case 1:
                returnList.add(Resource.SHIELD);
                break;
            case 2:
                returnList.add(Resource.COIN);
                break;
            case 3:
                returnList.add(Resource.SERVANT);
                break;
            case 4:
                returnList.add(Resource.STONE);
                break;
        }

        return returnList;
    }

}
