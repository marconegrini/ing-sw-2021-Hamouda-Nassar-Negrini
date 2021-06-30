package it.polimi.ingsw.client.CLI;


import it.polimi.ingsw.enumerations.ANSITextFormat;

import java.util.*;


public class FaithPathTracer {
    /**
     * @param othersPositions HashMap that contains the nickNames of the other players and their positions
     * @param playerPosition  the player (client)'s position
     * @return returns a list of strings of all player to be printed, every string is a player nickname & his position
     */
    public ArrayList<String> faithPathTracer(HashMap<String, Integer> othersPositions, Integer playerPosition) {

        ArrayList<String> result = new ArrayList<>();
//        TreeMap<String, Integer> treeMap = new TreeMap<>(othersPositions);

        othersPositions.put("tempEntry", playerPosition);

        //sort hashMap by value --> have the players sorted by position
        TreeSet<Map.Entry<String, Integer>> sortedEntries = new TreeSet<Map.Entry<String, Integer>>(
                (Map.Entry<String, Integer> e1, Map.Entry<String, Integer> e2) -> {
                    int res = e1.getValue().compareTo(e2.getValue());
                    return res != 0 ? res : 1;
                }
        );


        sortedEntries.addAll(othersPositions.entrySet());
        NavigableSet<Map.Entry<String, Integer>> treeReverse = sortedEntries.descendingSet();

        String stringFormatOthers = "%s is in position: %d";
        String stringFormatPlayer = ANSITextFormat.BOLD + "->" + ANSITextFormat.RESET +"You are in position: %d";



        //add strings ordered by the positions into the result array
        for (Map.Entry<String, Integer> entry : treeReverse) {

                if (entry.getKey().equals("tempEntry")) {
                    result.add(String.format(stringFormatPlayer, entry.getValue()));
            }
           else{
                result.add(String.format(stringFormatOthers,entry.getKey() , entry.getValue()));
           }

        }


        return result;
    }

    //TESTING
    public static void main(String[] args) {

        HashMap<String, Integer> othersPositions = new HashMap<>();
        othersPositions.put("Marco", 2);
        othersPositions.put("Adel", 2);
        othersPositions.put("Amir", 2);

        FaithPathTracer faithPathTracer = new FaithPathTracer();

        ArrayList<String> positions = faithPathTracer.faithPathTracer(othersPositions, 2);
        positions.forEach(System.out::println);
    }


}
