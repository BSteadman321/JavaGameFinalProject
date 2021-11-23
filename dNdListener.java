import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.*;
import java.awt.*;

public class dNdListener implements MouseListener, MouseMotionListener {
	private Panel refPanel;
	private Card c;
	private int numCardsBelow;
	private JLabel lab;
	private boolean valid;
	private CLabel[] cardsbelow;
	private JLabel[] labs;

	public dNdListener(Panel p){
		refPanel = p;
		numCardsBelow=0;
		lab = null;
		valid = false;
		cardsbelow = null;
	}

	public void mousePressed(MouseEvent e){
		CLabel temp = (CLabel)e.getComponent();

		if(e.getButton() ==1){
			if(temp.getCard().isFaceUp()){
				c = temp.getCard();
				refPanel.setCard(c);
				numCardsBelow = temp.Manager().numCardsBelow(temp);
	
				lab = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(((CLabel)e.getComponent()).getCard().Display())));
   			 	lab.setBounds(((CLabel)e.getSource()).getX(),((CLabel)e.getComponent()).getY(), 110, 140 );
   			 	cardsbelow = ((CLabel)e.getComponent()).Manager().getRestOfComponents(temp);
   			 	labs = new JLabel[numCardsBelow+1];

   			 	for(int k=0; k<=numCardsBelow; ++k){
    				labs[k] = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(cardsbelow[k].getCard().Display())));
    				labs[k].setBounds(((CLabel)e.getSource()).getX(),((CLabel)e.getComponent()).getY()+(25*k), 110, 140 );
    				refPanel.add(labs[k]);
    				refPanel.setComponentZOrder(labs[k], numCardsBelow-k);
    				cardsbelow[k].setVisible(false);
    			}

			    valid = true;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e){
		if(e.getButton() ==1){
			int s = refPanel.getComponents().length;
        	int mXLoc= e.getXOnScreen();
        	int mYLoc= e.getYOnScreen();
        	int xLoc=0;
        	int yLoc=0;
        	boolean placed = false;

        	if(valid){
	    	    for(int i=0; i<=numCardsBelow; ++i){
			        cardsbelow[i].setVisible(true);
	    		}
	    	    //playDeckLabel.setLocation(me.getXOnScreen()-xLoc-60, me.getYOnScreen()-yLoc-110);
	
				for(int i=0; i<s; ++i){
					Component temp = refPanel.getComponents()[i];
					if(temp instanceof AceLabel){
						xLoc=temp.getLocationOnScreen().x;
							yLoc=temp.getLocationOnScreen().y;
						if(temp.contains(mXLoc-xLoc, mYLoc-yLoc) && ((AceLabel)temp).canAddCard(refPanel.getCard()) && numCardsBelow==0){
							xLoc=temp.getLocationOnScreen().x;
							yLoc=temp.getLocationOnScreen().y;
							((AceLabel)temp).addCard(c);
							((CLabel)e.getComponent()).Manager().removeTop();
							refPanel.remove(e.getComponent());
							placed = true;
							break;
						}
					}else if(temp instanceof CLabel && !((CLabel)temp).isAnchor() && ((CLabel)temp).getCard().isFaceUp()){
	
						xLoc=temp.getLocationOnScreen().x;
						yLoc=temp.getLocationOnScreen().y;
						
						if(temp.contains(mXLoc-xLoc, mYLoc-yLoc)){
							if(((CLabel)temp).Manager().canAddCard(refPanel.getCard())){
								PileLabelManager goingOnMan = ((CLabel)temp).Manager();
								PileLabelManager comingFromMan = ((CLabel)e.getComponent()).Manager();
								//CLabel lab = (CLabel)e.getComponent();
								//CLabel[] cardsbelow = comingFromMan.getRestOfComponents(lab);
	
								for(int k=0; k<=numCardsBelow; ++k){
									c = cardsbelow[k].getCard();
									goingOnMan.addCard(c);
									//System.out.println(k);
									//System.out.println(newComponents[k].getCard().Display());
									refPanel.remove(cardsbelow[k]);
	
								}
								for(int k=0; k<=numCardsBelow; ++k){
									comingFromMan.removeTop();
								}
								placed = true;
								break;
							}
	
						}
					}else if(temp instanceof CLabel && ((CLabel)temp).isAnchor()){
						xLoc=temp.getLocationOnScreen().x;
						yLoc=temp.getLocationOnScreen().y;
						
						if(temp.contains(mXLoc-xLoc, mYLoc-yLoc) && ((CLabel)temp).Manager().canAddCard(refPanel.getCard())){
							PileLabelManager goingOnMan = ((CLabel)temp).Manager();
							PileLabelManager comingFromMan = ((CLabel)e.getComponent()).Manager();
							//CLabel lab = (CLabel)e.getComponent();
							//CLabel[] cardsbelow = comingFromMan.getRestOfComponents(lab);
							for(int k=0; k<=numCardsBelow; ++k){
								c = cardsbelow[k].getCard();
								goingOnMan.addCard(c);

								refPanel.remove(cardsbelow[k]);
	
							}
							for(int k=0; k<=numCardsBelow; ++k){
								comingFromMan.removeTop();
							}
							placed = true;
							break;
							//((CLabel)temp).Manager().addCard(refPanel.getCard());
							//((CLabel)e.getComponent()).Manager().removeTop();
							//refPanel.remove(e.getComponent());
							//break;
						}
					}
					
				}

				if(!placed){
					//e.getComponent().setLocation(((CLabel)e.getComponent()).getX(), ((CLabel)e.getComponent()).getY());
				}
			}



			if(valid){
				for(int i=0; i<=numCardsBelow; ++i){
					refPanel.remove(labs[i]);
				}
			}

			numCardsBelow=0;
			refPanel.repaint();
			valid = false;
		}

		if(refPanel.Full()){
			JOptionPane.showMessageDialog(null, "Congratulations You've Won!", "Winner!", JOptionPane.PLAIN_MESSAGE);
		}
	}

	public void mouseDragged(MouseEvent e){
		int xLoc=refPanel.getTopLevelAncestor().getLocation().x;
		int yLoc=refPanel.getTopLevelAncestor().getLocation().y;

    	//((CLabel)e.getComponent()).setLocation(e.getXOnScreen()-xLoc-60, e.getYOnScreen()-yLoc-110);
    	if(valid){
    		for(int k=0; k<=numCardsBelow; ++k){
    			labs[k].setLocation(e.getXOnScreen()-xLoc-60, e.getYOnScreen()-yLoc-110);
    			refPanel.setComponentZOrder(labs[k], numCardsBelow-k);
    			yLoc = yLoc-25;
    		}
    		//lab.setLocation(e.getXOnScreen()-xLoc-60, e.getYOnScreen()-yLoc-110);
    		//e.getComponent().setVisible(false);
    	}
	}

	public void mouseExited(MouseEvent e){
	}

	public void mouseClicked(MouseEvent e){
	}

	public void mouseEntered(MouseEvent e){
	}
	
	public void mouseMoved(MouseEvent e){
	}
}