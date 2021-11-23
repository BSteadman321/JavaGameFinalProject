import javax.swing.*;
import java.awt.*;

public class AceLabel extends JLabel{
    private AceDeck deck;
    private Image AceDeckImage;
    private int posX, posY;

	public AceLabel(String suit){
		super();
		super.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CardImages/" + suit + ".jpg")));
		deck = new AceDeck(suit);
	}

    public boolean canAddCard(Card c){
    	return deck.canAddCard(c);
    }
	
	public void addCard(Card c){
    	deck.addCard(c);
		if(deck.isComplete()){
			deck.getTopCard().setFaceDown();
		}
		AceDeckImage = new ImageIcon(getClass().getClassLoader().getResource(deck.getTopCard().Display())).getImage();
    	setIcon(new ImageIcon(AceDeckImage));
    }

    public boolean isFull(){
		if(deck.isComplete())
			return true;
		return false;
	}
}