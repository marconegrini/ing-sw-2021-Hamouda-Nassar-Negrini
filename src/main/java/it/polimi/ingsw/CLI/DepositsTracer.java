package it.polimi.ingsw.CLI;


import  it.polimi.ingsw.model.Coffer;
import  it.polimi.ingsw.model.Warehouse;
import  it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;

import java.util.ArrayList;
import java.util.List;

/**
 * Traces Coffer and Warehouse of a player
 */
public class DepositsTracer {
    ArrayList<String> result;
    List<Resource> storage1;
    List<Resource> storage2;
    List<Resource> storage3;
    Coffer coffer = new Coffer();



    public ArrayList<String> depositsTracer(Warehouse warehouse, Coffer coffer) {

        result = new ArrayList<String>();
        storage1 = new ArrayList<>();
        storage2 = new ArrayList<>();
        storage3 = new ArrayList<>();
        storage1 = warehouse.getWarehouseStorage(0);
        storage2 = warehouse.getWarehouseStorage(1);
        storage3 = warehouse.getWarehouseStorage(2);
        result.add(String.format("\n" +
                " ╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗ \n" +
                " ║      # Your warehouse #                                  # Your coffer #                            ║ \n" +
                " ║                                                                                                     ║ \n" +
                " ║             %s                                          Available Resources                         ║ \n" +
                " ║            ----                                         COINS:   % d                                ║ \n" +
                " ║            %s %s                                        SHIELDS: % d                                ║ \n" +
                " ║          ---------                                      STONES:  % d                                ║ \n" +
                " ║           %s %s %s                                      SERVANTS:% d                                ║ \n" +
                " ║        --------------                                                                               ║ \n" +
                " ╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝ \n",
                storage1.get(0),
                coffer.resourceOccurrences(Resource.COIN),
                storage2.get(0),
                storage2.get(1),
                coffer.resourceOccurrences(Resource.SHIELD),
                coffer.resourceOccurrences(Resource.STONE),
                storage3.get(0),
                storage3.get(1),
                storage3.get(2),
                coffer.resourceOccurrences(Resource.SERVANT)

                ));







//
//        ╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗
//        ║      # Your warehouse #                                  # Your coffer #                            ║
//        ║                                                                                                     ║
//        ║             %s                                          Available Resources                         ║
//        ║            ----                                         COINS:   %d                                 ║
//        ║            %s %s                                        SHIELDS: %d                                 ║
//        ║          ---------                                      STONES:  %d                                 ║
//        ║           %s %s %s                                      SERVANTS:%d                                 ║
//        ║        --------------                                                                               ║
//    }   ╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝
//
             return result;
    }

    public static void main(String[] args) throws StorageOutOfBoundsException, IllegalInsertionException {

        Coffer coffer = new Coffer();
        Warehouse warehouse = new Warehouse();

        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.COIN);
        resourceList.add(Resource.SHIELD);
        resourceList.add(Resource.COIN);
        resourceList.add(Resource.SERVANT);
        resourceList.add(Resource.COIN);
        coffer.putResource(resourceList);

        List<Resource> storage1 = new ArrayList<>();
        List<Resource> storage2 = new ArrayList<>();
        List<Resource> storage3 = new ArrayList<>();

        storage1.add(Resource.COIN);
        storage2.add(Resource.SHIELD);
        storage2.add(Resource.STONE);
        storage3.add(Resource.SERVANT);

        warehouse.putResource(1,storage1);
        warehouse.putResource(2,storage2);
        warehouse.putResource(3,storage3);

        DepositsTracer depositTracer = new DepositsTracer();
        depositTracer.depositsTracer(warehouse,coffer);


    }


}
