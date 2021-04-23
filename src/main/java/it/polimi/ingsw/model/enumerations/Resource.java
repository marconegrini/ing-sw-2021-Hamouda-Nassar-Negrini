package it.polimi.ingsw.model.enumerations;

public enum Resource {
    COIN, SERVANT, SHIELD, STONE, FAITH;

    public static Resource getEnum(String value) {
        if(value.toUpperCase().equals(Resource.COIN.toString()))
            return Resource.COIN;
        else if(value.toUpperCase().equals(Resource.SERVANT.toString()))
            return Resource.SERVANT;
        else if(value.toUpperCase().equals(Resource.STONE.toString()))
            return Resource.STONE;
        else if(value.toUpperCase().equals(Resource.FAITH.toString()))
            return Resource.FAITH;
        else return Resource.SHIELD;
    }
}
