
import java.io.*;
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


public class SugarPanel extends JPanel
{      
   public static boolean verbose = false;
   public static boolean running = true;
   public static boolean[] keys=new boolean[5];


	

   public static Graphics2D myBuffer;
   public static BufferedImage myImage;

   public static final Color BACKGROUND=Color.white;
   public static int W=600;
   public static int H=600;
   public static int grid_width = 10;
   public static int grid_height = 10;
   public static ArrayList<Agent> agents=new ArrayList<Agent>();
   public static ArrayList<Sugar> sugar=new ArrayList<Sugar>();
   public static Agent[][] agent_grid = new Agent[grid_width][grid_height];
   public static Sugar[][] sugar_grid = new Sugar[grid_width][grid_height];
   Timer t=new Timer(200,new Updater());
   
   private class Updater implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         update();
      }
   }
   public SugarPanel()
   {
      myImage =  new BufferedImage(W, H, BufferedImage.TYPE_INT_RGB);
      myBuffer = myImage.createGraphics();
      myBuffer.setColor(BACKGROUND);
      myBuffer.fillRect(0, 0, W, H);
      setFocusable(true);
      init();
      t.start();
      addKeyListener(new Key());
   
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
      
      
      repaint();
   }
   public void update_state()
   {
      Iterator it = agents.iterator();
      while(it.hasNext())
      {
         Agent a=(Agent)it.next();
         a.update();
         if(a.removable())
            it.remove();
      }
      it = sugar.iterator();
      while(it.hasNext())
      {
         Sugar a=(Sugar)it.next();
         a.update();
         if(a.removable())
            it.remove();
      }
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
      
      myBuffer.setColor(Color.black);
      for (Agent a: agents)
      {
      
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
   
   public void init()
   {
      randomInit();
   }
   public void randomInit()
   {
      for(int x = 0; x < 10; x++)
      {
         Agent a = new Agent();
         if(verbose)System.out.println(a);
      }
      
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