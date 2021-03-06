package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Resource;
import it.polimi.ingsw.exceptions.InsufficientResourcesException;

import java.util.List;

/**
 * interface implemented by warehouse and coffer
 */
public interface Deposit {

    void pullResource(List<Resource> resourcesToTake) throws InsufficientResourcesException;

    boolean checkAvailability(List<Resource> resourcesToTake);

    List<Resource> getTotalResources();

    Integer occurrences(Resource resource, List<Resource> resources);
}
