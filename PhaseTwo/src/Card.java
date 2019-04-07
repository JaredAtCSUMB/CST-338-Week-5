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