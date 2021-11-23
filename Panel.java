import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Panel extends JPanel {
    private int x, y;
    private DeckOfCards drawDeck, playDeck;
    private JLabel drawDeckLabel, playDeckLabel, prevCardLabel;
    private PileLabelManager pile1Label, pile2Label, pile3Label, pile4Label, pile5Label, pile6Label, pile7Label;
    private AceLabel acePile1Label, acePile2Label, acePile3Label, acePile4Label;
    private Card c = null;
    private boolean isGameOver = false, mode;
    private static JTextField statusBox = new JTextField();

    //mode is true when hard and false when normal

    public Panel(boolean mode) {
        //sets up the piles and the drawDeck
        InitializePanel();

        this.mode = mode;

        //initializing the labels
        drawDeckLabel = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(drawDeck.getTopCard().Display())));
        playDeckLabel = new JLabel();
        playDeckLabel.setVisible(false);
        acePile1Label = new AceLabel("CLUBS");
        acePile2Label = new AceLabel("DIAMONDS");
        acePile3Label = new AceLabel("SPADES");
        acePile4Label = new AceLabel("HEARTS");

        //set bounds for cards
        acePile1Label.setBounds(400,25,110, 140);
        acePile2Label.setBounds(525,25,110, 140);
        acePile3Label.setBounds(650,25,110, 140);
        acePile4Label.setBounds(775,25,110, 140);
        drawDeckLabel.setBounds(25,25,110, 140);
        playDeckLabel.setBounds(150,25,110, 140);

        prevCardLabel = new JLabel();
        prevCardLabel.setBounds(150, 25, 110, 140);

        //adding the different labels
        add(acePile1Label);
        add(acePile2Label);
        add(acePile3Label);
        add(acePile4Label);
        add(drawDeckLabel);
        add(playDeckLabel);
        pile1Label.add();
        pile2Label.add();
        pile3Label.add();
        pile4Label.add();
        pile5Label.add();
        pile6Label.add();
        pile7Label.add();

        playDeckLabel.addMouseListener(new MouseAdapter(){
                public void mousePressed(MouseEvent me){
                    if(me.getButton() == 1){
                        x = playDeckLabel.getLocation().x;
                        y = playDeckLabel.getLocation().y;
                        c = playDeck.getTopCard();
                        setComponentZOrder(playDeckLabel, 0);
                    }
                }
                public void mouseReleased(MouseEvent me){
                    if(me.getButton() == 1){
                        //positional logic check
                        int ap1x = acePile1Label.getLocationOnScreen().x;
                        int ap2x = acePile2Label.getLocationOnScreen().x;
                        int ap3x = acePile3Label.getLocationOnScreen().x;
                        int ap4x = acePile4Label.getLocationOnScreen().x;
                        int ap1y = acePile1Label.getLocationOnScreen().y;
                        int ap2y = acePile2Label.getLocationOnScreen().y;
                        int ap3y = acePile3Label.getLocationOnScreen().y;
                        int ap4y = acePile4Label.getLocationOnScreen().y;
                        int mXLoc = me.getXOnScreen();
                        int mYLoc = me.getYOnScreen();
    
                        if(acePile1Label.contains(mXLoc-ap1x, mYLoc-ap1y) && acePile1Label.canAddCard(c)){
                            acePile1Label.addCard(playDeck.DealTopCard());
                        }else if(acePile2Label.contains(mXLoc-ap2x, mYLoc-ap2y) && acePile2Label.canAddCard(c)){
                            acePile2Label.addCard(playDeck.DealTopCard());
                        }else if(acePile3Label.contains(mXLoc-ap3x, mYLoc-ap3y) && acePile3Label.canAddCard(c)){
                            acePile3Label.addCard(playDeck.DealTopCard());
                        }else if(acePile4Label.contains(mXLoc-ap4x, mYLoc-ap4y) && acePile4Label.canAddCard(c)){
                            acePile4Label.addCard(playDeck.DealTopCard());
                        }
    
                        if(pile1Label.contains(mXLoc, mYLoc) && pile1Label.canAddCard(c)){
                            pile1Label.addCard(playDeck.DealTopCard());
                        }else if(pile2Label.contains(mXLoc, mYLoc) && pile2Label.canAddCard(c)){
                            pile2Label.addCard(playDeck.DealTopCard());
                        }else if(pile3Label.contains(mXLoc, mYLoc) && pile3Label.canAddCard(c)){
                            pile3Label.addCard(playDeck.DealTopCard());
                        }else if(pile4Label.contains(mXLoc, mYLoc) && pile4Label.canAddCard(c)){
                            pile4Label.addCard(playDeck.DealTopCard());
                        }else if(pile5Label.contains(mXLoc, mYLoc) && pile5Label.canAddCard(c)){
                            pile5Label.addCard(playDeck.DealTopCard());
                        }else if(pile6Label.contains(mXLoc, mYLoc) && pile6Label.canAddCard(c)){
                            pile6Label.addCard(playDeck.DealTopCard());
                        }else if(pile7Label.contains(mXLoc, mYLoc) && pile7Label.canAddCard(c)){
                            pile7Label.addCard(playDeck.DealTopCard());
                        }
    
                        UpdatePlayerDeckUI();

                        //revert the position of the label
                        playDeckLabel.setLocation(x,y);
                        x=0;
                        y=0;
                        repaint();
                    }
                }
        });

        playDeckLabel.addMouseMotionListener(new MouseMotionAdapter(){
                public void mouseDragged(MouseEvent me){
                    if(playDeck.size() > 1 ) {
                        prevCardLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(playDeck.getNextCard().Display())));
                        add(prevCardLabel);
                    }
                    else {
                        remove(prevCardLabel);
                    }
                    if(drawDeck.isEmpty() && playDeck.size() == 1){
                        drawDeckLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CardImages/back_of_card.jpg")));
                        drawDeckLabel.setEnabled(false);
                    }
                    int xLoc=getTopLevelAncestor().getLocation().x;
                    int yLoc=getTopLevelAncestor().getLocation().y;
                    playDeckLabel.setLocation(me.getXOnScreen()-xLoc-60, me.getYOnScreen()-yLoc-110);
                }
        });

        drawDeckLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent me) {
                    setComponentZOrder(drawDeckLabel, 0);

                    if(drawDeck.isEmpty() && playDeck.isEmpty() && !mode){
                        drawDeckLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CardImages/back_of_card.jpg")));
                        drawDeckLabel.setEnabled(false);
                    }else{
                        if(drawDeck.isEmpty() && !mode) {
                            drawDeck = playDeck.FlipDeck();
                            drawDeckLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource("CardImages/back_of_card.jpg")));
                        }
                    }
                        if(drawDeck.size() != 0) {
                            //add + update playDeckLabel
                            playDeck.addCard(drawDeck.DealTopCard());
                            playDeck.getTopCard().setFaceUp();
                            UpdatePlayerDeckUI();
                        }
                    if(drawDeck.isEmpty() && !playDeck.isEmpty() && !mode) {
                        drawDeckLabel.setIcon(new ImageIcon(getClass().getResource("CardImages/Restart.jpg")));
                    }else if(drawDeck.isEmpty() && !playDeck.isEmpty() && mode){
                        drawDeckLabel.setVisible(false);
                    }
                    repaint();
                }
        });//end of addMouseListener
    }

    private void UpdatePlayerDeckUI(){
        if(playDeck.isEmpty())
            playDeckLabel.setVisible(false);
        else{
            playDeckLabel.setVisible(true);
            playDeckLabel.setIcon(new ImageIcon(getClass().getClassLoader().getResource(playDeck.getTopCard().Display())));
        }
        if(Full())
            JOptionPane.showMessageDialog(null, "Congratulations You've Won!", "Winner!", JOptionPane.PLAIN_MESSAGE);
    }

    private void InitializePanel(){
        setLayout(null);

        drawDeck = new DeckOfCards();
        playDeck = new DeckOfCards();
        drawDeck.CreateShuffledDeck();

        pile1Label = new PileLabelManager(drawDeck, 1,this);
        pile2Label = new PileLabelManager(drawDeck, 2,this);
        pile3Label = new PileLabelManager(drawDeck, 3,this);
        pile4Label = new PileLabelManager(drawDeck, 4,this);
        pile5Label = new PileLabelManager(drawDeck, 5,this);
        pile6Label = new PileLabelManager(drawDeck, 6,this);
        pile7Label = new PileLabelManager(drawDeck, 7,this);

        pile1Label.setBounds(25,200,110, 140);
        pile2Label.setBounds(150,200,110, 140);
        pile3Label.setBounds(275,200,110, 140);
        pile4Label.setBounds(400,200,110, 140);
        pile5Label.setBounds(525,200,110, 140);
        pile6Label.setBounds(650,200,110, 140);
        pile7Label.setBounds(775,200,110, 140);

        drawDeck.makeCardsFaceDown();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        //Set background color
        g.setColor(new Color(0, 100, 0));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public Card getCard(){
        return c;
    }

    public void setCard(Card card){
        c = card;
    }
    
    public boolean Full(){
        if(acePile1Label.isFull() && acePile2Label.isFull() && acePile3Label.isFull() && acePile4Label.isFull()) {
            return true;
        }
        return false;
    }
}