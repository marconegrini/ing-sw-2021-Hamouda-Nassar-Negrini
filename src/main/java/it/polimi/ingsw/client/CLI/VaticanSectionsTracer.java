package it.polimi.ingsw.client.CLI;

import it.polimi.ingsw.model.VaticanSection;

import java.util.List;

public class VaticanSectionsTracer {

    public void showVaticanSections(List<VaticanSection> vaticanSections){
        System.out.println("\n\t # Vatican Sections # \t");
        for(int i = 0; i < vaticanSections.size(); i++){
            String isActivated = "";
            if(vaticanSections.get(i).isActivated())
                isActivated = " has been activated:";
            else isActivated = " hasn't been activated:";
            System.out.println("\nVatican section number " + (i+1) + isActivated);
            if(vaticanSections.get(i).isCardFlipped() && vaticanSections.get(i).isActivated())
                System.out.println("you obtained " + vaticanSections.get(i).getVictoryPoints() + ".");
            else if (!vaticanSections.get(i).isCardFlipped() && vaticanSections.get(i).isActivated())
                System.out.println("you have not obtained victory points.");
            else System.out.println("you have not obtained victory points yet.");
            System.out.println("Starts at position: " + vaticanSections.get(i).getStartPos());
            System.out.println("Ends with a Spazio Papa at position: " + vaticanSections.get(i).getSpazioPapa());
        }
    }
}
