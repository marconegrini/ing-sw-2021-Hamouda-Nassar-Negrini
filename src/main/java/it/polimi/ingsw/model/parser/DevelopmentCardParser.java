package it.polimi.ingsw.model.parser;

import com.google.gson.*;
import it.polimi.ingsw.model.cards.DevelopmentCard;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Color;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DevelopmentCardParser extends Parser {

    public DevelopmentCardParser(String filePath){
        super(filePath);
    }

    public ArrayList<DevelopmentCard> getDevelopmentCard() {

        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();

        while (parser.hasNext()) {
            JsonElement element = parser.next();

            if (element.isJsonObject()) {

                JsonObject fileObject = element.getAsJsonObject();

                Integer victoryPoints = fileObject.get("Vp").getAsInt();
                String colorTemp = fileObject.get("color").getAsString();
                CardColor cardColor = CardColor.getEnum(colorTemp);
                String levelTemp = fileObject.get("level").getAsString();
                Level level = Level.getEnum(levelTemp);

                JsonArray jsonCardCostArray = fileObject.get("cardCost").getAsJsonArray();
                HashMap<Resource, Integer> cardCost = new HashMap<>();

                for (JsonElement jsonElement : jsonCardCostArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                /*
                if(jsonObject.has("color")){
                    String color = jsonObject.get("color").getAsString();
                }
                 */

                    //extracting data
                    String resourceTemp = jsonObject.get("resource").getAsString();
                    Resource resource = Resource.getEnum(resourceTemp);
                    Integer cost = jsonObject.get("cost").getAsInt();
                    cardCost.put(resource, cost);
                }

                JsonArray jsonProductionInArray = fileObject.get("productionIn").getAsJsonArray();
                HashMap<Resource, Integer> productionIn = new HashMap<>();

                for (JsonElement jsonElement : jsonProductionInArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                /*
                if(jsonObject.has("color")){
                    String color = jsonObject.get("color").getAsString();
                }
                 */

                    //extracting data
                    String resourceTemp = jsonObject.get("resource").getAsString();
                    Resource resource = Resource.getEnum(resourceTemp);
                    Integer cost = jsonObject.get("cost").getAsInt();
                    productionIn.put(resource, cost);
                }

                JsonArray jsonProductionOutArray = fileObject.get("productionOut").getAsJsonArray();
                HashMap<Resource, Integer> productionOut = new HashMap<>();

                for (JsonElement jsonElement : jsonProductionOutArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                /*
                if(jsonObject.has("color")){
                    String color = jsonObject.get("color").getAsString();
                }
                 */

                    //extracting data
                    String resourceTemp = jsonObject.get("resource").getAsString();
                    Resource resource = Resource.getEnum(resourceTemp);
                    Integer cost = jsonObject.get("cost").getAsInt();
                    productionOut.put(resource, cost);
                }

                DevelopmentCard developmentCard = new DevelopmentCard(victoryPoints, cardColor, level, cardCost, productionIn, productionOut);
                developmentCards.add(developmentCard);
            }
        }

        return developmentCards;
    }

    public static void main(String[] args) {
        DevelopmentCardParser parser = new DevelopmentCardParser("src/main/java/it/polimi/ingsw/model/jsonFiles/DevCardJson.json");
        ArrayList<DevelopmentCard> deck = parser.getDevelopmentCard();
        try{
            parser.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }



}
