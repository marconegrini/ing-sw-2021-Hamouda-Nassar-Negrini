package it.polimi.ingsw.model.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.InputStreamReader;
import java.util.Objects;

public class CardSlotParser extends Parser{
    public CardSlotParser() {
        this.reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/CardSlotsWarehouse.json")));
        this.parser = new JsonStreamParser(this.reader);
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
