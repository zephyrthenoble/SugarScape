
public class Sugar implements Updateable
{
//The global maximum sugar
   public static int MAX_SUGAR = 4;
   public static int ID = 0;

//the current amout of sugar
   private double sugar = 0;
//this is the maximum amount of sugar that can grow here
   private double saturated = (int)(Math.random()*(MAX_SUGAR+1));
   //the rate stuff grows back
   private double rate=1;
//static plots are not affected by seasons if there are seasons
   private boolean static_plot = false;

   private int xpos;
   private int ypos;
   private int id;

   private boolean remove = false;


   public Sugar(int xpos, int ypos)
   {
      this.xpos = xpos;
      this.ypos = ypos;
   
      add();
   }
      
   public Sugar(int xpos, int ypos, double sugar, double saturated)
   {
      this.xpos = xpos;
      this.ypos = ypos;
      this.sugar = sugar;
      this.saturated = saturated;
      add();
   }
   public Sugar()
   {
      int iter = 0;
      do{
         if(SugarPanel.verbose && iter>=1)
            System.out.println(String.format("intersect at (%d,%d) with sugar %d", xpos, ypos, SugarPanel.getSugarGrid()[xpos][ypos].id));
         this.xpos = (int)(Math.random()*SugarPanel.getSugarGrid().length);
         this.ypos = (int)(Math.random()*SugarPanel.getSugarGrid()[0].length);
         iter++;
      }
      while(SugarPanel.getSugarGrid()[this.xpos][this.ypos] !=null);
      
   
      add();
   }
   
   public void edit(double sugar, double saturated)
   {
   
      this.sugar = sugar;
      this.saturated = saturated;
   
   }
   public static Sugar empty_plot(int x,int y)
   {
      return new Sugar(x,y,0,0);
   }
   public void add()
   {
      id = Sugar.ID;
      Sugar.ID++;
      SugarPanel.getSugarGrid()[xpos][ypos] = this;
      SugarPanel.getSugar().add(this);
   }
   public int getID(){
      return id;}
   public int getX(){
      return xpos;} 
   public int getY(){
      return ypos;}
   public double getSugar(){
      return sugar;}
   public double getMaxSugar(){
      return saturated;}
   public Agent getAgent(){
      if(SugarPanel.getAgentGrid() == null)
         return null;
      else
         return SugarPanel.getAgentGrid()[getX()][getY()];
   }
   public String getAgentID(){
      if(getAgent() != null)
         return ""+getAgent().getID();
      else
         return "none";
   }
   public void removeSugar(){
      sugar=0;}
   public boolean removable(){
      return remove;}
   public void update()
   {
      if(SugarPanel.getAgentGrid()[getX()][getY()] == null)
      {
         if (sugar < saturated)
            sugar +=rate;
         if (sugar > saturated)
            sugar = saturated;
      }
   }
   public String toString()
   {
      return String.format("Sugar %d at (%d, %d) with %f sugar", getID(), getX(), getY(), getSugar());
   }
}