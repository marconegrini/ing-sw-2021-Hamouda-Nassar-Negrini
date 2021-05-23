package it.polimi.ingsw.model.cards.LeaderCards;

import it.polimi.ingsw.model.cards.LeaderCard;
import it.polimi.ingsw.model.enumerations.CardType;
import it.polimi.ingsw.model.enumerations.Resource;
import it.polimi.ingsw.model.exceptions.EmptySlotException;
import it.polimi.ingsw.model.exceptions.IllegalInsertionException;
import it.polimi.ingsw.model.exceptions.StorageOutOfBoundsException;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class StorageLeaderCard extends LeaderCard {

    private final HashMap<Resource, Integer> activationCost;
    private final HashMap<Resource, Integer> slots;
    private Integer maxCapacity = 0;
    private ArrayList<Optional<Resource>> storage;

    public StorageLeaderCard(
            CardType cardType,
            int victoryPoints,
            HashMap<Resource, Integer> activationCost,
            HashMap<Resource, Integer> slots) {
        this.cardType = cardType;
        this.Vp = victoryPoints;
        this.isActivated = false;
        this.activationCost = activationCost;
        this.slots = slots;
        maxCapacity = slots.values().stream().findAny().get();
        storage = new ArrayList<>(maxCapacity);
        //initialize all the elements of the arrayList with null elements.      STARTS WITH the ELEMENT 0
        for (int i = 0; i < maxCapacity; i++) {
            storage.add(Optional.empty());
        }
    }

    public boolean isActivatable(List<Resource> resources) {
        boolean activatable = true;
        Integer coinOccurr = occurrences(Resource.COIN, resources);
        Integer stoneOccurr = occurrences(Resource.STONE, resources);
        Integer servantOccurr = occurrences(Resource.SERVANT, resources);
        Integer shieldOccurr = occurrences(Resource.SHIELD, resources);
        Set<Resource> resourceCost = activationCost.keySet();
        for (Resource resource : resourceCost) {
            if (resource.equals(Resource.COIN))
                if (coinOccurr >= activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.STONE))
                if (stoneOccurr >= activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.SERVANT))
                if (servantOccurr >= activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.SHIELD))
                if (shieldOccurr >= activationCost.get(resource))
                    activatable = false;
        }
        return activatable;
    }

    /**
     * methos that can be used to a single resources or a list of resources to a storage leader card's storage
     *
     * @param resourceIn    a list of resources, when used the second argument should be "null"  .if want to add single resource --> "null" instead and put the single resource as the second argument.
     * @param oneResourceIn a single resource passed to the method to be added when used the first argument should be "null"
     * @throws StorageOutOfBoundsException storage out of bound
     * @throws IllegalInsertionException   illegal insertion when the type of the card is different from the resource passed or when the
     */
    public void putResourceInCardStorage(List<Resource> resourceIn, Resource oneResourceIn) throws StorageOutOfBoundsException, IllegalInsertionException {


        if (resourceIn == null) {
            if (oneResourceIn != null)
                resourceIn = new ArrayList<>();
            assert resourceIn != null;
            resourceIn.add(oneResourceIn);
        }
        assert resourceIn.size() > 0;
        Resource resourceInType = resourceIn.stream().findAny().get();

        if (!resourceIn.isEmpty()) {
            if (resourceIn.size() > maxCapacity) throw new IllegalInsertionException();
            //checking that resourcesIn contains resources of the same type
            List<Resource> finalResourceIn = resourceIn;
            if (!(resourceIn.stream().filter(x -> x.equals(finalResourceIn.get(0))).count() == resourceIn.size()))
                throw new IllegalInsertionException();

            //switch case: the storage is full
            if ((storage.stream().filter(Optional::isPresent).count() == maxCapacity))
                throw new IllegalInsertionException();
            //switch case: storage is filled partially
            if (storage.stream().findAny().get().isPresent()) {

                //checking that resource type in the storage is the same of resourceIn
                System.out.println(slots.keySet().stream().findAny().get());
                System.out.println(slots.keySet().stream().findAny().get().getClass());

                System.out.println(resourceIn.stream().findAny());
                System.out.println(resourceIn.stream().findAny().getClass());

                if (!(slots.keySet().stream().findAny().get().equals(resourceIn.stream().findAny().get())))
                    throw new IllegalInsertionException();


                //checking that there is enough capacity left to insert resourceIn

                if ((storage.stream().filter(Optional::isPresent).count() + resourceIn.size()) <= maxCapacity) {
                    int resourcesToAdd = resourceIn.size();
                    for (int i = 0; i < storage.size(); i++) { //Optional<Resource> optRrs : storage
                        if (storage.get(i).isEmpty() && resourcesToAdd > 0) {
                            storage.add(i, Optional.of(resourceInType));
                            resourcesToAdd--;
                        }
                    }


                } else throw new IllegalInsertionException();
            } else if ((storage.stream().filter(Optional::isPresent).count() + resourceIn.size()) <= maxCapacity) {
                int resourcesToAdd = resourceIn.size();
                for (int i = 0; i < storage.size(); i++) { //Optional<Resource> optRrs : storage
                    if (storage.get(i).isEmpty() && resourcesToAdd > 0) {
                        storage.add(i, Optional.of(resourceInType));
                        resourcesToAdd--;
                    }
                }
            }
        }
    }

    /**
     * method that pulls one resource from a leader card and remove it from tha card slot
     *
     * @return returns a resource and remove it from a slot in the card.
     * @throws EmptySlotException when all the slots are empty
     */
    public Resource pullResource() throws EmptySlotException {
        Resource tempResource;
        tempResource = storage.stream().filter(x -> x.isPresent()).findFirst().get().get();
        if (tempResource.equals(Optional.empty())) {
            throw new EmptySlotException();
        }

        storage.stream().filter(x -> x.isPresent()).findFirst().map(x -> x = Optional.empty());
        return tempResource;

    }

    public int getCardStorageFilledSlots() {
        return (int) storage.stream().filter(x -> x.isPresent()).count();
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public Integer occurrences(Resource resource, List<Resource> resources) {
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }

    public HashMap<Resource, Integer> getSlots() {
        return slots;
    }

    /**
     * @return returns a hashMap with the resources saved in the leaderCard
     * @throws EmptySlotException when all the slots are empty
     */
    @Override
    public HashMap<Resource, Integer> getLeaderCardPower() {
        int presentResources = (int) storage.stream().filter(Optional::isPresent).count();
        HashMap<Resource, Integer> tempHash = new HashMap<>();
        if (presentResources > 0) {
            tempHash.put(storage.stream().findAny().get().get(), presentResources);
        } else {
            try {
                throw new EmptySlotException();
            } catch (EmptySlotException e) {
                e.printStackTrace();
            }
        }
        return tempHash;
    }

    public int getOccupiedSlots() {
        return (int) storage.stream().filter(Optional::isPresent).count();
    }

    public Resource storageType() {
        return slots.keySet().stream().findFirst().get();
    }


    @Override
    public String toString() {
        return "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nAvailable storage: " + slots.toString() +
                "\nActivation cost: " + activationCost.toString();
    }
}
