
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.awt.*;
import javax.swing.JOptionPane;
import java.awt.image.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;

public class SugarPanel extends JPanel
{      
   public static boolean verbose = false;
   public boolean running = true;
   public static boolean[] keys=new boolean[5];
   public static int timestep = 500;

	

   public Graphics2D myBuffer;
   public BufferedImage myImage;

   public static final Color BACKGROUND=Color.white;
   public static int W=600;
   public static int H=600;
   public int grid_width = 10;
   public int grid_height = 10;
   public ArrayList<Agent> agents=new ArrayList<Agent>();
   public ArrayList<Sugar> sugar=new ArrayList<Sugar>();
   public Agent[][] agent_grid = new Agent[grid_width][grid_height];
   public Sugar[][] sugar_grid = new Sugar[grid_width][grid_height];
   public InfoPanel i;
   public Timer t;
   private Updater up;
   
   private static SugarPanel sugarpanel;
   
   private class Updater implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         update();
      }
   }
   public static void init()
   {
      sugarpanel = new SugarPanel();
      get().randomInit();
   }
   public static SugarPanel get()
   {
      return sugarpanel;
   }      
   public static int getGridWidth()
   {
      if(sugarpanel != null)
         return sugarpanel.grid_width;
      return 0;
   }
   public static int getGridHeight()
   {
      if(sugarpanel != null)
         return sugarpanel.grid_height;
      return 0;
   }
   public static Sugar[][] getSugarGrid()
   {
      if(sugarpanel != null)
         return sugarpanel.sugar_grid;
      return null;
   }
   public static Agent[][] getAgentGrid()
   {
      if(sugarpanel != null)
         return sugarpanel.agent_grid;
      return null;
   }
   public static ArrayList<Agent> getAgents()
   {
      if(sugarpanel != null)
         return sugarpanel.agents;
      return null;
   }
   public static ArrayList<Sugar> getSugar()
   {
      if(sugarpanel != null)
         return sugarpanel.sugar;
      return null;
   }
   public SugarPanel()
   {
   
      up = new Updater();
      t=new Timer(timestep, up);
      myImage =  new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.createGraphics();
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0, 0, W, H);
      setFocusable(true);
      //InfoPanel.get().select(1,1);
      addKeyListener(new Key());
      addMouseListener(new Mouse());
      
      
      t.start();
   }
   public void newTimer(int time)
   {
      t.setDelay(time);
   
   }
   public void update()
   {
      myImage =  new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.createGraphics();
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0, 0, W, H);
      
      if(running)
         update_state();
         
      draw_state();
      
      InfoPanel.get().update();
      
      repaint();
   }
   public void update_state()
   {
      ArrayList<Agent> agentcopy = new ArrayList<Agent>(agents);
      Iterator it = agentcopy.iterator();
      while(it.hasNext())
      {
         Agent a=(Agent)it.next();
         a.update();
         if(a.removable())
         {
            agent_grid[a.getX()][a.getY()] = null;
            agents.remove(a);
            
         }
      }
      
      ArrayList<Sugar> sugarcopy = new ArrayList<Sugar>(sugar);
      it = sugarcopy.iterator();
      while(it.hasNext())
      {
         Sugar a=(Sugar)it.next();
         a.update();
         if(a.removable())
            sugar.remove(a);
      } 
      
   }
   public boolean pause()
   {
      if (running)
      {
         running = false;
         return true;
      }
      running = true;
      return false;
   }
   public void draw_state()
   {
   
      
      for (Sugar a: sugar)
      {
         switch((int)a.getSugar())
         {
            case 4: myBuffer.setColor(Color.green); 
               break;
            case 3: myBuffer.setColor(Color.yellow); 
               break;
            case 2: myBuffer.setColor(Color.orange); 
               break;
            case 1: myBuffer.setColor(Color.blue); 
               break;
            default: myBuffer.setColor(Color.white);
         }
         myBuffer.fillRect(getCellWidth()*a.getX(), getCellHeight()*a.getY(), getCellWidth(), getCellHeight());
      }
      
      
      for (Agent a: agents)
      {
         int age = a.getAge();
         myBuffer.setColor(new Color((255-age)%255,255,(age)%255));
         myBuffer.fillOval(getCellWidth()*a.getX()+4, getCellHeight()*a.getY()+4, getCellWidth()-8, getCellHeight()-8);
      }
   }
   
   public void paintComponent(Graphics g)
   {
      
      g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
      
   }
   public void clear()
   {
      agents = new ArrayList<Agent>();
      sugar = new ArrayList<Sugar>();
      agent_grid = null;
      sugar_grid = null;
   }
   
   public void newGrid(int rows, int cols)
   {
      sugar_grid = new Sugar[rows][cols];
      agent_grid = new Agent[rows][cols];
      grid_width = rows;
      grid_height = cols;
   }
   public void randomInit()
   {
      clear();
      newGrid(grid_width, grid_height);
      randomAgents();
      randomSugar();
   }
   public void randomAgents()
   {
      for(int x = 0; x < 7; x++)
      {
         Agent a = new Agent();
         if(verbose)System.out.println(a);
      }
   }
   public void randomSugar()
   {
      for(int x = 0; x < (grid_width); x++)
         for(int y = 0; y < (grid_height); y++)
         {
            Sugar a = new Sugar(x,y);
            if(verbose)System.out.println(a);
         }
   }
   public void blankInit()
   {
      for(int x = 0; x < (grid_width); x++)
         for(int y = 0; y < (grid_height); y++)
         {
            Sugar a = Sugar.empty_plot(x,y);
            if(verbose)System.out.println(a);
         }
   }

   public int getCellWidth()
   {
      return W/grid_width;
   }
   public int getCellHeight()
   {
      return H/grid_height;
   }
   
   public int getXCoord(int x)
   {
      return x/getCellWidth();
   }
   
   public int getYCoord(int y)
   {
      return y/getCellHeight();
   }
   
   private class Mouse implements MouseListener
   {
      public void mousePressed(MouseEvent e) {
         ;
      }
   
      public void mouseReleased(MouseEvent e) {
         ;
      }
   
      public void mouseEntered(MouseEvent e) {
         ;
      }
   
      public void mouseExited(MouseEvent e) {
         ;
      }
   
      public void mouseClicked(MouseEvent e) {
         if (e.getButton() == MouseEvent.BUTTON1)
         {
            int x = getXCoord(e.getX());
            int y = getYCoord(e.getY());
            InfoPanel.get().select(x, y);
            System.out.println("(" + x + ", " + y + ")");
         }
      }
   }
   
   /*      | 0
	**      |
	**      |
	**      |
270_________________90
	
	*/
//    /\
//    0
//< 3    1  >
//    2
//    \/
//space=4
   public final static int UP=0,RIGHT=1,DOWN=2,LEFT=3,SPACE=4;
   private class Key extends KeyAdapter
   {
   
      public void keyReleased(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {keys[0]=false;}
         
         if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         {keys[1]=false;}
                   
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {keys[2]=false;}
         
         if(e.getKeyCode() == KeyEvent.VK_LEFT)
         {keys[3]=false;}
      	
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
         {keys[4]=false;} 
      }
      public void keyPressed(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_UP)
         {keys[0]=true;}
         
         if(e.getKeyCode() == KeyEvent.VK_RIGHT)
         {keys[1]=true;}
                   
         if(e.getKeyCode() == KeyEvent.VK_DOWN)
         {keys[2]=true;}
         
         if(e.getKeyCode() == KeyEvent.VK_LEFT)
         {keys[3]=true;}
      	
         if(e.getKeyCode() == KeyEvent.VK_SPACE)
         {keys[4]=true;}          }
   }
}