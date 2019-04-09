import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
/**
 * This application...
 * 
 * @author Team 6: Jared Cheney, Andrew Meraz, Chul Kim and Agustin Garcia
 *
 */
public class AssignmentFivePhaseThree
{
   static int NUM_CARDS_PER_HAND = 7;
   static int  NUM_PLAYERS = 2;
   static JLabel[] computerLabels = new JLabel[NUM_CARDS_PER_HAND];
   static JLabel[] humanLabels = new JLabel[NUM_CARDS_PER_HAND];  
   static JLabel[] playedCardLabels  = new JLabel[NUM_PLAYERS]; 
   static JLabel[] playLabelText  = new JLabel[NUM_PLAYERS];
   
   public static void main(String[] args)
   {
      int numPacksPerDeck = 1;
      int numJokersPerPack = 0;
      int numUnusedCardsPerPack = 0;
      Card[] unusedCardsPerPack = null;
      
      CardGameFramework highCardGame = new CardGameFramework( 
            numPacksPerDeck, numJokersPerPack,  
            numUnusedCardsPerPack, unusedCardsPerPack, 
            NUM_PLAYERS, NUM_CARDS_PER_HAND);
      
      //We have two hands from the highCardGame object.
      //calling deal to deal all of the cards per each hand.  (7 cards per hand)
      highCardGame.deal();
      
      // establish main frame in which program will run
      CardTable myCardTable 
         = new CardTable("CardTable", NUM_CARDS_PER_HAND, NUM_PLAYERS);
      myCardTable.setSize(800, 600);
      myCardTable.setLocationRelativeTo(null);
      myCardTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // set up layout which will control placement of buttons, etc.
      FlowLayout layout = new FlowLayout(FlowLayout.CENTER, 5, 20);
      myCardTable.setLayout(layout);

      Hand computerHand = highCardGame.getHand(0);
      Hand yourHand = highCardGame.getHand(1);
      
      //HighCard Game
      
      // Create a Playing Area Hand
      JPanel pnlPlayArea = new JPanel();
      Border border = new TitledBorder("Playing Area");
      pnlPlayArea.setBorder(border);
      GridLayout gridLayout = new GridLayout(2, 2);
      pnlPlayArea.setLayout(gridLayout);
      
      JPanel pnlComputerArea = new JPanel();
      JPanel pnlYourHandArea = new JPanel();

      createHandJLabels(myCardTable, computerHand, computerHand, computerLabels, false, pnlPlayArea, pnlComputerArea, pnlYourHandArea);
      createHandJLabels(myCardTable, yourHand, computerHand, humanLabels, false, pnlPlayArea, pnlComputerArea, pnlYourHandArea);

      displayHandArea(myCardTable, "Computer Hand", computerLabels, pnlComputerArea);

      // and two random cards in the play region (simulating a computer/hum ply)
      displayPlayingArea(pnlPlayArea, myCardTable, null, null);

      // My Hand
      displayHandArea(myCardTable, "Your Hand", humanLabels, pnlYourHandArea);
      
      // show everything to the user
      myCardTable.setVisible(true);

   }
   
   /**
    * 
    * @param myCardTable
    * @param hand
    * @param JLabels
    * @param isBackCard
    */
   private static void createHandJLabels(CardTable myCardTable, Hand hand, Hand computerHand, JLabel[] JLabels, boolean isBackCard, JPanel pnlPlayArea, JPanel pnlComputerArea, JPanel pnlYourHandArea) {
      for (int i = 0; i < NUM_CARDS_PER_HAND; i ++) {
         //Create an icon and store in an array later use.
         Icon icon = null;
         HighCardListener highCardListener = null;
         Card dealCard = hand.inspectCard(i);
         if (isBackCard) {
            icon = GUICard.getBackCardIcon();
         } else {
            icon = GUICard.getIcon(dealCard);
            highCardListener = new HighCardListener(myCardTable, dealCard, hand, computerHand, pnlPlayArea, pnlComputerArea, pnlYourHandArea);
         }
         JLabel jlabel = new JLabel(icon);
         if (highCardListener != null) {
            jlabel.addMouseListener(highCardListener);
         }
         JLabels[i] = jlabel;
      }
   }
   
   /**
    * Displays your hand.
    * @param myCardTable
    * @param yourHand
    */
   private static void displayHandArea(CardTable myCardTable, String areaTitle, JLabel[] JLables, JPanel pnlHand) {
      // My Hand
      Border border = new TitledBorder(areaTitle);
      pnlHand.setBorder(border);
      for (int i = 0; i < NUM_CARDS_PER_HAND; i ++) {
         pnlHand.add(JLables[i]);
      }
      myCardTable.add(pnlHand);
   }
   

   /**
    * 
    * @param myCardTable
    * @param computerCard
    * @param yourCard
    * @return
    */
   private static void displayPlayingArea(JPanel pnlPlayArea, CardTable myCardTable, Card computerCard, Card yourCard ) {

      //Play Computer Card
      JLabel computerCardJLabel = null;
      if (computerCard != null) {
         Icon computerCardIcon = GUICard.getIcon(computerCard);
         computerCardJLabel = new JLabel(computerCardIcon);
         playedCardLabels[0] = computerCardJLabel;
      }
      JLabel computerLabel = new JLabel( "Computer", JLabel.CENTER );
      playLabelText[0] = computerLabel;


      //Play Your Card
      JLabel yourCardJLabel = null;
      if (yourCard != null) {
         Icon yourCardIcon = GUICard.getIcon(yourCard);
         yourCardJLabel = new JLabel(yourCardIcon);
         playedCardLabels[1] = yourCardJLabel;
      }
      JLabel yourHandLabel = new JLabel( "You", JLabel.CENTER );
      playLabelText[1] = yourHandLabel;

      //Add labels to the play area
      
      if (computerCardJLabel != null) {
         pnlPlayArea.add(computerCardJLabel);
      }
      if (yourCardJLabel != null) {
         pnlPlayArea.add(yourCardJLabel);
      }
      
      pnlPlayArea.add(computerLabel);
      pnlPlayArea.add(yourHandLabel);

      myCardTable.add(pnlPlayArea);
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
    * 
    * @author charlesk
    *
    */
   private static class HighCardListener implements MouseListener {
      private CardTable myCardTable;
      private Card card;
      private Hand hand;
      private Hand computerHand;
      private JPanel pnlPlayArea;
      private JPanel pnlComputerArea;
      private JPanel pnlYourHandArea;
      
      HighCardListener(CardTable myCardTable, Card card, Hand hand, Hand computerHand, JPanel pnlPlayArea, JPanel pnlComputerArea, JPanel pnlYourHandArea) {
         this.myCardTable = myCardTable;
         this.card = card;
         this.hand = hand;
         this.computerHand = computerHand;
         this.pnlPlayArea = pnlPlayArea;
         this.pnlComputerArea = pnlComputerArea;
         this.pnlYourHandArea = pnlYourHandArea;
      }

      @Override
      public void mouseClicked(MouseEvent e) {
         // Play the card to the playing are when clicked.
         //1) Add the card to the playing area
         Icon icon = GUICard.getIcon(card);
         JLabel iconJLabel = new JLabel(icon);
         playedCardLabels[0] = iconJLabel;
         pnlPlayArea.add(iconJLabel);
         
         //2) remove the card from the hand and hand area.
         for (int i = 0; i < hand.getNumCards(); i ++) {
            Card card = hand.inspectCard(i);
            if (card.getValue() == this.card.getValue() && card.getSuit() == this.card.getSuit()) {
               hand.playCard(i);
               break;
            }
         }
         pnlYourHandArea.remove(e.getComponent());
         
         //3) Randomly pick and add the card from the computer hand and remove it from the hand as well.
         int computerHandCards = this.computerHand.getNumCards() - 1;
         Random r = new Random();
         int cCard = r.nextInt((computerHandCards - 0) + 1);

         Card computerCard = computerHand.playCard(cCard);
         icon = GUICard.getIcon(computerCard);
         iconJLabel = new JLabel(icon);
         playedCardLabels[1] = iconJLabel;
         pnlPlayArea.add(iconJLabel);
         pnlComputerArea.remove(cCard);
         
         //Refresh the JFrame
         myCardTable.revalidate();

         //Determine who won
         if (this.card.getValueAsInt() > computerCard.getValueAsInt()) {
            JOptionPane.showMessageDialog(myCardTable, "You Won!");
         } else {
            JOptionPane.showMessageDialog(myCardTable, "Sorry You Lost!");
         }
         
         //Clean up the playing area
         pnlPlayArea.removeAll();
         myCardTable.revalidate();
      }

      @Override
      public void mousePressed(MouseEvent e) {
         // TODO Auto-generated method stub
      }

      @Override
      public void mouseReleased(MouseEvent e) {
         // TODO Auto-generated method stub
      }

      @Override
      public void mouseEntered(MouseEvent e) {
         // TODO Auto-generated method stub
      }

      @Override
      public void mouseExited(MouseEvent e) {
         // TODO Auto-generated method stub
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

class CardGameFramework
{
   private static final int MAX_PLAYERS = 50;

   private int numPlayers;
   // # standard 52-card packs per deck, ignoring jokers or unused cards
   private int numPacks;            
   // if 2 per pack & 3 packs per deck, get 6
   private int numJokersPerPack;
   // # cards removed from each pack
   private int numUnusedCardsPerPack;
   // # cards to deal each player
   private int numCardsPerHand;
   // holds the initial full deck and gets smaller (usually) during play
   private Deck deck;       
   // one Hand for each player
   private Hand[] hand;  
   // an array holding the cards not used in the game. e.g. pinochle does not
   // use cards 2-8 of any suit
   private Card[] unusedCardsPerPack;

   public CardGameFramework(int numPacks, int numJokersPerPack,
       int numUnusedCardsPerPack,  Card[] unusedCardsPerPack,
       int numPlayers, int numCardsPerHand)
   {
      int k;

      // filter bad values
      if (numPacks < 1 || numPacks > 6) {
         numPacks = 1;
      }
      
      if (numJokersPerPack < 0 || numJokersPerPack > 4) {
         numJokersPerPack = 0;
      }
      
      //  > 1 card
      if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) {
         numUnusedCardsPerPack = 0;
      }
      
      if (numPlayers < 1 || numPlayers > MAX_PLAYERS) {
         numPlayers = 4;
      }
      
      // one of many ways to assure at least one full deal to all players
      if (numCardsPerHand < 1 ||
            numCardsPerHand >  numPacks * (52 - numUnusedCardsPerPack) / numPlayers) {
         numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;
      }

      // allocate
      this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
      this.hand = new Hand[numPlayers];

      for (k = 0; k < numPlayers; k++) {
         this.hand[k] = new Hand();
      }
      
      deck = new Deck(numPacks);

      // assign to members
      this.numPacks = numPacks;
      this.numJokersPerPack = numJokersPerPack;
      this.numUnusedCardsPerPack = numUnusedCardsPerPack;
      this.numPlayers = numPlayers;
      this.numCardsPerHand = numCardsPerHand;

      for (k = 0; k < numUnusedCardsPerPack; k++) {
         this.unusedCardsPerPack[k] = unusedCardsPerPack[k];
      }

      // prepare deck and shuffle
      newGame();
   }

   // constructor overload/default for game like bridge
   public CardGameFramework()
   {
      this(1, 0, 0, null, 4, 13);
   }

   public Hand getHand(int k)
   {
      // hands start from 0 like arrays

      // on error return automatic empty hand
      if (k < 0 || k >= numPlayers) {
         return new Hand();
      }

      return hand[k];
   }

   public Card getCardFromDeck()
   {
      return deck.dealCard();
   }

   public int getNumCardsRemainingInDeck()
   {
      return deck.getNumCards();
   }

   public void newGame()
   {
      int k, j;

      // clear the hands
      for (k = 0; k < numPlayers; k++) {
         hand[k].resetHand();
      }

      // restock the deck
      deck.init(numPacks);

      // remove unused cards
      for (k = 0; k < numUnusedCardsPerPack; k++) {
         deck.removeCard( unusedCardsPerPack[k] );
      }

      // add jokers
      for (k = 0; k < numPacks; k++) {
         for ( j = 0; j < numJokersPerPack; j++) {
            deck.addCard( new Card('X', Card.Suit.values()[j]) );
         }
      }

      // shuffle the cards
      deck.shuffle();
   }

   public boolean deal()
   {
      // returns false if not enough cards, but deals what it can
      int k, j;
      boolean enoughCards;

      // clear all hands
      for (j = 0; j < numPlayers; j++) {
         hand[j].resetHand();
      }

      enoughCards = true;
      for (k = 0; k < numCardsPerHand && enoughCards ; k++) {
         for (j = 0; j < numPlayers; j++) {
            if (deck.getNumCards() > 0) {
               hand[j].takeCard( deck.dealCard() );
            } else {
               enoughCards = false;
               break;
            }
         }
      }

      return enoughCards;
   }

   void sortHands()
   {
      int k;

      for (k = 0; k < numPlayers; k++) {
         hand[k].sort();
      }
   }

   Card playCard(int playerIndex, int cardIndex)
   {
      // returns bad card if either argument is bad
      if (playerIndex < 0 ||  playerIndex > numPlayers - 1 ||
            cardIndex < 0 || cardIndex > numCardsPerHand - 1) {
         //Creates a card that does not work
         return new Card('M', Card.Suit.spades);      
      }
 
      // return the card played
      return hand[playerIndex].playCard(cardIndex);
 
   }

   boolean takeCard(int playerIndex)
   {
      // returns false if either argument is bad
      if (playerIndex < 0 || playerIndex > numPlayers - 1) {
         return false;
      }
   
      // Are there enough Cards?
      if (deck.getNumCards() <= 0) {
         return false;
      }

      return hand[playerIndex].takeCard(deck.dealCard());
   }
}
