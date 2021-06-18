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

    /**
     * flips (or activate) the papal card in this Vatican Section
     */
    public void flipCard(){
        this.cardFlipped = true;
    }

    /**
     *
     * @param position someone else's position in faiht path
     * @return true if specified position activates a rapporto in vaticano, else otherwise.
     */
    public boolean rapportoVaticano(Integer position){
        return position.equals(this.spazioPapa) && !this.activated;
    }

    /**
     * @param userPosition if rapporto in vaticano returns true, the following method is invoked. If the caller (the user)
     *                     is inside the Vatican Section (this) in which the rapporto in vaticano has been activated, the papal
     *                     favor card is flipped. Otherwise, the card is not flipped but the Vatican Section (this) is activated,
     *                     so it will not be possible to activate another rapporto in vatican in this Vatican Section.
     */
    public void activate(Integer userPosition){
        //if the user position is inside vatican section, papalFavorCard is flipped
        if(userPosition >= this.startPos && userPosition <= this.spazioPapa)
            this.flipCard();

        //in any case, the vatican section is activated
        this.activated = true;
    }

    /**
     * Returns victory points given by the papal favor card inside this rapporto in vaticano
     * @return
     */
    public Integer getVictoryPoints(){
        if(this.cardFlipped)
            return this.victoryPoints;
        else return 0;
    }

    public Integer getStartPos(){
        return this.startPos;
    }

    public Integer getSpazioPapa() {
        return spazioPapa;
    }

    public boolean isActivated() {
        return activated;
    }

    public boolean isCardFlipped() {
        return cardFlipped;
    }
}
