public class pileDeck extends DeckOfCards{
	private int MAX_SIZE = 19;

	//this is here for us to populate the piles at the start without the rules of the piles
	public void InitialAdd(Card c){
		super.addCard(c);
	}

	public void InitialAdd(DeckOfCards d, int number_of_cards){
		for(int i=0; i<number_of_cards; ++i){
			super.addCard(d.DealTopCard());
		}
		makeCardsFaceDown();
		getTopCard().setFaceUp();
	}

	public boolean addPile(pileDeck p){
		if(canAddPile(p)){
			int Size = p.size();
			for(int i=0; i< Size; ++i){
				addCard(p.get(i));
			}
			p.makeEmpty();
			return true;
		}
		return false;
	}

	public boolean canAddPile(pileDeck p){
		if(p == null)
			return false;
		if(numCards == MAX_SIZE)
			return false;
		
		//only a king on the bottom can be placed in empty pile
		if(isEmpty() && p.getBottomCard().getNumber() == 13){ 
			return true;
		}

		//correct placement gate
		if(p.getBottomCard().Color() != getTopCard().Color()){
			if(p.getBottomCard().getNumber()+1 == getTopCard().getNumber()){
				return true;
			}
		}
		return false;
	}

	public Card getBottomCard(){
		return deck.get(0);
	}

	@Override
	public boolean addCard(Card c){
		if(canAddCard(c)){
			super.addCard(c);
			return true;
		}
		return false;	
	}

	public boolean canAddCard(Card c){
		if(c == null)
			return false;
		if(numCards == MAX_SIZE)
			return false;

		if(numCards == 0 && c.getNumber() == 13){ 
			return true;
		}else if(numCards==0){
			return false;
		}

		if(c.Color() != getTopCard().Color()){
			if(c.getNumber()+1 == getTopCard().getNumber()){
				return true;
			}
		}
		return false;
	}

	//method for indexing a pileDeck (from 0 to numCards-1)
	public Card get(int index){
		return deck.get(index);
	}

	//used when we have successfully added this pile somewhere to empty it
	public void makeEmpty(){
		deck.removeAllElements();
		numCards = 0;
	}

	public pileDeck createSubPile(int index){
		pileDeck newPile = new pileDeck();
		int s = numCards;

		for(int i=index; i< s; ++i){
			//newPile.InitialAdd(deck.get(index));
		}
		return null;
	}
}
