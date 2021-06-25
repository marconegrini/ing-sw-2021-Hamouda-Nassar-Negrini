package it.polimi.ingsw.model;

import it.polimi.ingsw.enumerations.Resource;

import java.util.List;

/**
 * Storage represents a shelf inside Warehouse
 * capacity: the maximum space that can be occupied inside this storage
 * resources: an ArrayList that contains resources stored in this storage, with .size() <= capacity
 */
public class Storage {
    private Integer capacity;
    private List<Resource> resources;

    public Storage(Integer capacity, List<Resource> resources){
        this.capacity = capacity;
        this.resources = resources;
    }

    public Integer getCapacity(){
        return this.capacity;
    }

    public List<Resource> getResources(){
        return this.resources;
    }

    public Resource getResourceType(){
        return this.resources.get(0);
    }

    public void insert(List<Resource> resources){
        this.resources.addAll(resources);
    }
}
