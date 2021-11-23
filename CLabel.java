import javax.swing.*;

public class CLabel extends JLabel{
	private Card card;
	private int x,y;
	private PileLabelManager belongs_to;

	public CLabel(Card c, PileLabelManager p){
		super.setIcon(new ImageIcon(getClass().getClassLoader().getResource(c.Display())));
		card = c;
		belongs_to = p;
	}

	public CLabel(PileLabelManager p){
		super.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CardImages/anchor.jpg")));
		card = null;
		belongs_to = p;
	}

	public Card getCard(){
		return card;
	}

	public void setXY(int x, int y){
		this.x = x;
		this.y = y;
	}

	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

	public PileLabelManager Manager(){
		return belongs_to;
	}

	@Override
	public void setLocation(int x, int y){
		super.setLocation(x,y);
	}

	public boolean isAnchor(){
		return (card == null);
	}
}