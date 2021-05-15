package it.polimi.ingsw.CLI;


import  it.polimi.ingsw.model.Coffer;
import  it.polimi.ingsw.model.Storage;
import  it.polimi.ingsw.model.Warehouse;
import  it.polimi.ingsw.model.enumerations.Resource;

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

                storage2.get(0),
                storage2.get(1),
                storage3.get(0),
                storage3.get(1),
                storage3.get(2),

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
             return null;
    }

}
