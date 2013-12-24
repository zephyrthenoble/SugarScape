
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;

public class InfoPanel extends JPanel implements Updateable
{

   public static final int MIN = 1;
   public static final int MAX = 200;
   public static final int INIT = 20;
   private JSlider slider;

   private JPanel innerPanel;

   private JPanel sugar;
   private JLabel sid = new JLabel("ID:\t", 0);
   private JLabel sposition = new JLabel("(x,y):\t", 0);
   private JLabel ssugar = new JLabel("Sugar:\t", 0);
   private JLabel smax = new JLabel("Max sugar:\t", 0);
   private JButton sagent = new JButton("Agent:\t");
   
   private JLabel sidv = new JLabel("",0);
   private JLabel spositionv = new JLabel("",0);
   private JLabel ssugarv = new JLabel("",0);
   private JLabel smaxv = new JLabel("",0);
   private JLabel sagentv = new JLabel("",0);
   
   
   
   
   private JPanel agent; 
   private JLabel aid = new JLabel("ID:\t", 0);
   private JButton aposition = new JButton("(x,y):\t");
   private JLabel asugar = new JLabel("Sugar:\t", 0);
   private JLabel amax = new JLabel("Max sugar:\t", 0);
   private JLabel amet = new JLabel("Metabolism:\t", 0);
   
   private JLabel aidv = new JLabel("",0);
   private JLabel apositionv = new JLabel("",0);
   private JLabel asugarv = new JLabel("",0);
   private JLabel amaxv = new JLabel("",0);
   private JLabel ametv = new JLabel("",0);
   
   private int x = -1;
   private int y = -1;
   
   Sugar s;
   Agent a;
   public InfoPanel()
   {
      super(new GridLayout(1,2));
      innerPanel = new JPanel(new GridLayout(3,1));
      add(innerPanel);
      sugar = new JPanel();
      agent = new JPanel();
      
      sugar.setLayout(new GridLayout(5,2));
      agent.setLayout(new GridLayout(5,2));
      innerPanel.add(sugar);
      
      
      sugar.add(sid);
      sugar.add(sidv);
      sugar.add(sposition);
      sugar.add(spositionv);
      sugar.add(ssugar);
      sugar.add(ssugarv);
      sugar.add(smax);
      sugar.add(smaxv);
      sugar.add(sagent);
      sugar.add(sagentv);
      
      innerPanel.add(new JSeparator());
      
      innerPanel.add(agent);
      agent.add(aid);
      agent.add(aidv);
      agent.add(aposition);
      agent.add(apositionv);
      agent.add(asugar);
      agent.add(asugarv);
      agent.add(amax);
      agent.add(amaxv);
      agent.add(amet);
      agent.add(ametv);
      
      
      
      slider = new JSlider(JSlider.VERTICAL, MIN, MAX, INIT);
      
      add(slider);
   }
   
   public void select(int x, int y)
   {
      this.x = x;
      this.y = y;
      s = SugarPanel.sugar_grid[x][y];
      a = SugarPanel.agent_grid[x][y];
   }
   
   public void update()
   {
   
      
      if(s==null)
      {
         /*
         sidv.setText();
         spositionv.setText();
         ssugarv.setText();
         smaxv.setText();
         sagentv.setText();
         */
      }
      else
      {
         sidv.setText(""+s.getID());
         spositionv.setText(""+String.format("(%d, %d)",s.getX(),s.getY()));
         ssugarv.setText(""+s.getSugar());
         smaxv.setText(""+s.getMaxSugar());
         sagentv.setText(""+s.getAgentID());
      }
      
      if(a==null)
      {
         ;
      }
      else
      {
         ;
      }
   }
   
   public void deselect()
   {
      select(-1,-1);
   }
}