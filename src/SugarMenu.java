import javax.swing.*;
import java.util.Scanner;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

public class SugarMenu extends JMenuBar
{
   JMenu menu;
   JButton pause;
   JMenu _new;
   JMenuItem g, r, edit,load,save;
   
   //JFileChooser fc = new JFileChooser(".\\Resources\\Maps");
   JFileChooser fc;    
   public SugarMenu()
   {
      super();
      UIManager.put("FileChooser.readOnly", Boolean.TRUE); 
      fc = new JFileChooser((new File(System.getProperty("user.dir"))));
   
      
      System.out.println();
      menu = new JMenu("File");
      //menu.setBorder(BorderFactory.createEmptyBorder(4,30,4,30));
      add(menu);
      add(Box.createHorizontalGlue());
      
      pause = new JButton("Pause");
      pause.setActionCommand("pause");
      pause.addActionListener(new Pause());
      add(pause);
      
      _new = new JMenu("New");
      menu.add(_new);
      g = new JMenuItem("Grid");
      r = new JMenuItem("Random");
      _new.add(g);
      _new.add(r);
      
      g.addActionListener(new Grid());
      r.addActionListener(new Random());
      
      edit = new JMenuItem("Edit");
      menu.add(edit);
      edit.addActionListener(new Edit());
      
      load = new JMenuItem("Load");
      menu.add(load);
      load.addActionListener(new Load());
      
      save = new JMenuItem("Save");
      menu.add(save);
      save.addActionListener(new Save());
   }
   
   public boolean toggle()
   {
      if("pause".equals(pause.getActionCommand()))
      {
         pause.setActionCommand("resume");
         pause.setText("Resume");
         return true;
      }
   
      pause.setActionCommand("pause");
      pause.setText("Pause");
      return false;
         
   }
   
   public static boolean loadFile(File f)
   {
      Scanner infile;
      try
      {
         infile = new Scanner(f);
      }
      catch(FileNotFoundException e)
      {
         return false;
      }
      try
      {
         int rows = (infile.nextInt());
         int cols = (infile.nextInt());
         int count = 0;
         int xpos = 0;
         int ypos = 0;
         SugarPanel.clear();
         SugarPanel.sugar_grid = new Sugar[rows][cols];
         SugarPanel.agent_grid = new Agent[rows][cols];
         while(infile.hasNext())
         {
            int value = infile.nextInt();
            xpos = count % cols;
            ypos = count / rows;
            Sugar a = new Sugar(xpos, ypos, value, value);
            count++;
         }
         SugarPanel.grid_width = rows;
         SugarPanel.grid_height = cols;
         
         for(int x = 0; x < (Math.sqrt(rows*cols))/4+1; x++)
         {
            Agent a = new Agent();
         }
      }
      catch(Exception e)
      {
         return false;
      }
      return true;
   }
   private class Pause implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         SugarPanel.pause();
         toggle();
      }
   }
  
   private class Grid implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.out.println(e);
      }
   }
   private class Random implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.out.println(e);
      }
   }
   private class Edit implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.out.println(e);
      }
   }
   private class Load implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.out.println(e);
         
         SugarPanel.pause();
         toggle();
         
         
      
         int returnVal = fc.showOpenDialog(null);
         
         File f = fc.getSelectedFile();
         System.out.println(f);
         loadFile(f);
         
         
         
         SugarPanel.pause();
         toggle();
         //Scanner infile = new Scanner(new File("\Resources\Maps"))
      }
   }

   private class Save implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         System.out.println(e);
      }
   }
}