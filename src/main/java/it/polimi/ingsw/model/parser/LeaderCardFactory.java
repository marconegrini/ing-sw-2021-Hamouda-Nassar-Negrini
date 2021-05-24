package it.polimi.ingsw.model.parser;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LeaderCardFactory {

    public List<LeaderCard> create(JsonArray jsonLeaderCardsArray){
        List<LeaderCard> leaderCards= new ArrayList<>();

        for (JsonElement jsonElement : jsonLeaderCardsArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            int victoryPoints = jsonObject.get("Vp").getAsInt();
            String tempCardType = jsonObject.get("cardType").getAsString();
            CardType cardType = CardType.getEnum(tempCardType);

            if(jsonObject.get("cardType").getAsString().equals("DISCOUNT")){

                JsonArray jsonArray1 = jsonObject.get("activationCost").getAsJsonArray();
                List<LeaderCardCost> activationCost = new ArrayList<>();
                for(JsonElement elem  : jsonArray1){
                    JsonObject object = elem.getAsJsonObject();
                    String tempCardColor = object.get("color").getAsString();
                    CardColor cardColor = CardColor.getEnum(tempCardColor);
                    activationCost.add(new LeaderCardCost(cardColor, Level.ANY));
                }

                JsonObject discount = jsonObject.get("discountedResource").getAsJsonObject();
                HashMap<Resource,Integer> discountedResource = getCorrectHashMap(new Gson().fromJson(discount.toString(), HashMap.class));

                DiscountLeaderCard leaderCard = new DiscountLeaderCard(cardType ,victoryPoints, activationCost, discountedResource);
                leaderCards.add(leaderCard);
            }

            if(jsonObject.get("cardType").getAsString().equals("STORAGE")){

                JsonObject jsonObject1 = jsonObject.get("activationCost").getAsJsonObject();
                HashMap<Resource, Integer> activationCost = getCorrectHashMap(new Gson().fromJson(jsonObject1.toString(), HashMap.class));
                JsonObject jsonObject2 = jsonObject.get("slots").getAsJsonObject();
                HashMap<Resource, Integer> slots = getCorrectHashMap(new Gson().fromJson(jsonObject2.toString(), HashMap.class));

                StorageLeaderCard leaderCard = new StorageLeaderCard(cardType, victoryPoints, activationCost, slots);
                leaderCards.add(leaderCard);
            }

            if(jsonObject.get("cardType").getAsString().equals("MARBLE")){

                JsonArray jsonArray1 = jsonObject.get("activationCost").getAsJsonArray();
                List<LeaderCardCost> activationCost = new ArrayList<>();
                for(JsonElement elem  : jsonArray1){
                    JsonObject object = elem.getAsJsonObject();
                    String tempCardColor = object.get("color").getAsString();
                    CardColor cardColor = CardColor.getEnum(tempCardColor);
                    activationCost.add(new LeaderCardCost(cardColor, Level.ANY));
                }

                JsonObject jsonObject1 = jsonObject.get("productionOut").getAsJsonObject();
                HashMap<Resource, Integer> productionOut = getCorrectHashMap(new Gson().fromJson(jsonObject1.toString(), HashMap.class));

                WhiteMarbleLeaderCard leaderCard = new WhiteMarbleLeaderCard(cardType, victoryPoints, activationCost, productionOut);
                leaderCards.add(leaderCard);
            }

            if(jsonObject.get("cardType").getAsString().equals("PRODUCTION")){

                JsonArray jsonArray1 = jsonObject.get("activationCost").getAsJsonArray();
                List<LeaderCardCost> activationCost = new ArrayList<>();
                for(JsonElement elem  : jsonArray1){
                    JsonObject object = elem.getAsJsonObject();
                    String tempCardColor = object.get("color").getAsString();
                    CardColor cardColor = CardColor.getEnum(tempCardColor);
                    activationCost.add(new LeaderCardCost(cardColor, Level.ANY));
                }

                JsonObject jsonObject1 = jsonObject.get("productionIn").getAsJsonObject();
                HashMap<Resource, Integer> productionIn = getCorrectHashMap(new Gson().fromJson(jsonObject1.toString(), HashMap.class));
                Integer outProductionResourceNum = jsonObject.get("outProductionResourceNum").getAsInt();
                Integer outProductionFaithPoints = jsonObject.get("outProductionFaithPoints").getAsInt();

                ProdPowerLeaderCard leaderCard = new ProdPowerLeaderCard(cardType, victoryPoints, activationCost, productionIn, outProductionResourceNum, outProductionFaithPoints);
                leaderCards.add(leaderCard);
            }
        }

        return leaderCards;

    }

    private HashMap<Resource, Integer> getCorrectHashMap(HashMap<String, Double> temporary){
        HashMap<Resource,Integer> correctOne = new HashMap<>();
        for(String s : temporary.keySet()){
            Resource res = Resource.getEnum(s);
            Integer value = temporary.get(s).intValue();
            correctOne.put(res, value);
        }
        return correctOne;
    }
}
