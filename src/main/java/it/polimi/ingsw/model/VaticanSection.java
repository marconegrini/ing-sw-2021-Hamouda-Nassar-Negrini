package it.polimi.ingsw.model;

public class VaticanSection {

    private Integer startPos;
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
        cardFlipped = true;
    }

    //the argument is someone else's position
    public boolean rapportoVaticano(Integer position){
        return position.equals(this.spazioPapa) && !activated;
    }

    public void activate(Integer userPosition){
        if(userPosition >= startPos && userPosition <= spazioPapa)
            this.flipCard();

        activated = true;
    }

    public Integer getVictoryPoints(){
        if(this.activated)
            return this.victoryPoints;
        else return 0;
    }

}
