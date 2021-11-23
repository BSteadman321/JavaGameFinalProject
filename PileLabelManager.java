import javax.swing.*;
import java.util.Vector;

public class PileLabelManager{
    private pileDeck pile;
    private CLabel anchor;
    private int anchorXbound, anchorYbound;
    private Vector<CLabel> cards;
    private Panel refPanel;
    private int x,y;
    private dNdListener listener;

    public PileLabelManager(DeckOfCards d, int size, Panel p){
        super();
        listener = new dNdListener(p);
        refPanel = p;
        pile = new pileDeck();
        pile.InitialAdd(d, size);
        pile.makeCardsFaceDown();
        pile.getTopCard().setFaceUp();
       	anchor = new CLabel(this);
        cards = new Vector<>(pile.size());
        for(int i=0; i<pile.size(); ++i){
        	cards.add(new CLabel(pile.get(i), this));
            cards.get(i).addMouseListener(listener);
    		cards.get(i).addMouseMotionListener(listener);
        }
    }

    public JLabel getAnchor(){
    	return anchor;
    }

    public void setBounds(int x , int y, int width, int height){
    	anchorXbound = x;
    	anchorYbound = y;
        this.x = width;
        this.y = height;
    	anchor.setBounds(x,y,width,height);
    }

    public void add(){
    	CLabel temp;
    	for(int i=pile.size()-1; i>=0; --i){
    		temp = cards.get(i);
    		temp.setXY(anchorXbound,anchorYbound+(10*i));
    		temp.setBounds(temp.getX(), temp.getY(), anchor.getWidth(), anchor.getHeight());
    		refPanel.add(temp);

    	}
    }

    public void addCard(Card c){
    	pile.addCard(c);
    	cards.add(new CLabel(pile.getTopCard(),this));
    	CLabel temp =cards.lastElement();

        if(pile.size()==1){
        	//adding to the anchor
            temp.setXY(anchorXbound, anchorYbound);
            refPanel.remove(anchor);
        }else{
            temp.setXY(anchorXbound,cards.get(cards.size()-2).getY()+25);
        }

    	//temp.setBounds(0,0,anchor.getWidth(), anchor.getHeight());
    	temp.setBounds(temp.getX(), temp.getY(), anchor.getWidth(), anchor.getHeight());
    	temp.addMouseListener(listener);
    	temp.addMouseMotionListener(listener);
    	refPanel.add(temp);
    	refPanel.setComponentZOrder(temp, 0);
    	refPanel.repaint();
    }

    public boolean canAddCard(Card c){
    	return pile.canAddCard(c);
    }

    public void removeTop(){
    	pile.DealTopCard();
    	cards.remove(cards.size()-1);

    	if(cards.size()>0){
    		cards.lastElement().getCard().setFaceUp();
    		cards.lastElement().setIcon(new ImageIcon(getClass().getClassLoader().getResource(cards.lastElement().getCard().Display())));
    	}else{
            anchor.setXY(anchorXbound, anchorYbound);
            anchor.setBounds(anchor.getX(), anchor.getY(), x,y);
            refPanel.add(anchor);
            refPanel.setComponentZOrder(anchor, 1);
        }
    }

    public CLabel[] getRestOfComponents(CLabel cl){
    	int index = cards.indexOf(cl);
    	CLabel[] retval = new CLabel[cards.size()-index];

    	if(index == cards.size()-1){
    		retval[0] = cl;
		}else{
			int count=0;
	    	for(int i=index; i<cards.size(); ++i){
   		 		retval[count++] = cards.get(i);
    		}
    	}
    	return retval;
    }

    public boolean contains(int x, int y){
    	int thisx = 0;
    	int thisy =0;

    	for(int i=0; i<pile.size(); ++i){
    		thisx = ((CLabel)cards.get(i)).getLocationOnScreen().x;
    		thisy = ((CLabel)cards.get(i)).getLocationOnScreen().y;

    		if(cards.get(i).contains(x-thisx,y-thisy))
    			return true;
    	}
        if(pile.size()==0){
            thisx = anchor.getLocationOnScreen().x;
            thisy = anchor.getLocationOnScreen().y;

            if(anchor.contains(x-thisx,y-thisy))
                return true;
        }   
    	return false;
    }

    public int numCardsBelow(CLabel cl){
    	int retval = cards.indexOf(cl);
        
    	if(retval != -1){
    		return cards.size()-1-retval;
    	}
    	return -1;
    }

    public Panel getPanel(){
        return refPanel;
    }

}
