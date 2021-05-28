package it.polimi.ingsw.model;

public class VaticanSection {

    //start pos
    private Integer startPos;
    //end pos
    private Integer spazioPapa;

    private boolean activated;
    private boolean cardFlipped;
    private Integer victoryPoints;

    public VaticanSection(Integer startPos, Integer spazioPapa, Integer victoryPoints){
        this.startPos = startPos;
        this.spazioPapa = spazioPapa;
        this.victoryPoints = victoryPoints;
        this.activated = false;
        this.cardFlipped = false;
    }

    public void flipCard(){
        this.cardFlipped = true;
    }

    //the argument is someone else's position
    public boolean rapportoVaticano(Integer position){
        return position.equals(this.spazioPapa) && !this.activated;
    }

    public void activate(Integer userPosition){
        //if the user position is inside vatican section, papalFavorCard is flipped
        if(userPosition >= this.startPos && userPosition <= this.spazioPapa)
            this.flipCard();

        //in any case, the vatican section is activated
        this.activated = true;
    }

    public Integer getVictoryPoints(){
        if(this.cardFlipped)
            return this.victoryPoints;
        else return 0;
    }

}
