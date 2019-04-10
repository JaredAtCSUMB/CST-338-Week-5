import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
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
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      GridLayout layout = new GridLayout(4, 1);
      myCardTable.setLayout(layout);

      //Create a Deck
      Deck deck = new Deck();
      deck.shuffle();

      Hand computerHand = dealHand(myCardTable, deck, computerLabels, true);
      Hand yourHand = dealHand(myCardTable, deck, humanLabels, false);

      displayHandArea(myCardTable, "Computer Hand", computerLabels);

      // and two random cards in the play region (simulating a computer/hum ply)
      displayPlayingArea(myCardTable, computerHand, yourHand);

      // My Hand
      displayHandArea(myCardTable, "Your Hand", humanLabels);
      
      // show everything to the user
      myCardTable.setVisible(true);

   }
   
   /**
    * Deals hand from a Deck.
    * Stores the your hand icons in JLabels array
    * 
    * @param myCardTable
    * @param deck
    * @param JLabels
    * @return Hand
    */
   private static Hand dealHand(CardTable myCardTable, Deck deck, JLabel[] JLabels, boolean isBackCard) {
      // Create a Hand (dealer or player)
      Hand hand = new Hand();
      for (int i = 0; i < NUM_CARDS_PER_HAND; i ++) {
         Card dealCard = deck.dealCard();
         hand.takeCard(dealCard);
         //Create an icon and store in an array later use.
         Icon icon = null;
         if (isBackCard) {
            icon = GUICard.getBackCardIcon();
         } else {
            icon = GUICard.getIcon(dealCard);
         }
         JLabel jlabel = new JLabel(icon);
         JLabels[i] = jlabel;
      }
      return hand;
   }
   
   /**
    * Displays your hand.
    * @param myCardTable
    * @param yourHand
    */
   private static void displayHandArea(CardTable myCardTable, String areaTitle, JLabel[] JLables) {
      // My Hand
      JPanel pnlHand = new JPanel();
      Border border = new TitledBorder(areaTitle);
      pnlHand.setBorder(border);
      //Don't display every card in hand to this area as one card will be displayed in the playing area.
      for (int i = 0; i < NUM_CARDS_PER_HAND - 1; i ++) {
         pnlHand.add(JLables[i]);
      }
      myCardTable.add(pnlHand);
   }
   
   /**
    * Create a Playing Area Hand
    * 
    * @param myCardTable
    * @param computerHand
    * @param yourHand
    */
   private static void displayPlayingArea(CardTable myCardTable, Hand computerHand, Hand yourHand ) {
      JPanel pnlPlayArea = new JPanel();
      JPanel pnlPlayAreaPosition = new JPanel();
      Border border = new TitledBorder("Playing Area");
      pnlPlayArea.setBorder(border);
      

      GridLayout layout = new GridLayout(1, 2);
      pnlPlayArea.setLayout(layout);
      pnlPlayAreaPosition.setLayout(layout);
      
      // Create a Playing Area Hand
      //Play Computer Card
      Card computerCard = computerHand.playCard();
      Icon computerCardIcon = GUICard.getIcon(computerCard);
      JLabel computerCardJLabel = new JLabel(computerCardIcon);
      playedCardLabels[0] = computerCardJLabel;
      JLabel computerLabel = new JLabel( "Computer", JLabel.CENTER );
      playLabelText[0] = computerLabel;


      //Play Your Card
      Card yourCard = yourHand.playCard();
      Icon yourCardIcon = GUICard.getIcon(yourCard);
      JLabel yourCardJLabel = new JLabel(yourCardIcon);
      playedCardLabels[1] = yourCardJLabel;
      JLabel yourHandLabel = new JLabel( "You", JLabel.CENTER );
      playLabelText[1] = yourHandLabel;

      //Add labels to the play area
      pnlPlayArea.add(computerCardJLabel);
      pnlPlayArea.add(yourCardJLabel);
      pnlPlayAreaPosition.add(computerLabel);
      pnlPlayAreaPosition.add(yourHandLabel);

      myCardTable.add(pnlPlayArea);
      myCardTable.add(pnlPlayAreaPosition);
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
   
   /**
    *  It will read the image files and store them in a static Icon array. 
    *  Rather than a 1-D array of Phase 1, this will be a 2-D array to facilitate addressing the value and suit of a Card in order get its Icon.
    *  
    *  We have to be able to convert from chars and suits to ints, and back again, in order to find the Icon for any given Card object. 
    * @author charlesk
    *
    */
   private static class GUICard
   {
      public static final String IMAGE_FOLDER_NAME = "images";
      public static final String BACK_IMAGE_NAME = "BK.gif";

      public static final int MAX_ROW = 14;
      public static final int MAX_COLUMN = 4;
      
      // 14 = A thru K + joker
      //The 52 + 4 jokers Icons will be read and stored into the iconCards[][] array.
      private static Icon[][] iconCards = new ImageIcon[MAX_ROW][MAX_COLUMN];
      
      //The card-back image in the iconBack member
      private static Icon iconBack;
      
      static boolean iconsLoaded = false;
      
      //
      static {
         loadCardIcons();
      }
      
      /**
       *  Icons are loaded following rules
       *  C = Clover, D = Diamond, H = Hearts S = Spades
       *  
       *  [A,C],[A,D],[A,H],[A,S]
       *  [2,C],[2,D],[2,H],[2,S]
       *  [3,C],[3,D],[3,H],[3,S]
       *  ...
       *  [J,C],[J,D],[J,H],[J,S]
       *  ...
       *  [K,C],[K,D],[K,H],[K,S]
       */
      public static void loadCardIcons()
      {
         if (iconsLoaded ) {
            return;
         }
         //Load iconBack image
         iconBack= new ImageIcon(IMAGE_FOLDER_NAME + "/" +  BACK_IMAGE_NAME);
         
         //Load other icons(cards)
         for (int r = 0; r < MAX_ROW; r ++) {
            for (int c = 0; c < MAX_COLUMN; c ++) {
               iconCards[r][c] =  new ImageIcon(IMAGE_FOLDER_NAME + "/" +  getImageFileName(r, c));
            }
         }
 
         iconsLoaded = true;
      }
      
      private static String getImageFileName(int row, int column) {
         StringBuilder ret = new StringBuilder();
         switch(row) {
         case 0:
            ret.append('A');
            break;
         case 1:
            ret.append('2');
            break;
         case 2:
            ret.append('3');
            break;
         case 3:
            ret.append('4');
            break;
         case 4:
            ret.append('5');
            break;
         case 5:
            ret.append('6');
            break;
         case 6:
            ret.append('7');
            break;
         case 7:
            ret.append('8');
            break;
         case 8:
            ret.append('9');
            break;
         case 9:
            ret.append('T');
            break;
         case 10:
            ret.append('J');
            break;
         case 11:
            ret.append('Q');
            break;
         case 12:
            ret.append('K');
            break;
         case 13:
            ret.append('X');
            break;
         default:
            throw new IllegalArgumentException("Illegal value of row");
         }
         
         switch(column) {
         case 0:
            ret.append('S');
            break;
         case 1:
            ret.append('H');
            break;
         case 2:
            ret.append('D');
            break;
         case 3:
            ret.append('C');
            break;
         default:
            throw new IllegalArgumentException("Illegal value of column");
         }
         
         ret.append(".gif");
         return ret.toString();
      }
      /**
       * 
       * @param card
       * @return the Icon for that card
       */
      public static Icon getIcon(Card card)
      {
         return iconCards[valueAsInt(card)][suitAsInt(card)];
      }
      
      /**
       * 
       * @param card
       * @return
       */
      private static int valueAsInt(Card card) {
         int ret = 0;
         char valueChar = card.getValue();
         switch(valueChar) {
         case 'A':
            ret = 0;
            break;
         case '2':
            ret = 1;
            break;
         case '3':
            ret = 2;
            break;
         case '4':
            ret = 3;
            break;
         case '5':
            ret = 4;
            break;
         case '6':
            ret = 5;
            break;
         case '7':
            ret = 6;
            break;
         case '8':
            ret = 7;
            break;
         case '9':
            ret = 8;
            break;
         case 'T':
            ret = 9;
            break;
         case 'J':
            ret = 10;
            break;
         case 'Q':
            ret = 11;
            break;
         case 'K':
            ret = 12;
            break;
         case 'X':
            ret = 13;
            break;
         default:
            throw new IllegalArgumentException("Illegal value of card value.");
      }
         return ret;
      }
      
      /**
       * 
       * @param card
       * @return
       */
      private static int suitAsInt(Card card) {
         int ret = 0;
         Card.Suit suit = card.getSuit();
         switch(suit) {
         case spades:
            ret = 0;
            break;
         case hearts:
            ret = 1;
            break;
         case diamonds:
            ret = 2;
            break;
         case clubs:
            ret = 3;
            break;
         default:
            throw new IllegalArgumentException("Illegal value of card suit.");
         }
         return ret;
      }
      
      /**
       * 
       * @return back card icon
       */
      public static Icon getBackCardIcon()
      {
         return iconBack;
      }
   }
}

