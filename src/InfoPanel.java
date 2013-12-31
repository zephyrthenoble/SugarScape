
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class InfoPanel extends JPanel implements Updateable
{

   public static final int MIN = 0;
   public static final int MAX = 50;
   public static final int INIT = (int)Math.sqrt(500);
   

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
   
   private JPanel sliderPanel;
   private JLabel sliderLabel;
   private JSlider slider;
   
   private JPanel agent; 
   private JLabel aid = new JLabel("ID:\t", 0);
   private JButton aposition = new JButton("(x,y):\t");
   private JLabel asugar = new JLabel("Sugar:\t", 0);
   private JLabel aage = new JLabel("Age:\t", 0);
   private JLabel amet = new JLabel("Metabolism:\t", 0);
   
   private JLabel aidv = new JLabel("",0);
   private JLabel apositionv = new JLabel("",0);
   private JLabel asugarv = new JLabel("",0);
   private JLabel aagev = new JLabel("",0);
   private JLabel ametv = new JLabel("",0);
   
   private int x = -1;
   private int y = -1;
   
   private Sugar s;
   private Agent a;

   
   private static InfoPanel infopanel;
   public static void init()
   {
      infopanel = new InfoPanel();
   }
   public static InfoPanel get()
   {
      return infopanel;
   }
   public InfoPanel()
   {
      super(new GridLayout(3,1));
   
   
      sugar = new JPanel();
      agent = new JPanel();
      
      sugar.setLayout(new GridLayout(6,2));
      agent.setLayout(new GridLayout(5,2));
      
      
      
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
   
      sliderPanel = new JPanel(new GridLayout(2,1));
      slider = new JSlider(JSlider.HORIZONTAL, MIN, MAX, INIT);
      sliderLabel = new JLabel(""+500);
      sliderLabel.setHorizontalAlignment(JLabel.CENTER);
      sliderLabel.setVerticalAlignment(JLabel.TOP);
      //slider.setMajorTickSpacing(10);
      slider.setMinorTickSpacing(5);
      slider.setPaintTicks(true);
      //slider.setPaintLabels(true);
      slider.addChangeListener(new Slider());
      sliderPanel.add(slider);
      sliderPanel.add(sliderLabel);
      
   
      
      
      agent.add(aid);
      agent.add(aidv);
      agent.add(aposition);
      agent.add(apositionv);
      agent.add(asugar);
      agent.add(asugarv);
      agent.add(aage);
      agent.add(aagev);
      agent.add(amet);
      agent.add(ametv);
      
      
      
      sugar.add(new JSeparator());
      sugar.add(new JSeparator());
   
      
      add(sugar);
      add(agent); 
      add(sliderPanel);
      
      sagent.addActionListener(new SelectAgent());
      aposition.addActionListener(new SelectSugar());
   
   }
   private class SelectAgent implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         a = s.getAgent();
         //System.out.println("SelectAgent");
      }
   }
   private class SelectSugar implements ActionListener
   {
      public void actionPerformed(ActionEvent e)
      {
         s = a.getPlot();
      }
   }
   private class Slider implements ChangeListener
   {
      public void stateChanged(ChangeEvent e)
      {
         JSlider source = (JSlider)e.getSource();
         if (!source.getValueIsAdjusting()) {
            int val = (int)source.getValue();
            val = (int)Math.pow(val,2);
            SugarPanel.get().newTimer(val);
            sliderLabel.setText(""+val);
         }
      }
   }
   public void select(int x, int y)
   {
      if (x < 0 || y < 0)
      {
         s=null;
         a=null;
      }
      else
      {
         this.x = x;
         this.y = y;
         s = SugarPanel.getSugarGrid()[x][y];
         a = SugarPanel.getAgentGrid()[x][y];
      }
      update();
   }
   
   public void update()
   {
   
      
      if(s==null)
      {
         
         sidv.setText("");
         spositionv.setText("");
         ssugarv.setText("");
         smaxv.setText("");
         sagentv.setText("");
         
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
         aidv.setText("");
         apositionv.setText("");
         asugarv.setText("");
         aagev.setText("");
         ametv.setText("");
      }
      else
      {
         aidv.setText(""+a.getID());
         apositionv.setText(""+String.format("(%d, %d)",a.getX(),a.getY()));
         asugarv.setText(""+a.getSugar());
         aagev.setText(""+a.getAge());
         ametv.setText(""+a.getMetabolism());
       
      }
   }
   
   public void deselect()
   {
      select(-1,-1);
   }
}