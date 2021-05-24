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

            if(jsonObject.get("cardType").getAsString().equals("DISCOUNT")){

                JsonArray jsonArray1 = jsonObject.get("activationCost").getAsJsonArray();
                JsonElement jsonDiscount = jsonObject.get("discountedResource");
                String tempCardType = jsonObject.get("cardType").getAsString();
                CardType cardType = CardType.getEnum(tempCardType);
                List<LeaderCardCost> activationCost = new ArrayList<>();

                for(JsonElement elem  : jsonArray1){
                    JsonObject object = elem.getAsJsonObject();

                    String tempCardColor = object.get("color").getAsString();
                    CardColor cardColor = CardColor.getEnum(tempCardColor);

                    activationCost.add(new LeaderCardCost(cardColor, Level.ANY));
                }

                JsonObject discount = jsonDiscount.getAsJsonObject();
                HashMap<Resource,Integer> discountedResource = new Gson().fromJson(discount.toString(), HashMap.class);

                DiscountLeaderCard leaderCard = new DiscountLeaderCard(cardType ,victoryPoints, activationCost, discountedResource);

                leaderCards.add(leaderCard);
            }

            if(jsonObject.get("cardType").getAsString().equals("STORAGE")){

                JsonElement jsonElement1 = jsonObject.get("activationCost");
                JsonObject jsonObject1 = jsonElement1.getAsJsonObject();
                HashMap<Resource, Integer> activationCost = new Gson().fromJson(jsonObject1.toString(), HashMap.class);
                String tempCardType = jsonObject.get("cardType").getAsString();
                CardType cardType = CardType.getEnum(tempCardType);
                JsonElement jsonElement2 = jsonObject.get("storage");
                JsonObject jsonObject2 = jsonElement2.getAsJsonObject();
                HashMap<Resource, Integer> slots = new Gson().fromJson(jsonObject2.toString(), HashMap.class);

                HashMap<Resource, Integer> activationCost = new HashMap<>();

                    JsonObject object = jsonCost.getAsJsonObject();

                    String tempResource = object.getAsString();
                    System.out.println(tempResource);
                    Resource resource = Resource.getEnum(tempResource);
                    Integer cost = object.get(tempResource).getAsInt();

                    activationCost.put(resource, cost);

                HashMap<Resource, Integer> storage = new HashMap<>();

                for(JsonElement elem : jsonStorage){
                    JsonObject obj = elem.getAsJsonObject();
                    //TODO check meaning
                    storage.put(null, null);

                }

                StorageLeaderCard leaderCard = new StorageLeaderCard(cardType, victoryPoints, activationCost, storage);

                leaderCards.add(leaderCard);

            }

            if(jsonObject.get("type").getAsString().equals("marble")){


                String tempcardType = jsonObject.get("type").getAsString();
                CardType cardType = CardType.getEnum(tempcardType);

                List<LeaderCardCost> activationCost = new ArrayList();

                for(JsonElement elem  : jsonArray1){
                    JsonObject object = elem.getAsJsonObject();

                    String tempCardColor = object.get("cardColor").getAsString();
                    CardColor cardColor = CardColor.getEnum(tempCardColor);
                    Integer cost = object.get("cost").getAsInt();

                    for(int i = 0; i < cost; i++)
                        activationCost.add(new LeaderCardCost(cardColor, Level.ANY));
                }

                HashMap<Resource, Integer> productionOut = new HashMap<>();

                for(JsonElement elem : jsonArray1){

                    JsonObject object = elem.getAsJsonObject();

                    String tempResource = object.get("resource").getAsString();
                    Resource resource = Resource.getEnum(tempResource);
                    Integer value = object.get("value").getAsInt();

                    productionOut.put(resource, value);

                }

                WhiteMarbleLeaderCard leaderCard = new WhiteMarbleLeaderCard(cardType, victoryPoints, activationCost, productionOut);

                leaderCards.add(leaderCard);

            }

            if(jsonObject.get("type").getAsString().equals("production")){

                String tempcardType = jsonObject.get("type").getAsString();
                CardType cardType = CardType.getEnum(tempcardType);
                List<LeaderCardCost> activationCost = new ArrayList();

                for(JsonElement elem  : jsonArray1){

                    JsonObject object = elem.getAsJsonObject();

                    String tempCardColor = object.get("cardColor").getAsString();
                    CardColor cardColor = CardColor.getEnum(tempCardColor);
                    Integer cost = object.get("cost").getAsInt();

                    for(int i = 0; i < cost; i++)
                        activationCost.add(new LeaderCardCost(cardColor, Level.SECOND));
                }

                HashMap<Resource, Integer> productionIn = new HashMap<>();
                int outProductionResourceNum = jsonObject.get("resourceOut").getAsInt();
                int outProductionFaithPoints = jsonObject.get("faithOut").getAsInt();

                for(JsonElement elem : jsonArray1){

                    JsonObject object = elem.getAsJsonObject();

                    String tempResource = object.get("resource").getAsString();
                    Resource resource = Resource.getEnum(tempResource);
                    Integer value = object.get("value").getAsInt();
                    productionIn.put(resource, value);
                }

                ProdPowerLeaderCard leaderCard = new ProdPowerLeaderCard(cardType, victoryPoints, activationCost, productionIn, outProductionResourceNum, outProductionFaithPoints);

                leaderCards.add(leaderCard);

            }
        }

        return leaderCards;

    }
}
