package it.polimi.ingsw.client.CLI;

import java.util.*;


public class FaithPathTracer {
    /**
     *
     * @param othersPositions HashMap that contains the nickNames of the other players and their positions
     * @param playerPosition the player (client)'s position
     * @return returns a list of strings of all player to be printed, every string is a player nickname & his position
     */
    public ArrayList<String> faithPathTracer(HashMap<String, Integer> othersPositions, Integer playerPosition){

    ArrayList<String> result=new ArrayList<>();

    String stringFormatOthers="%s is in position: %s";
    String stringFormatPlayer="You are in position: %d";
    //othersPositions.put("tempEntry",playerPosition);

        //result.add(String.format(stringFormatPlayer,playerPosition));

        //sort hashMap by value --> have the players sorted by position
        List<Integer> intList = new ArrayList<Integer>(othersPositions.values());

        intList.sort(new Comparator<Integer>() {

            public int compare(Integer o1, Integer o2) {
                // for descending order
                return o2 - o1;
            }
        });

        /* or simply :
         intList.sort((o1, o2) -> {

            // for descending order
            return o2 - o1;
        */

        //add strings ordered by the positions into the result array
        for (int i=0;i<intList.size();i++) {

        for (String nikName:othersPositions.keySet()){

        if (othersPositions.get(nikName).equals(intList.get(i))) {
            if (nikName.equals("tempEntry")) {
                result.add(String.format(stringFormatPlayer, playerPosition));
            } else {
                String tempStr = String.format(stringFormatOthers, nikName, othersPositions.get(nikName));
                result.add(tempStr);
            }
        }
      }
    }


    return result;
}


    public static void main(String[] args) {

        HashMap<String,Integer> othersPositions=new HashMap<>();
        othersPositions.put("Marco",6);
        othersPositions.put("Paolo",7);
        othersPositions.put("Amir",5);

        FaithPathTracer faithPathTracer = new FaithPathTracer();

        ArrayList<String> positions = faithPathTracer.faithPathTracer(othersPositions,1);
        positions.forEach(System.out::println);
    }






}
