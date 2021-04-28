package it.polimi.ingsw.model;

import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.UnsufficientResourcesException;

import java.util.HashMap;
import java.util.List;

public interface Deposit {

    void pullResource(List<Resource> resourcesToTake) throws UnsufficientResourcesException;

    boolean checkAvailability(List<Resource> resourcesToTake);

    List<Resource> getTotalResources();

    Integer occurrences(Resource resource, List<Resource> resources);
}
