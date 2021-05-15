package it.polimi.ingsw.client.ClientModel.parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.server.model.VaticanSection;

import java.util.ArrayList;
import java.util.HashMap;

public class FaithPathParser extends Parser {

    ArrayList<VaticanSection> vaticanSections;

    HashMap<Integer, Integer> victoryPoints;

    Integer end;

    public FaithPathParser(String filePath) {
        super(filePath);

        vaticanSections = new ArrayList<>();

        victoryPoints = new HashMap<>();

        while (parser.hasNext()) {
            JsonElement element = parser.next();

            if (element.isJsonObject()) {

                JsonObject fileObject = element.getAsJsonObject();

                JsonArray jsonRapportiVaticano = fileObject.get("rapportiVaticano").getAsJsonArray();

                end = 0;

                for (JsonElement jsonElement : jsonRapportiVaticano) {
                    JsonObject object = jsonElement.getAsJsonObject();
                    Integer startPos = object.get("start").getAsInt();
                    Integer spazioPapa = object.get("end").getAsInt();
                    Integer victoryPoints = object.get("vp").getAsInt();

                    if(spazioPapa > end)
                        end = spazioPapa;

                    VaticanSection vaticanSection = new VaticanSection(startPos, spazioPapa, victoryPoints);

                    vaticanSections.add(vaticanSection);
                }

                JsonArray jsonVictoryPoints = fileObject.get("victoryPoints").getAsJsonArray();

                for(JsonElement jsonElement : jsonVictoryPoints){
                    JsonObject object = jsonElement.getAsJsonObject();
                    Integer position = object.get("pos").getAsInt();
                    Integer vp = object.get("vp").getAsInt();

                    victoryPoints.put(position, vp);
                }
            }
        }
    }

    //public VaticanSection(Integer startPos, Integer spazioPapa, Integer victoryPoints)
    public ArrayList<VaticanSection> getVaticanSections(){
        return this.vaticanSections;
    }

    public HashMap<Integer, Integer> getFaithPathVictoryPoints(){
        return this.victoryPoints;
    }

    public Integer getEnd(){
        return this.end;
    }
}
