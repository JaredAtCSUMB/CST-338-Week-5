import java.awt.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;
/**
 * This application...
 * 
 * @author Team 6: Jared Cheney, Andrew Meraz, Chul Kim and Agustin Garcia
 *
 */
public class AssignmentFivePhaseTwo
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS]; 
   
   public static void main(String[] args)
   {
      int k;
      Icon tempIcon;
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // show everything to the user
      myCardTable.setVisible(true);

      // CREATE LABELS ----------------------------------------------------
      // code goes here ...
  
      // ADD LABELS TO PANELS -----------------------------------------
      // code goes here ...
      
      // and two random cards in the play region (simulating a computer/hum ply)
      // code goes here ...

      // show everything to the user
      myCardTable.setVisible(true);
   }
   
   private static class CardTable extends JFrame
   {
      static int MAX_CARDS_PER_HAND = 56;
      static int MAX_PLAYERS = 2;  // for now, we only allow 2 person games    
      private int numCardsPerHand;
      private int numPlayers;
      public JPanel pnlComputerHand, pnlHumanHand, pnlPlayArea;
      
      public CardTable(String title, int numCardsPerHand, int numPlayers)
      {
         if (numCardsPerHand > MAX_CARDS_PER_HAND) {
            throw new IllegalArgumentException("Max number of cards per hand:" + MAX_CARDS_PER_HAND);
         }
         if (numPlayers > MAX_PLAYERS) {
            throw new IllegalArgumentException("Max number of players are:" + MAX_PLAYERS);
         }
         this.numCardsPerHand = numCardsPerHand;
         this.numPlayers = numPlayers;
      }
      
      public int getNumCardsPerHand()
      {
         return numCardsPerHand;
      }
      
      public int getNumPlayers()
      {
         return numPlayers;
      }
   }
   
   private static class GUICard
   {
      //TODO: Should we move this folder?
      public static final String IMAGE_FOLDER_NAME = "PhaseOne/images";
      public static final String BACK_IMAGE_NAME = "BK.gif";

      public static final int MAX_ROW = 14;
      public static final int MAX_COLUMN = 4;
      
      // 14 = A thru K + joker
      //The 52 + 4 jokers Icons will be read and stored into the iconCards[][] array.
      private static Icon[][] iconCards = new ImageIcon[MAX_ROW][MAX_COLUMN];
      
      //The card-back image in the iconBack member
      private static Icon iconBack;
      
      static boolean iconsLoaded = false;
      
      static {
         loadCardIcons();
      }
      
      /**
       *  Don't require the client to call this method. 
       */
      public static void loadCardIcons()
      {
         if (iconsLoaded ) {
            return;
         }
         //Load iconBack image
         iconBack= new ImageIcon(IMAGE_FOLDER_NAME + "/" +  BACK_IMAGE_NAME);
         
         //Load other icons(cards)
         File folder = new File(IMAGE_FOLDER_NAME);
         File[] listOfFiles = folder.listFiles();
         int j = 0;
         int k = 0;
         if (listOfFiles != null) {
            for (int i = 0; i < listOfFiles.length; i++) {
               String fileName = listOfFiles[i].getName();
               if (listOfFiles[i].isFile() && !fileName.equals(BACK_IMAGE_NAME)) {
                  ImageIcon imageIcon = new ImageIcon(IMAGE_FOLDER_NAME + "/" +  fileName);
                  
                  if (k == MAX_COLUMN) {
                     j++;
                     k = 0;
                  }
                  iconCards[j][k] = imageIcon;
                  k++;
               }
           }
         }
         iconsLoaded = true;
      }
      
      // TODO import card class and implement method
      public static Icon getIcon(Card card)
      {
         return null;
         // example return
         // return iconCards[valueAsInt(card)][suitAsInt(card)];
      }
      
      public static Icon getBackCardIcon()
      {
         return iconBack;
      }
   }
}

