package it.polimi.ingsw.model.jsonFiles;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.DevCardSlots;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.HashMap;

public class Prova {

    public static void main(String[] args) {
        HashMap<Resource, Integer> cardCost;
        HashMap<Resource, Integer> prodIn;
        HashMap<Resource, Integer> prodOut;

        cardCost = new HashMap<>();
        cardCost.put(Resource.SERVANT, 2);
        cardCost.put(Resource.COIN, 1);

        prodIn = new HashMap<>();
        prodIn.put(Resource.SERVANT, 2);

        prodOut = new HashMap<>();
        prodOut.put(Resource.COIN, 1);

        DevelopmentCard dc = new DevelopmentCard(2, CardColor.BLUE, Level.SECOND, cardCost, prodIn, prodOut);
        Gson gson = new Gson();
        String stringJson = gson.toJson(dc);

        System.out.println(stringJson);

        Gson gson1 = new Gson();
        DevelopmentCard dc1 = gson1.fromJson(stringJson, DevelopmentCard.class);
        System.out.println(dc1.getProductionOut());

    }



}
