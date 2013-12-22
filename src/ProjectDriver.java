import javax.swing.JFrame;
import java.awt.Dimension;
 /**The ProjectDriver starts the whole game.  It instantiates the GamePanel, which
 actually runs the game.
 @author Mitchell Smith**/
public class ProjectDriver
{
   public static JFrame frame = new JFrame("SugarScape ");
   public static Screen screen;
   public static SugarMenu m;
   /**Sets up the game.**/
   public static void main(String[] args)
   {
         //frame.setSize(600, 600);
         //frame.setLocation(150, 100);
      //frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      screen = new Screen();
      m=new SugarMenu();
      //screen.setPreferredSize(new Dimension(600,600));
      frame.setContentPane(screen);
      frame.setJMenuBar(m);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
      screen.requestFocus();
   }
}
