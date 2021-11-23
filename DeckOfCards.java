import java.lang.Integer;
import java.util.Vector;
import java.awt.image.BufferedImage;
//used to create the cards used in game

public class DeckOfCards{

	protected Vector<Card> deck;
	protected int numCards;

	public DeckOfCards(){
		deck = new Vector<Card>();
		numCards = 0;
	}

	public void CreateShuffledDeck(){	
		for(int i=0; i<52; ++i){
			addCard(new Card((i%13)+1, Card.ListOfSuits()[i/13]));
		}

		Shuffle(); //shuffles the deck
		numCards = 52;
	}

	public void Shuffle(){
		Card hold;
		int randIndex=0;
		for(int i=0; i<10; ++i){
			for(int j=0; j< numCards; ++j){
				randIndex = (int)(Math.random()*numCards);
				hold = deck.remove(randIndex);
				deck.add(hold);
			}
		}

	}

	//Takes the top card off the top and returns it. Reduces deck size by 1
	//RETURNS NULL IF IT IS EMPTY!!!
	public Card DealTopCard(){
		if(numCards == 0){
			return null;
		}
		numCards--;
		deck.get(numCards).setFaceUp();
		return deck.remove(numCards);
	}

	//does the same as DealTopCard but does NOT remove the card, only returns it.
	//RETURNS NULL IF IT IS EMPTY!!!
	public Card getTopCard(){
		if(numCards ==0)
			return null;
		return (deck.get(numCards-1));
	}

	public Card getNextCard(){
		if(numCards == 0)
			return null;
		return (deck.get(numCards - 2));
	}

	//adds a card if the card is not null.
	//increases deck size by 1
	public boolean addCard(Card c){
		if(c == null)
			return false;
		deck.add(c);
		numCards++;
		return true;
	}

	//this method can be used for when the draw pile is empty to create a new draw pile.
	//Also empties the current pile!!!!
	//NOTE: this calls DeckOfCards.addCard so it makes all cards returned face up!!!!
	public DeckOfCards FlipDeck(){		
		DeckOfCards ret = new DeckOfCards();

		while(numCards > 0){
			ret.addCard(this.DealTopCard());
		}
		return ret;
	}

	public void makeCardsFaceUp(){
		for(int i=0; i< numCards; ++i){
			deck.get(i).setFaceUp();
		}
	}

	public void makeCardsFaceDown(){
		for(int i=0; i< numCards; ++i){
			deck.get(i).setFaceDown();
		}
	}

	public int size(){
		return numCards;
	}
	
	public boolean isEmpty(){
		return (numCards == 0);
	}
	
}