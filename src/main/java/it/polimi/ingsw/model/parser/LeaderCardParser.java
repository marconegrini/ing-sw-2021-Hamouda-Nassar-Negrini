package it.polimi.ingsw.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;
import it.polimi.ingsw.model.cards.LeaderCards.DiscountLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.ProdPowerLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.StorageLeaderCard;
import it.polimi.ingsw.model.cards.LeaderCards.WhiteMarbleLeaderCard;
import it.polimi.ingsw.enumerations.CardColor;
import it.polimi.ingsw.enumerations.CardType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.cards.LeaderCardCost;

import java.io.InputStreamReader;
import java.util.*;

public class LeaderCardParser extends Parser{


    public LeaderCardParser() {
        this.reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/LeaderCardJson.json")));
        this.parser = new JsonStreamParser(this.reader);
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
                        List<LeaderCardCost> activationCost = new ArrayList<>();

                        for(JsonElement elem  : jsonArray1){
                            JsonObject object = elem.getAsJsonObject();

                            String tempCardColor = object.get("cardColor").getAsString();
                            CardColor cardColor = CardColor.getEnum(tempCardColor);
                            Integer cost = object.get("cost").getAsInt();

                            for(int i = 0; i < cost; i++)
                                activationCost.add(new LeaderCardCost(cardColor, Level.ANY));
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
        LeaderCardParser parser = new LeaderCardParser();
        Stack<LeaderCard> leaderCards = parser.getLeaderCardsDeck();
        parser.close();
    }
}
