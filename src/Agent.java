import java.util.ArrayList;

public class Agent implements Updateable
{
   public static final int METABOLISM_MAX = 4;
   public static final int SUGAR_MAX = 20;
   public static int ID = 0;
   
   private int xpos = 0;
   private int ypos = 0;
   private int metabolism = (int)(Math.random()*METABOLISM_MAX)+1;
   private double sugar;
   private int age = 0;
   private int death_age = 100 + (int)(Math.random()*100);
   private double reproduction_rate=Math.random()/20; 
   private int id;
   private boolean remove = false;
   public Agent(int xpos, int ypos)
   {
      this.xpos = xpos;
      this.ypos = ypos;
   
   
      add();
   }
   public Agent()
   {
      int iter = 0;
      do{
         if(SugarPanel.verbose && iter>=1)
            System.out.println(String.format("intersect at (%d,%d) with agent %d", xpos, ypos, SugarPanel.agent_grid[xpos][ypos].id));
         this.xpos = (int)(Math.random()*SugarPanel.agent_grid.length);
         this.ypos = (int)(Math.random()*SugarPanel.agent_grid[0].length);
         iter++;
      }
      while(SugarPanel.agent_grid[this.xpos][this.ypos] !=null);
      
     
      add();
   }
   public void add()
   {
      id = Agent.ID;
      Agent.ID++;
      sugar = (int)(Math.random()*10) + 5;
      SugarPanel.agent_grid[xpos][ypos] = this;
      SugarPanel.agents.add(this);
   }
   public void update()
   {
      Sugar s = search();
      if(SugarPanel.verbose) System.out.println("Going to "+s);
      if (s!=null)
      {
         moveTo(s);
      }
      else if(SugarPanel.verbose)
         System.out.println(this);
      age++;
      if (age>=death_age || sugar < 0)
         remove = true;
   }
   public int getX(){
      return xpos;} 
   public void setX(int x){
      xpos=x;}
   public int getY(){
      return ypos;}
   public void setY(int y){
      ypos=y;}
   public int getMetabolism(){
      return metabolism;}
   public double getSugar(){
      return sugar;}
   public void addSugar(double x){
      sugar+=x;}
   public int getAge(){
      return age;}
   public Sugar getPlot(){
      return SugarPanel.sugar_grid[getX()][getY()];}
   public int getDeathAge(){
      return death_age;}
   public double getReproductionRate(){
      return reproduction_rate;}
   public int getID(){
      return id;}
   public boolean removable(){
      return remove;}
   public void moveTo(Sugar s)
   {
      SugarPanel.agent_grid[getX()][getY()] = null;
      setX(s.getX());
      setY(s.getY());
      SugarPanel.agent_grid[getX()][getY()] = this;
      addSugar(s.getSugar());
      s.removeSugar();
   }
   
   public Agent getNeighbor()
   {
      ArrayList<Agent> neighbors = new ArrayList<Agent>();
      for(int x = -1; x <= 1; x++)
         for(int y = -1; y <= 1; y++)       
         {
            if ((x==0 || y==0) && (x !=0 && y != 0))
            {
               Agent temp = SugarPanel.agent_grid[getX()][getY()];
               if(temp != null)
                  neighbors.add(temp);
            }
         }
      if (neighbors.size() > 1)
      {
         System.out.println(""+neighbors);
      
         Agent element = neighbors.get((int)(Math.random()*neighbors.size()));
         return element;
      }
      return null;
   }
   
   public Sugar search()
   {
      ArrayList<Sugar> s= new ArrayList<Sugar>();
      ArrayList<Sugar> empty = new ArrayList<Sugar>();
      double max = -1;
      int value = (int)(getSugar()*2);
      if (value > getMetabolism())
         value = getMetabolism();
      for(int x = value*-1; x < value+1; x++)
         for(int y = value*-1; y < value+1; y++)       
         {
         
            if (Math.abs(x) == value && Math.abs(y) == value)
            {
               continue;
            }
            int xp = getX() + x;
            int yp = getY() + y;
            
            if (xp >=0 && xp < SugarPanel.grid_width && yp >=0 && yp < SugarPanel.grid_height)
               if(SugarPanel.agent_grid[xp][yp] == null)
               {
                  empty.add(SugarPanel.sugar_grid[xp][yp]);
                  if (SugarPanel.sugar_grid[xp][yp].getSugar() > max)
                  {
                     s = new ArrayList<Sugar>();
                     s.add(SugarPanel.sugar_grid[xp][yp]);
                     max = SugarPanel.sugar_grid[xp][yp].getSugar();
                  }
                  if (SugarPanel.sugar_grid[xp][yp].getSugar() == max)
                  {
                     s.add(SugarPanel.sugar_grid[xp][yp]);
                  }
               }
         }
      if(s.size() ==0)
         if(empty.size() != 0)
            return empty.get((int)(Math.random()*s.size()));
         else
            return null;
      Sugar element = s.get((int)(Math.random()*s.size()));
      return element;
   }
   public String toString()
   {
      return String.format("Agent %d at (%d,%d) sugar: %f, age: %d, metabolism:%d", getID(), getX(), getY(), getSugar(), getAge(), getMetabolism());
   }
}