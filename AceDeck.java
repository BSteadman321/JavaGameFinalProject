//1 parameter constructor, used to init the suit of the ace pile.
//suit is all caps {"SPADES", "DIAMONDS", "CLUBS", "HEARTS"}
public class AceDeck extends DeckOfCards{
	private final int MAX_SIZE = 13;
	private String suit;

	public AceDeck(String s){
		super();	//creates a deck of 0 cards
		suit = new String(s);
	}

	@Override
	public boolean addCard(Card c){
		if(canAddCard(c)){
			super.addCard(c);
			return true;
		}
		return false;
	}

	//checks requirements of an AceDeck to see if a card can be added
	public boolean canAddCard(Card c){
		if(c == null)
			return false;
		if(isEmpty()){
			//System.out.println(c.Display());
			return (c.getSuit().equals(suit) && c.getNumber() ==1);
		}
		if(numCards < MAX_SIZE && c.getSuit().equals(suit)){
			if(getTopCard() !=null){
				return c.getNumber()-1 == getTopCard().getNumber();
			}
		}
		return false;
	}


	public boolean isComplete(){
		return numCards==MAX_SIZE;
	}
}