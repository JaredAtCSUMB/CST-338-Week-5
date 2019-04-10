import javax.swing.*;
import java.awt.*;
/**
 * Reads and displays the .gif image files of the cards. 
 * 
 * @author Team 6: Jared Cheney, Andrew Meraz, Chul Kim and Agustin Garcia
 *
 */
public class AssignmentFivePhaseOne
{
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
      
   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.
      //exclude BK.gif (back image)
       int x = 0;
       for (int j = 0; j <4; j++)
       {
          for (int i = 0; i < 14; i++)
          {
             
             icon[x] = new ImageIcon("images/" + turnIntIntoCardValue(i) + 
                   turnIntIntoCardSuit(j) +".gif");
             x++;
          }  
       }
   }
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      // an idea for a helper method (do it differently if you wish)
      if (k == 0) {
         return "A";
      } else if (k == 9) {
         return "T";
      } else if (k == 10) {
         return "J";
      } else if (k == 11) {
         return "Q";
      } else if (k == 12) {
         return "K";
      } else if (k == 13) {
         return "X";
      } else {
         return Integer.toString(++k);
      }
   }
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      if (j == 0) {
         return "C";
      } else if (j == 1) {
         return "D";
      } else if (j == 2) {
         return "H";
      } else {
         return "S";
      }
         
   }
   
   // a simple main to throw all the JLabels out there for the world to see
   // TODO: this was the main that was provided in the assignment
   public static void main(String[] args)
   {
      int k;
      
      // prepare the image icon array
      loadCardIcons();
      
      // establish main frame in which program will run
      JFrame frmMyWindow = new JFrame("Card Room");
      frmMyWindow.setSize(1150, 650);
      frmMyWindow.setLocationRelativeTo(null);
      frmMyWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);   
      frmMyWindow.setLayout(layout);
      
      // prepare the image label array
      JLabel[] labels = new JLabel[NUM_CARD_IMAGES];
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         labels[k] = new JLabel(icon[k]);
      
      // place your 3 controls into frame
      for (k = 0; k < NUM_CARD_IMAGES; k++)
         frmMyWindow.add(labels[k]);

      // show everything to the user
      frmMyWindow.setVisible(true);
   }

}
