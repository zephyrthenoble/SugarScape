import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
public class Screen extends JPanel
{
   public static SugarPanel p;
   public static InfoPanel i;
   public Screen()
   {
      super(new BorderLayout());
      i=new InfoPanel();
      p=new SugarPanel(i);
      p.setPreferredSize(new Dimension(600,600));
   
      add(p, BorderLayout.CENTER);
      add(i, BorderLayout.EAST);
   }
}