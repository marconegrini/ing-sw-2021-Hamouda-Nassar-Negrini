package it.polimi.ingsw.client.CLI;


import  it.polimi.ingsw.model.Coffer;
import  it.polimi.ingsw.model.Warehouse;
import it.polimi.ingsw.model.enumerations.ASCII_Resources;
import  it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.IllegalMoveException;
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
        ArrayList<ASCII_Resources> storageResources = new ArrayList<>();
        ASCII_Resources storage1Resource;
        ASCII_Resources storage2Resource;
        ASCII_Resources storage3Resource;
        String resource1String;
        ArrayList<String> resource2String = new ArrayList<>();
        ArrayList<String> resource3String = new ArrayList<>();

        storage1 = warehouse.getWarehouseStorage(1);
        storage2 = warehouse.getWarehouseStorage(2);
        storage3 = warehouse.getWarehouseStorage(3);


        if (1 == storage1.size()){
           storage1Resource = ASCII_Resources.getShape( storage1.get(0).toString() );
            resource1String = storage1Resource.toString();
        }else
            resource1String = " "; //empty space

        for (int i=0; i<2; i++) {
            if (i < storage2.size()) {
                storage2Resource = ASCII_Resources.getShape(storage2.get(i).toString());
                resource2String .add(storage2Resource.toString()) ;

            } else
                resource2String .add( " " ); //empty space
        }

         for (int i=0; i<3; i++) {
             if (i < storage3.size()) {
                 storage3Resource = ASCII_Resources.getShape(storage3.get(i).toString());
                 resource3String.add(storage3Resource.toString());

             } else
                 resource3String.add(" "); //empty space
         }







        result.add(String.format("\n" +
                "     ╔═══════════════════╗\n"+
                "     ║  \u001b[1m## DEPOSITS ##\u001B[0m   ║\n" +
                "╔════╩═══════════════════╩════╗ \n" +
                "║                             ║ \n" +
                "║      \u001b[4m# Your warehouse #\u001B[0m       \n" +
                "║                               \n" +
                "║             /  \\             \n" +
                "║            / %s               \n" +
                "║           / ----              \n" +
                "║          /  %s %s             \n" +
                "║         / ---------           \n" +
                "║        /   %s %s %s            \n" +
                "║        --------------         \n" +
                "║                               \n" +
                "║    ═ ═ ═ ═ ═ ═ ═ ═ ═ ═ ═      \n" +
                "║                              \n" +
                "║      \u001b[4m# Your coffer #\u001B[0m         \n" +
                "║                                  \n" +
                "║      Available Resources         \n" +
                "║      COINS    (%s): %d          \n" +
                "║      SHIELDS  (%s): %d          \n" +
                "║      STONES   (%s): %d          \n" +
                "║      SERVANTS (%s): %d          \n" +
                "║                              \n" +
                "╚═════════════════════════════╝\n"
                ,

             resource1String,
             resource2String.get(0),
             resource2String.get(1),
             resource3String.get(0),
             resource3String.get(1),
             resource3String.get(2),

             ASCII_Resources.COIN,
             coffer.resourceOccurrences(Resource.COIN),
             ASCII_Resources.SHIELD,
             coffer.resourceOccurrences(Resource.SHIELD),
             ASCII_Resources.STONE,
             coffer.resourceOccurrences(Resource.STONE),
             ASCII_Resources.SERVANT,
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

    public static void main(String[] args) throws StorageOutOfBoundsException, IllegalInsertionException, IllegalMoveException {

        Coffer coffer = new Coffer();
        Warehouse warehouse = new Warehouse();

        //FILLING THE COFFER
        List<Resource> resourceList = new ArrayList<>();
        resourceList.add(Resource.COIN);
        resourceList.add(Resource.SHIELD);
        resourceList.add(Resource.COIN);
        resourceList.add(Resource.SERVANT);
        resourceList.add(Resource.COIN);
        coffer.putResource(resourceList);

        //FILLING THE WAREHOUSE
        List<Resource> storage1 = new ArrayList<>();
        List<Resource> storage2 = new ArrayList<>();
        List<Resource> storage3 = new ArrayList<>();

        storage1.add(Resource.SERVANT);
        storage2.add(Resource.SHIELD);

        storage3.add(Resource.COIN);
        storage3.add(Resource.COIN);

        warehouse.putResource(1,storage1);
        warehouse.putResource(2,storage2);
        warehouse.putResource(3,storage3);

        
        
//        warehouse.moveResource(2,3);
//        warehouse.moveResource(1,3);


        DepositsTracer depositTracer = new DepositsTracer();

        ArrayList<String> arrayList = new ArrayList();
        arrayList = depositTracer.depositsTracer(warehouse,coffer);
        arrayList.forEach(System.out::println);

    }


}
