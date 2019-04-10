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

/*
 * A class that represents a playing card
 */
class Card
{
   private char value;
   private boolean errorFlag;
   private Suit suit;
   public enum Suit {clubs, diamonds, hearts, spades};

   public static char[] ORDERED_CARD_VALUES = new char[] 
         {'A','2','3','4','5','6','7','8','9','T','J','Q','K','X'};
   
   // Default card is 'A of spades'
   public Card()
   {
      this('A', Suit.spades);
   }

   public Card(char value, Suit suit)
   {
      set(value, suit);
   }

   /*
    * Sets the value and suit of a Card if value and suit are valid.
    * If valid, set errorFlag to false.
    * If invalid, set errorFlag to true.
    * Returns true/false if the value and suit were set
    */
   public boolean set(char value, Suit suit)
   {
      boolean wasSet;

      if (isValid(value, suit)) {
         setValue(value);
         setSuit(suit);
         errorFlag = false;
         wasSet = true;
      } else {
         errorFlag = true;
         wasSet = false;
      }
      
      return wasSet;
   }

   /*
    * Returns a string representation of a Card object if valid
    * otherwise returns an error message
    */
   public String toString()
   {
      String message;

      if (!errorFlag) {
         message = String.format("%s of %s", value, suit);
      } else {
         message = "ERROR: Invalid card";
      }
      
      return message;
   }

   /*
    * Checks the equality of two Card objects.
    * Returns true if card value and card suit match
    * Returns false otherwise
    */
   public boolean equals(Card card)
   {
      boolean areEqual;
      
      if(value == card.value && suit == card.suit
            && errorFlag == card.errorFlag) {
         areEqual = true;
      } else {
         areEqual = false;
      }
      
      return areEqual;
   }

/*
    * Checks the validity of value and suit.
    * Valid values: A, 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, X (Joker)
    * Valid suits: spades, hearts, diamonds, clubs.
    * Note: suits are not validated at this time
    */
   private boolean isValid(char value, Suit suit)
   {
      boolean validCard;
      
      switch(value) {
         case 'A':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
         case 'T':
         case 'J':
         case 'Q':
         case 'K':
         case 'X':
            validCard = true;
            break;
         default:
            validCard =  false;
      }
      
      return validCard;
   }

   public Suit getSuit()
   {
      return suit;
   }

   public boolean setSuit(Suit suit)
   {
      this.suit = suit;
      return true;
   }

   public char getValue()
   {
      return value;
   }

   public int getValueAsInt() {
      int ret = 0;
      switch(value) {
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
   
   public int getSuitAsInt() {
      int ret = 0;
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
   
   public void setValue(char value)
   {
      this.value = value;
   }

   public boolean isErrorFlag()
   {
      return errorFlag;
   }
   
   /**
    * 
    * @return ordered card values
    */
   public static char[] valuRanks() {
      return ORDERED_CARD_VALUES;
   }
   
   /**
    * Sort the incoming array of cards using a bubble sort routine.
    * Sort by Value and suit. The suits are ordered as follows: spades, hearts, diamonds, clubs.
    * @param cards
    */
   public static void arraySort(Card[] cards) {
      int n = cards.length;
      // Sort by Value first.
      for (int i = 0; i < n - 1; i++) {
         for (int j = 0; j < n - i - 1; j++) {
            if (cards[j].getValueAsInt() > cards[j + 1].getValueAsInt()) {
               // swap cards[j+1] and cards[i]
               Card temp = cards[j];
               cards[j] = cards[j + 1];
               cards[j + 1] = temp;
            }
         }
      }
      // Sort by Suit
      for (int i = 0; i < n - 1; i++) {
         for (int j = 0; j < n - i - 1; j++) {
            if (cards[j].getSuitAsInt() > cards[j + 1].getSuitAsInt()) {
               // swap cards[j+1] and cards[i]
               Card temp = cards[j];
               cards[j] = cards[j + 1];
               cards[j + 1] = temp;
            }
         }
      }
   }
}

/*
 * A class that represents the source of the cards for dealing and,
 * as the game progresses, the place from which players can receive new cards
 */
class Deck
{
   public static final int MAX_CARDS = 6 * 56;
   private static Card[] masterPack = new Card[56];
   private Card[] cards;
   private int topCard;
   
   //Overload the deck class; default only 1 class
   public Deck ()
   {
      this(1);
   }
   
   //Deck class with number of packs as the argument
   public Deck(int numPacks)
   {
      allocateMasterPack();

      //Fatal error if more than 6 decks used. 
      if (numPacks * 56 > MAX_CARDS) {
         System.out.println("Fatal Error. Too many decks. Need "
               + "count between 1 and 6");
         System.exit(0);
      }

      this.cards = new Card[56 * numPacks];
      init(numPacks);
   }
   
   //Initiates the cards array from the masterPack
   public void init(int numPacks)
   {
      topCard = 56 * numPacks - 1;
      int x = 0;

      for(int i = 0; i < numPacks; i++) {
         for(int j = 0; j < 56; j++) {
            cards[x++] = masterPack[j];
         }
      }
   }
   
   //Shuffle changes the position of the cards
   public void shuffle()
   {
      int rand;
      Card temp;

      for(int i = 0; i < cards.length; i++) {
         temp = cards[i]; //create a temp variable for the current card
         //Math.random is used to get a random card from the array to switch
         //with the current card.
         rand = (int)(Math.random() * (cards.length - 1));
         this.cards[i] = this.cards[rand];
         this.cards[rand] = temp;
         
      }
   }
   
   //returns the top card in the deck and removes 1 from topCard
   public Card dealCard()
   {
      return this.cards[topCard--];
   }
   
   //selects the top card index
   public int getTopCard()
   {
      return topCard;
   }
   
   //inspects the card to see if it is valid within the deck
   public Card inspectCard(int k)
   {
      Card cardToInspect;

      if(this.cards == null || this.cards.length == 0
            || k > this.cards.length) {
         Card errorCard = new Card();
         errorCard.setValue('E');
         cardToInspect = errorCard; 
      } else {
         cardToInspect = this.cards[k];
      }

      return cardToInspect;
   }
   
   //creates the master pack of 56 cards that does not change
   private static void allocateMasterPack()
   {
      int k = 0; //used to count the index of the masterPack deck.
      Card.Suit[] suits = Card.Suit.values();
      char[] cardValues = {'A', '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'X'};

      //if null then create new deck
      if(masterPack[51] == null) {
         for(Card.Suit suit : suits) {
            for(char value : cardValues) {
               masterPack[k++] = new Card(value, suit);
            }
         }
      }
   }
   
   // make sure that there are not too many instances of the card in the deck if you add it.
   // Return false if there will be too many.  It should put
   // the card on the top of the deck.
   public boolean addCard(Card card)
   {
      if (this.cards == null || this.topCard == this.cards.length) {
         return false;
      }
      this.cards[topCard++] = card;
      return true;
   }
   
   // you are looking to remove a specific card from the deck.  Put the
   // current top card into its place.  Be sure the card you need is actually
   // still in the deck, if not return false.
   public boolean removeCard(Card card)
   {
      if (this.cards == null || this.cards.length == 0) {
         return false;
      }
      boolean exists = false;
      for (int i = 0; i < this.cards.length; i ++) {
         Card cardInDeck = this.cards[i];
         if (cardInDeck.getValue() == card.getValue() &&
               cardInDeck.getSuit() == card.getSuit()) {
            //Found the matching card. Remove it and put the current top card into its place
            exists = true;
            this.cards[i] = this.cards[topCard];
            this.cards[topCard] = null;
            topCard--;
         }
      }
      return exists;
   }
   
   /**
    * put all of the cards in the deck back into the right order according to their values and suits. 
    */
   public void sort()
   {
      Card.arraySort(this.cards);
   }
   
   // return the number of cards remaining in the deck.
   public int getNumCards()
   {
      return this.cards.length;
   }
}

/**
 * A class that represents the cards held by a single player.
 * 
 * Hand object usually contains several cards, so we'll need an array of Card objects (myArray) as the principal member of the Hand class.  
 * Since each game deals a different number of cards into its players hands, and even within a game the number of cards in a hand will increase or decrease,
 * we must keep track of this with an int value (numCards).  
 * 
 * We'll need constructors, mutators, etc., of course.  
 * 
 * We'll also want a way for the hand to receive a card (from the deck or somewhere else), 
 * 
 * and play a card (to the table or to another player).  
 * 
 * These two methods will be called takeCard() and playCard(), respectively. 
 * 
 * Since this class has no information about the game being played, it always puts new cards received by takeCard() into the next available location of the array (index position numCards) 
 * 
 * and plays a card via playCard() from the highest occupied location (index position numCards - 1).  
 * 
 * The client game application would somehow prepare this highest position with the correct card to be played before calling Hand's playCard() method. 
 * 
 *  This detail is not our concern.
 * @author charlesk
 *
 */
class Hand
{
    public static final int MAX_CARDS = 100;
    private Card[] myCards;
    private int numCards;
    
    public Hand() {
       //create a default array of Cards
       this.myCards = new Card[MAX_CARDS];
    }
    
    /**
     * 
     * @return a number of cards
     */
    public int getNumCards() {
       return numCards;
    }

    /**
     * 
     * @param numCards
     */
    public boolean setNumCards(int numCards) {
       this.numCards = numCards;
       return true;
    }

    /**
     * remove all cards from the hand (in the simplest way).
     */
    public void resetHand() {
       //nothing to reset as myCards is either null or it's reset already.
       if (this.myCards == null || this.myCards.length == 0) {
          return;
       }
     
       this.numCards = 0;
       this.myCards = new Card[MAX_CARDS];
    }
    
    /**
     * adds a card to the next available position in the myCards array.
     * This is an object copy, not a reference copy, since the source of the Card might destroy or change 
     * its data after our Hand gets it -- we want our local data to be exactly as it was when we received it.
     * @param card
     * @return true if card was successfully added to the myCards array, otherwise return false.
     */
    public boolean takeCard(Card card) {
       boolean cardWasTaken;
 
       if (this.numCards == this.myCards.length) {
          cardWasTaken = false;
       } else {
          this.myCards[numCards++] = new Card(card.getValue(), card.getSuit());
          cardWasTaken = true;  
       }
        
       return cardWasTaken;
    }
    
    /**
     * 
     * @return Card and removes the card in the top occupied position of the array.
     */
    public Card playCard() {
       Card card = this.myCards[--numCards];
       this.myCards[numCards] = null;
       return card; 
    }

    /**
     * 
     */
    @Override
    public String toString() {
       StringBuilder myCards = new StringBuilder();
  
       for (int i = 0; i < this.numCards; i ++) {
          myCards.append(this.myCards[i]).append(" ");
       }
  
       return myCards.toString();
    }
    
    /**
     * Accessor for an individual card.
     * 
     * @param k
     * @return a card with errorFlag = true if k is bad.
     */
    public Card inspectCard(int k) {
       Card cardToInspect = null;
  
       if (this.myCards == null || this.myCards.length == 0 || k > this.myCards.length) {
          Card errorCard = new Card();
          errorCard.setValue('E');
          cardToInspect =  errorCard;
       } else {
         cardToInspect = this.myCards[k];
       }
  
       return cardToInspect;
    }
    
    /**
     * 
     * @param cardIndex
     * @return
     */
    public Card playCard(int cardIndex)
    {
       if ( numCards == 0 ) {
          //Creates a card that does not work
          return new Card('M', Card.Suit.spades);
       }
       
       //Decreases numCards.
       Card card = myCards[cardIndex];
       
       numCards--;
       for(int i = cardIndex; i < numCards; i++) {
          myCards[i] = myCards[i+1];
       }
       
       myCards[numCards] = null;
       
       return card;
    }
    
    /**
     * will sort the hand by calling the arraySort() method in the Card class
     */
    public void sort()
    {
       Card.arraySort(this.myCards);
    }
}