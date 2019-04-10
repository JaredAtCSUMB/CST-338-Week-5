import java.awt.FlowLayout;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
/**
 * This application...
 * 
 * @author Team 6: Jared Cheney, Andrew Meraz, Chul Kim and Agustin Garcia
 *
 */
public class AssignmentFivePhaseOne
{
   public static final String IMAGE_FOLDER_NAME = "images";

   // static for the 57 icons and their corresponding labels
   // normally we would not have a separate label for each card, but
   // if we want to display all at once using labels, we need to.
   
   static final int NUM_CARD_IMAGES = 57; // 52 + 4 jokers + 1 back-of-card image
   static Icon[] icon = new ImageIcon[NUM_CARD_IMAGES];
      
   static void loadCardIcons()
   {
      // build the file names ("AC.gif", "2C.gif", "3C.gif", "TC.gif", etc.)
      // in a SHORT loop.  For each file name, read it in and use it to
      // instantiate each of the 57 Icons in the icon[] array.
      //exclude BK.gif (back image)
       File folder = new File(IMAGE_FOLDER_NAME);
       File[] listOfFiles = folder.listFiles();
       if (listOfFiles != null) {
          for (int i = 0; i < listOfFiles.length; i++) {
             String fileName = listOfFiles[i].getName();
             if (listOfFiles[i].isFile() && !fileName.equals("BK.gif")) {
                icon[i] = new ImageIcon(IMAGE_FOLDER_NAME + "/" +  fileName);
             }
         }
       }
   }
   
   // turns 0 - 13 into "A", "2", "3", ... "Q", "K", "X"
   static String turnIntIntoCardValue(int k)
   {
      // an idea for a helper method (do it differently if you wish)
      String ret = "";
      switch (k) {
      case 0:
         ret = "A";
         break;
      case 1:
         ret = "2";
         break;
      case 2:
         ret = "3";
         break;
      case 3:
         ret = "4";
         break;
      case 4:
         ret = "5";
         break;
      case 5:
         ret = "6";
         break;
      case 6:
         ret = "7";
         break;
      case 7:
         ret = "8";
         break;
      case 8:
         ret = "9";
         break;
      case 9:
         ret = "10";
         break;
      case 10:
         ret = "J";
         break;
      case 11:
         ret = "Q";
         break;
      case 12:
         ret = "K";
         break;
      case 13:
         ret = "K";
         break;
      default:
         throw new IllegalArgumentException();
      }
      return ret;
   }
   
   // turns 0 - 3 into "C", "D", "H", "S"
   static String turnIntIntoCardSuit(int j)
   {
      // an idea for another helper method (do it differently if you wish)
      String ret = "";
      switch (j) {
      case 0:
         ret = "C";
         break;
      case 1:
         ret = "D";
         break;
      case 2:
         ret = "H";
         break;
      case 3:
         ret = "S";
         break;
      default:
         throw new IllegalArgumentException();
      }
      return ret;
   }
   
   // a simple main to throw all the JLabels out there for the world to see
   // this was the main that was provided in the assignment
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
