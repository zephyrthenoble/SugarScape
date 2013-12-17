import javax.swing.*;

import java.awt.event.*;

public class SugarMenu extends JMenuBar
{
   public SugarMenu()
   {
      super();
      
   
      JMenu menu = new JMenu("File");
      //menu.setBorder(BorderFactory.createEmptyBorder(4,30,4,30));
      add(menu);
      add(Box.createHorizontalGlue());
      JButton pause = new JButton("Pause");
      add(pause);
      
      JMenu _new = new JMenu("New");
      menu.add(_new);
      JMenuItem g = new JMenuItem("Grid");
      JMenuItem r = new JMenuItem("Random");
      _new.add(g);
      _new.add(r);
      
      g.addActionListener(new Grid());
      r.addActionListener(new Random());
      
      JMenuItem edit = new JMenuItem("Edit");
      menu.add(edit);
      edit.addActionListener(new Edit());
      
      JMenuItem load = new JMenuItem("Load");
      menu.add(load);
      load.addActionListener(new Load());
      
      JMenuItem save = new JMenuItem("Save");
      menu.add(save);
      save.addActionListener(new Save());
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