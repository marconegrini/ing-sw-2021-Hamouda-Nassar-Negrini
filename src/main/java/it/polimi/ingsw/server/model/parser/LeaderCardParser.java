package it.polimi.ingsw.server.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.server.model.cards.LeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.*;
import it.polimi.ingsw.server.model.enumerations.CardColor;
import it.polimi.ingsw.server.model.enumerations.CardType;
import it.polimi.ingsw.server.model.enumerations.Level;
import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.server.model.cards.LeaderCards.WhiteMarbleLeaderCard;

import java.util.HashMap;
import java.util.Stack;

public class LeaderCardParser extends Parser{


    public LeaderCardParser(String filePath) {
        super(filePath);
    }

    public Stack<LeaderCard> getLeaderCardsDeck(){


        Stack<LeaderCard> leaderCards = new Stack();

        while (parser.hasNext()) {
            JsonElement element = parser.next();

            if (element.isJsonObject()) {

                JsonObject fileObject = element.getAsJsonObject();
                JsonArray jsonLeaderCards = fileObject.get("leaderCards").getAsJsonArray();

                for (JsonElement jsonElement : jsonLeaderCards) {

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    int victoryPoints = jsonObject.get("Vp").getAsInt();

                    JsonArray jsonArray1 = jsonObject.get("activationCost").getAsJsonArray();
                    JsonArray jsonArray2 = jsonObject.get("leaderPower").getAsJsonArray();

                    if(jsonObject.get("type").getAsString().equals("discount")){

                        String tempcardType = jsonObject.get("type").getAsString();
                        CardType cardType = CardType.getEnum(tempcardType);
                        HashMap<LeaderCardCost, Integer> activationCost = new HashMap<>();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(new LeaderCardCost(cardColor, Level.ANY), cost);
                        }

                        HashMap<Resource, Integer> discountedResource = new HashMap<>();

                        for(JsonElement elem : jsonArray2){
                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer discountValue = object.get("value").getAsInt();

                            discountedResource.put(resource, discountValue);

                        }


                        DiscountLeaderCard leaderCard = new DiscountLeaderCard(cardType ,victoryPoints, activationCost, discountedResource);

                        leaderCards.push(leaderCard);
                    }

                    if(jsonObject.get("type").getAsString().equals("storage")){

                        String tempcardType = jsonObject.get("type").getAsString();
                        CardType cardType = CardType.getEnum(tempcardType);

                        HashMap<Resource, Integer> activationCost = new HashMap<>();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(resource, cost);
                        }

                        HashMap<Resource, Integer> storage = new HashMap<>();

                        for(JsonElement elem : jsonArray2){
                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();

                            storage.put(resource, value);

                        }

                        StorageLeaderCard leaderCard = new StorageLeaderCard(cardType, victoryPoints, activationCost, storage);

                        leaderCards.push(leaderCard);

                    }

                    if(jsonObject.get("type").getAsString().equals("marble")){


                        String tempcardType = jsonObject.get("type").getAsString();
                        CardType cardType = CardType.getEnum(tempcardType);

                        HashMap<LeaderCardCost, Integer> activationCost = new HashMap<>();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(new LeaderCardCost(cardColor, Level.ANY), cost);
                        }

                        HashMap<Resource, Integer> productionOut = new HashMap<>();

                        for(JsonElement elem : jsonArray2){

                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();

                            productionOut.put(resource, value);

                        }

                        WhiteMarbleLeaderCard leaderCard = new WhiteMarbleLeaderCard(cardType, victoryPoints, activationCost, productionOut);

                        leaderCards.push(leaderCard);

                    }

                    if(jsonObject.get("type").getAsString().equals("production")){

                        String tempcardType = jsonObject.get("type").getAsString();
                        CardType cardType = CardType.getEnum(tempcardType);
                        HashMap<LeaderCardCost, Integer> activationCost = new HashMap<>();

                        for(JsonElement elem  : jsonArray1){

                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(new LeaderCardCost(cardColor, Level.SECOND), cost);
                        }

                        HashMap<Resource, Integer> productionIn = new HashMap<>();
                        int outProductionResourceNum = jsonObject.get("resourceOut").getAsInt();
                        int outProductionFaithPoints = jsonObject.get("faithOut").getAsInt();

                        for(JsonElement elem : jsonArray2){

                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();
                            productionIn.put(resource, value);
                        }

                        ProdPowerLeaderCard leaderCard = new ProdPowerLeaderCard(cardType, victoryPoints, activationCost, productionIn, outProductionResourceNum, outProductionFaithPoints);

                        leaderCards.push(leaderCard);

                    }
                }
            }
        }
                return leaderCards;
    }

    public static void main(String[] args) {
        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
    }
}
