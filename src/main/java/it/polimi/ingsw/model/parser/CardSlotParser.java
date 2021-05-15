package it.polimi.ingsw.model.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class CardSlotParser extends Parser{
    public CardSlotParser(String filePath) {
        super(filePath);
    }

    public Integer getCardSlotsNumber(){

        Integer cardSlotsNumber = 0;

        while(parser.hasNext()){

            JsonElement element = parser.next();

            if(element.isJsonObject()){

                JsonObject fileObject = element.getAsJsonObject();
                cardSlotsNumber = fileObject.get("devCardSlotNum").getAsInt();
            }
        }

        return cardSlotsNumber;
    }



}
