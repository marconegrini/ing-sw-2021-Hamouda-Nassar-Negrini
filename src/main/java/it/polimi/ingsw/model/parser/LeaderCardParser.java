package it.polimi.ingsw.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderCardParser extends Parser{


    public LeaderCardParser(String filePath) {
        super(filePath);
    }

    public ArrayList<LeaderCard> getLeaderCardsDeck(){
        ArrayList<DevelopmentCard> leaderCards = new ArrayList<>();

        while (parser.hasNext()) {
            JsonElement element = parser.next();

            if (element.isJsonObject()) {

                JsonObject fileObject = element.getAsJsonObject();
                JsonArray jsonLeaderCards = fileObject.get("leaderCards").getAsJsonArray();

                for (JsonElement jsonElement : jsonLeaderCards) {

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    Integer victoryPoints = jsonObject.get("Vp").getAsInt();

                    JsonArray jsonArray1 = jsonObject.get("activationCost").getAsJsonArray();
                    JsonArray jsonArray2 = jsonObject.get("leaderPower").getAsJsonArray();


                    if(jsonObject.get("type").equals("discount")){

                        HashMap<CardColor, Integer> activationCost = new HashMap();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(cardColor, cost);
                        }

                        HashMap<Resource, Integer> discountedResource = new HashMap();

                        for(JsonElement elem : jsonArray2){
                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer discountValue = object.get("value").getAsInt();

                            discountedResource.put(resource, discountValue);

                        }

                        DiscountLeaderCard leaderCard = new DiscountLeaderCard(victoryPoints, activationCost, discountedResource);


                    }

                    if(jsonObject.get("type").equals("storage")){

                        HashMap<Resource, Integer> activationCost = new HashMap();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(resource, cost);
                        }

                        HashMap<Resource, Integer> storage = new HashMap();

                        for(JsonElement elem : jsonArray2){
                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();

                            storage.put(resource, value);

                        }

                        StorageLeaderCard leaderCard = new StorageLeaderCard(victoryPoints, activationCost, storage);

                    }

                    if(jsonObject.get("type").equals("marble")){


                        HashMap<CardColor, Integer> activationCost = new HashMap();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(cardColor, cost);
                        }

                        HashMap<Resource, Integer> productionOut = new HashMap();

                        for(JsonElement elem : jsonArray2){

                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();

                            productionOut.put(resource, value);

                        }

                        WhiteMarbleLeaderCard leaderCard = new WhiteMarbleLeaderCard(victoryPoints, activationCost, productionOut);




                    }

                    if(jsonObject.get("type").equals("production")){

                        HashMap<CardColor, Integer> activationCost = new HashMap();

                        for(JsonElement elem  : jsonArray1){

                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(cardColor, cost);
                        }

                        HashMap<Resource, Integer> productionOut = new HashMap();

                        for(JsonElement elem : jsonArray2){

                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();

                            productionOut.put(resource, value);

                        }

                        WhiteMarbleLeaderCard leaderCard = new WhiteMarbleLeaderCard(victoryPoints, activationCost, productionOut);


                    }



                }
            }
        }



                return null;
    }
}
