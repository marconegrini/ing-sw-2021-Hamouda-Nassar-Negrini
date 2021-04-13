package it.polimi.ingsw.model.enumerations;

public enum LorenzoCards{
  DISCARD2GREENDVCARDS{
    @Override
    public void onPool() {
     System.out.println("Picked Up a"+ DISCARD2GREENDVCARDS);
    }
  },
 DISCARD2YELLOWDVCARDS{
  @Override
  public void onPool() {
   System.out.println("Picked Up a"+ DISCARD2YELLOWDVCARDS);
  }
 },
 DISCARD2BLUEDVCARDS{
  @Override
  public void onPool() {
   System.out.println("Picked Up a"+ DISCARD2BLUEDVCARDS);
  }
 },
 DISCARD2VIOLETDVCARDS{
  @Override
  public void onPool() {
   System.out.println("Picked Up a"+ DISCARD2VIOLETDVCARDS);
  }
 },
 TWOFAITHPOINTSCARD{
  @Override
  public void onPool() {
   System.out.println("Picked Up a"+ TWOFAITHPOINTSCARD);
  }
 },
 FAITHANDSHUFFLECARD{
  @Override
  public void onPool() {
   System.out.println("Picked Up a"+ FAITHANDSHUFFLECARD);
  }
 };



 public abstract void onPool();
}
