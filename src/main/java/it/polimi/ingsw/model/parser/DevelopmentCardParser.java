package it.polimi.ingsw.model.parser;

import com.google.gson.*;
import it.polimi.ingsw.model.enumerations.CardColor;
import it.polimi.ingsw.model.enumerations.Level;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.cards.DevelopmentCard;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DevelopmentCardParser extends Parser {

    public DevelopmentCardParser(){
        this.reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/DevCardJson.json")));
        this.parser = new JsonStreamParser(this.reader);
    }

    public ArrayList<DevelopmentCard> getDevelopmentCardsDeck() {

        ArrayList<DevelopmentCard> developmentCards = new ArrayList<>();

        while (parser.hasNext()) {
            JsonElement element = parser.next();

            if (element.isJsonObject()) {

                JsonObject fileObject = element.getAsJsonObject();
                JsonArray jsonDevelopmentCards = fileObject.get("developmentCards").getAsJsonArray();

                for(JsonElement jsonElement : jsonDevelopmentCards){
                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    Integer victoryPoints = jsonObject.get("Vp").getAsInt();
                    String colorTemp = jsonObject.get("color").getAsString();
                    CardColor cardColor = CardColor.getEnum(colorTemp);
                    String levelTemp = jsonObject.get("level").getAsString();
                    Level level = Level.getEnum(levelTemp);

                    JsonArray jsonCardCostArray = jsonObject.get("cardCost").getAsJsonArray();
                    HashMap<Resource, Integer> cardCost = new HashMap<>();

                    for (JsonElement jsonArray : jsonCardCostArray) {
                        JsonObject object = jsonArray.getAsJsonObject();

                        String resourceTemp = object.get("resource").getAsString();
                        Resource resource = Resource.getEnum(resourceTemp);
                        Integer cost = object.get("cost").getAsInt();
                        cardCost.put(resource, cost);
                    }

                    JsonArray jsonProductionInArray = jsonObject.get("productionIn").getAsJsonArray();
                    HashMap<Resource, Integer> productionIn = new HashMap<>();

                    for(JsonElement jsonArray : jsonProductionInArray){
                        JsonObject object = jsonArray.getAsJsonObject();

                        //extracting data
                        String resourceTemp = object.get("resource").getAsString();
                        Resource resource = Resource.getEnum(resourceTemp);
                        Integer cost = object.get("cost").getAsInt();
                        productionIn.put(resource, cost);
                    }

                    JsonArray jsonProductionOutArray = jsonObject.get("productionOut").getAsJsonArray();
                    HashMap<Resource, Integer> productionOut = new HashMap<>();

                    for(JsonElement jsonArray : jsonProductionOutArray){
                        JsonObject object = jsonArray.getAsJsonObject();
                        //extracting data
                        String resourceTemp = object.get("resource").getAsString();
                        Resource resource = Resource.getEnum(resourceTemp);
                        Integer cost = object.get("cost").getAsInt();
                        productionOut.put(resource, cost);
                    }

                    DevelopmentCard developmentCard = new DevelopmentCard(victoryPoints, cardColor, level, cardCost, productionIn, productionOut);
                    developmentCards.add(developmentCard);


                }
            }
        }

        return developmentCards;
    }
}
