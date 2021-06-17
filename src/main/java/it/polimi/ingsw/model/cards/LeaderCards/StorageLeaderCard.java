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
    private ArrayList<Resource> storage;

    //the constructor of Storage leader cards from messages between server and client.
    public StorageLeaderCard(
            CardType cardType,
            int victoryPoints,
            boolean isActivated,
            boolean isDiscarded,
            Integer maxCapacity,
            HashMap<Resource, Integer> activationCost,
            HashMap<Resource, Integer> slots,
            ArrayList<Resource> storage) {
        this.cardType = cardType;
        this.Vp = victoryPoints;
        this.isActivated = isActivated;
        this.isDiscarded = isDiscarded;
        this.activationCost = activationCost;
        this.slots = slots;
        this.maxCapacity = maxCapacity;
        this.storage = storage;
    }


    //first instantiation of the leader cards in the game,, is being used just at the beginning.
    public StorageLeaderCard(
            CardType cardType,
            int victoryPoints,
            HashMap<Resource, Integer> activationCost,
            HashMap<Resource, Integer> slots) {
        this.cardType = cardType;
        this.Vp = victoryPoints;
        this.isActivated = false;
        this.isDiscarded = false;
        this.activationCost = activationCost;
        this.slots = slots;
        for(Resource res : slots.keySet())
            this.maxCapacity = slots.get(res);
        storage = new ArrayList<>(maxCapacity);
        //initialize all the elements of the arrayList with null elements.      STARTS WITH the INDEX 0
        for (int i = 0; i < maxCapacity; i++) {
            storage.add(null);
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
                if (coinOccurr < activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.STONE))
                if (stoneOccurr < activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.SERVANT))
                if (servantOccurr < activationCost.get(resource))
                    activatable = false;
            if (resource.equals(Resource.SHIELD))
                if (shieldOccurr < activationCost.get(resource))
                    activatable = false;
        }
        return activatable;
    }

    /**
     * methos that can be used to put a single resources or a list of resources to a storage leader card's storage
     *
     * @param resourceIn    a list of resources, when used the second argument should be "null"  .if want to add single resource --> "null" instead and put the single resource as the second argument.
     * @param oneResourceIn a single resource passed to the method to be added when used the first argument should be "null"
     * @throws StorageOutOfBoundsException storage out of bound
     * @throws IllegalInsertionException   illegal insertion when the type of the card is different from the resource passed or when the
     */
    public void putResourceInCardStorage(List<Resource> resourceIn, Resource oneResourceIn) throws  IllegalInsertionException {

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
            if ((storage.stream().filter(Objects::nonNull).count() == maxCapacity))
                throw new IllegalInsertionException();
            //switch case: storage is filled partially
            if (storage.stream().anyMatch(Objects::nonNull)) {

                //checking that resource type in the storage is the same of resourceIn
                System.out.println("LeaderCard/StorageLeaderCard  slots.keySet().stream().findAny().get(): "  + slots.keySet().stream().findAny().get());
                System.out.println("LeaderCard/StorageLeaderCard  slots.keySet().stream().findAny().get().getClass(): " + slots.keySet().stream().findAny().get().getClass());

                System.out.println("LeaderCard/StorageLeaderCard  resourceIn.stream().findAny(): " + resourceIn.stream().findAny());
                System.out.println("LeaderCard/StorageLeaderCard  resourceIn.stream().findAny().getClass(): " + resourceIn.stream().findAny().getClass());

                if (!(slots.keySet().stream().filter(Objects::nonNull).findAny().get().equals(resourceIn.stream().findAny().get())))
                    throw new IllegalInsertionException();


                //checking that there is enough capacity left to insert resourceIn

                if ((storage.stream().filter(Objects::nonNull).count() + resourceIn.size()) <= maxCapacity) {
                    int resourcesToAdd = resourceIn.size();
                    for (int i = 0; i < maxCapacity; i++) { //Optional<Resource> optRrs : storage
                        if (storage.get(i)==null && resourcesToAdd > 0) {
                            storage.add(i, resourceInType );
                            resourcesToAdd--;
                        }
                    }


                } else throw new IllegalInsertionException();
                //switch case: the chosen shelf is empty
            } else if ((storage.stream().filter(Objects::nonNull).count() + resourceIn.size()) <= maxCapacity) {
                int resourcesToAdd = resourceIn.size();
                for (int i = 0; i < maxCapacity; i++) { //Optional<Resource> optRrs : storage
                    if (resourcesToAdd > 0) {
                        storage.add(i, resourceInType);
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
        Optional<Resource> tempOpt = storage.stream().filter(Objects::nonNull).findFirst();
        if (tempOpt.equals(Optional.empty())) {
            throw new EmptySlotException();
        }else tempResource = tempOpt.get();  //if there is at least one shelf non empty--> get tha=e resource in the shelf

        //clearing the resource in that shelf
//        storage.stream().filter(Objects::nonNull).findFirst().map(x -> x = null);
        for (int i=0;i<maxCapacity; i++){
            if (storage.get(i)!=null) {
                storage.remove(i); //the last loop does not depend on the array's size
                break;
            }
        }
        return tempResource;

    }

//    public int getCardStorageFilledSlots() {
//        return (int) storage.stream().filter(x -> x != null).count();
//    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public Integer occurrences(Resource resource, List<Resource> resources) {
        return Math.toIntExact(resources.stream().filter(x -> x.equals(resource)).count());
    }

    public HashMap<Resource, Integer> getActivationCost() {
        return activationCost;
    }


    /**
     * @return returns a hashMap with the resources saved in the leaderCard
     *  EmptySlotException -- when all the slots are empty
     */
    @Override
    public HashMap<Resource, Integer> getLeaderCardPower() {
        int presentResources = (int) storage.stream().filter(Objects::nonNull).count();

        //putting the return value into a hashMap
        HashMap<Resource, Integer> tempHash = new HashMap<>();
        if (presentResources > 0) {
            tempHash.put(storage.stream().filter(Objects::nonNull).findAny().get(), presentResources);
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
        return (int) storage.stream().filter(Objects::nonNull).count();
    }


    public List<Resource> getStoredResources(){
        List<Resource> res = new ArrayList<>();

        res = storage.stream().filter(Objects::nonNull).collect(Collectors.toList());

        return res;
    }

    /**
     *
     * @return the type of the resource that a Storage Leader Card can contain.
     */
    public Resource storageType() {
        return slots.keySet().stream().findFirst().get();
    }

    public boolean hasAvailableSlots(){
        return getOccupiedSlots() < maxCapacity;
    }


    @Override
    public String toString() {
        return "\nCard type: " + this.cardType +
                "\nVictory points: " + this.Vp +
                "\nAvailable storage: " + slots.toString() +
                "\nActivation cost: " + activationCost.toString();
    }

    /**
     * This method transform a card to a String that represent the image name in the resources directory. It will be used to upload the image of the card in the GUI
     * @return  a String that will be used for the path of the image
     */
    @Override
    public String toPath() {
        return "storage" +
                slots.keySet().iterator().next().toString().charAt(0) +
                slots.keySet().iterator().next().toString().substring(1).toLowerCase();
    }


    }

