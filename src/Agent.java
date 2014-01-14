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
   private double reproduction_rate=Math.random()/50; 
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
            System.out.println(String.format("intersect at (%d,%d) with agent %d", xpos, ypos, SugarPanel.getAgentGrid()[xpos][ypos].id));
         this.xpos = (int)(Math.random()*SugarPanel.getAgentGrid().length);
         this.ypos = (int)(Math.random()*SugarPanel.getAgentGrid()[0].length);
         iter++;
      }
      while(SugarPanel.getAgentGrid()[this.xpos][this.ypos] !=null);
      
     
      add();
   }
   public void add()
   {
      id = Agent.ID;
      Agent.ID++;
      sugar = (int)(Math.random()*10) + 5;
      SugarPanel.getAgentGrid()[xpos][ypos] = this;
      SugarPanel.getAgents().add(this);
   }
   public void update()
   {
   //reproduce if lucky
      if(Math.random() < getReproductionRate())
      {
         reproduce();
      }
      
      Sugar s = search();
      if(SugarPanel.verbose) System.out.println("Going to "+s);
      if (s!=null)
      {
         moveTo(s);
      }
      else 
         moveTo(SugarPanel.getSugarGrid()[getX()][getY()]);
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
      return SugarPanel.getSugarGrid()[getX()][getY()];}
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
      SugarPanel.getAgentGrid()[getX()][getY()] = null;
      int x = s.getX();
      int y = s.getY();
      double dist = Math.sqrt((double)(x-getX())*(x-getX()) + (double)(y-getY())*(y-getY())  );
      double sugarx = dist / 2 + 1;
      setX(x);
      setY(y);
      SugarPanel.getAgentGrid()[getX()][getY()] = this;
      
      addSugar(s.getSugar()-sugarx);
      
      s.removeSugar();
   }
   public void reproduce()
   {
      System.out.println("Reproducing "+this);
     
      Agent a = getNeighbor();
      
      System.out.println(""+a);
      
      Sugar s = getOpenSpace();
      
      if(s!=null && a!=null)
      {
         new Agent(s.getX(), s.getY());
      }
   }
   
   public Sugar getOpenSpace()
   {
      ArrayList<Sugar> plots = new ArrayList<Sugar>();
      Sugar[][] grid = SugarPanel.getSugarGrid();
      int xp = getX();
      int yp = getY();
      for(int x = -2; x <= 2; x++)
         for(int y = -2; y <= 2; y++)       
         {
            if (!(x==0 && y==0) && x+xp>=0 && y+yp>=0 && x+xp<grid.length &&y+yp<grid[0].length)
            {
               if(grid[x+xp][y+yp].getAgent() == null)
               {
                  plots.add(grid[x+xp][y+yp]);
               }
            }
         }
      if (plots.size() >= 1)
      {
         System.out.println(""+plots);
      
         Sugar element = plots.get((int)(Math.random()*plots.size()));
         return element;
      }
      return null;
   }
   public Agent getNeighbor()
   {
      ArrayList<Agent> neighbors = new ArrayList<Agent>();
      Agent[][] grid = SugarPanel.getAgentGrid();
      int xp = getX();
      int yp = getY();
      for(int x = -2; x <= 2; x++)
         for(int y = -2; y <= 2; y++)       
         {
            int newx = xp+x;
            int newy = xp+y;
            if (!(x==0 && y==0) && x+xp>=0 && y+yp>=0 && x+xp<grid.length &&y+yp<grid[0].length)
            {
               Agent temp = grid[x+xp][y+yp];
               if(temp != null)
                  neighbors.add(temp);
            }
         }
      if (neighbors.size() >= 1)
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
            
            if (xp >=0 && xp < SugarPanel.getGridWidth() && yp >=0 && yp < SugarPanel.getGridHeight())
               if(SugarPanel.getAgentGrid()[xp][yp] == null)
               {
                  empty.add(SugarPanel.getSugarGrid()[xp][yp]);
                  if (SugarPanel.getSugarGrid()[xp][yp].getSugar() > max)
                  {
                     s = new ArrayList<Sugar>();
                     s.add(SugarPanel.getSugarGrid()[xp][yp]);
                     max = SugarPanel.getSugarGrid()[xp][yp].getSugar();
                  }
                  if (SugarPanel.getSugarGrid()[xp][yp].getSugar() == max)
                  {
                     s.add(SugarPanel.getSugarGrid()[xp][yp]);
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