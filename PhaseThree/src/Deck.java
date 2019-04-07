/*
 * A class that represents the source of the cards for dealing and,
 * as the game progresses, the place from which players can receive new cards
 */
public class Deck
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
