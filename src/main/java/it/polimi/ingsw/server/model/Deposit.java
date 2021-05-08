package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enumerations.Resource;
import it.polimi.ingsw.server.model.exceptions.UnsufficientResourcesException;

import java.util.List;

public interface Deposit {

    void pullResource(List<Resource> resourcesToTake) throws UnsufficientResourcesException;

    boolean checkAvailability(List<Resource> resourcesToTake);

    List<Resource> getTotalResources();

    Integer occurrences(Resource resource, List<Resource> resources);
}
