import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class InfoPanel extends JPanel implements Updateable
{
   JFrame sugar = new JFrame();
   JFrame agent = new JFrame();
   
   
   public InfoPanel()
   {
      super(new GridLayout(1,2));
      
      add(sugar);
      add(agent);
   
   }
   
   public void select(int x, int y)
   {
   
   }
   
   public void update()
   {
      ;
   }
   
   public void deselect()
   {
   
   }
}