package it.polimi.ingsw.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WarehouseParser extends Parser{

    public WarehouseParser() {
        this.reader = new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("/CardSlotsWarehouse.json")));
        this.parser = new JsonStreamParser(this.reader);
    }

    public Map<Integer, Integer> getStorageNumAndCapacity(){

        Map<Integer, Integer> storageNumAndCapacity = new HashMap();

        while(parser.hasNext()){
            JsonElement element = parser.next();

            if(element.isJsonObject()){

                JsonObject fileObject = element.getAsJsonObject();
                JsonArray jsonArray = fileObject.get("warehouse").getAsJsonArray();

                for(JsonElement jsonElement : jsonArray){

                    JsonObject jsonObject = jsonElement.getAsJsonObject();

                    Integer storageNum = jsonObject.get("storageNum").getAsInt();
                    Integer storageCapacity = jsonObject.get("storageCapacity").getAsInt();

                    storageNumAndCapacity.put(storageNum, storageCapacity);
                }
            }
        }

        return storageNumAndCapacity;
    }
}
