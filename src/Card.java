//Card class --------------------------------------------------------------------
class Card
{   
// type and constants
public enum Suit { clubs, diamonds, hearts, spades }

static char DEFAULT_VAL = 'A';
static Suit DEFAULT_SUIT = Suit.spades;

// private data
private char value;
private Suit suit;
boolean errorFlag;

// 4 overloaded constructors
public Card(char value, Suit suit)
{   // because mutator sets errorFlag, we don't have to test
  set(value, suit);
}

public Card(char value)
{
  this(value, Suit.spades);
}

public Card()
{
  this(DEFAULT_VAL, DEFAULT_SUIT);
}

// copy constructor
public Card(Card card)
{
  this(card.value, card.suit);
}

// mutators
public boolean set(char value, Suit suit)
{
  char upVal;            // for upcasing char

  // convert to uppercase to simplify
  upVal = Character.toUpperCase(value);

  if ( !isValid(upVal, suit))
  {
     errorFlag = true;
     return false;
  }
  
  // else implied
  errorFlag = false;
  this.value = upVal;
  this.suit = suit;
  return true;
}

// accessors
public char getValue()
{
  return value;
}

public Suit getSuit()
{
  return suit;
}

public boolean isErrorFlag()
{
  return errorFlag;
}

// stringizer
public String toString()
{
  String retVal;

  if (errorFlag)
     return "** illegal **";

  // else implied
  retVal =  String.valueOf(value);
  retVal += " of ";
  retVal += String.valueOf(suit);

  return retVal;
}

// helper
private static boolean isValid(char value, Suit suit)
{
  // don't need to test suit (enum), but leave in for clarity
  char upVal;  // string to hold the 1-char value
  String legalVals = "23456789TJQKA";
  
  // convert to uppercase to simplify (need #include <cctype>)
  upVal = Character.toUpperCase(value);

  // check for validity
  if ( legalVals.indexOf(upVal) >= 0 )
     return true;
  else
     return false;
}

public boolean equals(Card card)
{
  if (this.value != card.value)
     return false;
  if (this.suit != card.suit)
     return false;
  if (this.errorFlag != card.errorFlag)
     return false;
  return true;
}
}