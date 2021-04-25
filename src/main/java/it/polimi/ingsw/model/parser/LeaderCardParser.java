package it.polimi.ingsw.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;
import it.polimi.ingsw.model.cards.LeaderCards.*;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class LeaderCardParser extends Parser{

    private CardsCompositionMethods cardsCompositionMethods;

    public LeaderCardParser(String filePath) {
        super(filePath);
    }

    public ArrayList<LeaderCard> getLeaderCardsDeck(){

        cardsCompositionMethods=new CardsCompositionMethods();

        ArrayList<LeaderCard> leaderCards = new ArrayList<>();

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

                    if(jsonObject.get("type").getAsString().equals("discount")){

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


                        DiscountLeaderCard leaderCard = new DiscountLeaderCard(victoryPoints, activationCost, discountedResource);

                        leaderCards.add(leaderCard);
                    }

                    if(jsonObject.get("type").getAsString().equals("storage")){

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

                        leaderCards.add(leaderCard);

                    }

                    if(jsonObject.get("type").getAsString().equals("marble")){


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

                        WhiteMarbleLeaderCard leaderCard = new WhiteMarbleLeaderCard(victoryPoints, activationCost, productionOut);

                        leaderCards.add(leaderCard);

                    }

                    if(jsonObject.get("type").getAsString().equals("production")){

                        HashMap<LeaderCardCost, Integer> activationCost = new HashMap<>();

                        for(JsonElement elem  : jsonArray1){

                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            activationCost.put(new LeaderCardCost(cardColor, Level.ANY), cost);
                        }

                        HashMap<Resource, Integer> productionIn = new HashMap<>();
                        Integer outProductionResourceNum = jsonObject.get("resourceOut").getAsInt();
                        Integer outProductionFaithPoints = jsonObject.get("faithOut").getAsInt();

                        for(JsonElement elem : jsonArray2){

                            JsonObject object = elem.getAsJsonObject();

                            String tempResource = object.get("resource").getAsString();
                            Resource resource = Resource.getEnum(tempResource);
                            Integer value = object.get("value").getAsInt();
                            productionIn.put(resource, value);
                        }

                        ProdPowerLeaderCard leaderCard = new ProdPowerLeaderCard(victoryPoints, activationCost, productionIn, outProductionResourceNum, outProductionFaithPoints);

                        leaderCards.add(leaderCard);

                    }
                }
            }
        }
                return leaderCards;
    }

    public void close(){
        try{
            this.inputStream.close();
        } catch (IOException e){
            System.err.println("Exception while closing file");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LeaderCardParser parser = new LeaderCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/LeaderCardJson.json");
        ArrayList<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
    }
}
