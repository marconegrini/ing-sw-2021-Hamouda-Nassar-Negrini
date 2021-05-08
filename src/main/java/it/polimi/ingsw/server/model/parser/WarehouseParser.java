package it.polimi.ingsw.server.model.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class WarehouseParser extends Parser{

    public WarehouseParser(String filePath) {
        super(filePath);
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
