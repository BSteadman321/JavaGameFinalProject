import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.*;

public class Board extends JFrame implements ActionListener{
    static protected Panel panel;
    private JPanel selection_screen;
    private boolean mode;
    private JButton easy, hard;

    public Board() {
        super("Klondike Solitaire");
        panel = null;
        main_menu();
    }

    public static void main(String[] args) {
        Board board = new Board();
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setSize(900, 775);
        board.setResizable(false);
        board.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(panel != null ) {
            panel.setVisible(false);
            remove(panel);
            main_menu();
        }
    }

    public void startGame(boolean mode){
        panel = new Panel(mode);
        panel.setPreferredSize(new Dimension(600, 900));
        add(panel);

        top_menu();
    }

    public void top_menu(){
        String readIn = "";

        JMenu menu = new JMenu("Help");
        menu.setMnemonic('H');

        JMenuItem restartGame = new JMenuItem("Restart Game");
        if(panel != null) {
            restartGame.setMnemonic('R');
            menu.add(restartGame);
            restartGame.addActionListener(this);
        }

        try {
            readIn = new Scanner(new File("gameRules.txt")).useDelimiter("\\Z").next();
        }catch (FileNotFoundException e) {
            System.out.println("rules.txt file not found");
        }
        final String rules = readIn;
        JMenuItem boardRules = new JMenuItem("Rules");
        boardRules.setMnemonic('s');
        menu.add(boardRules);
        boardRules.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, rules, "Rules", JOptionPane.PLAIN_MESSAGE);
                    }
                }
        );

        JMenuItem exit = new JMenuItem("Exit");
        exit.setMnemonic('x');
        menu.add(exit);
        exit.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
        );

        JMenuBar helpMenu = new JMenuBar();
        setJMenuBar(helpMenu);
        helpMenu.add(menu);
    }

    public void main_menu(){
        panel = null;

        top_menu();

        easy = new JButton("Easy Mode");
        hard = new JButton("Hard Mode");
        selection_screen = new JPanel();
        GridBagConstraints c = new GridBagConstraints();
        selection_screen.setPreferredSize(new Dimension(600, 900));
        selection_screen.setBackground(new Color(0, 100, 0));
        selection_screen.setLayout(new GridBagLayout());
        add(selection_screen);

        JLabel jlabel = new JLabel("Choose your difficulty");
        jlabel.setFont(new Font("Verdana",0,40));
        jlabel.setForeground(Color.WHITE);
        c.insets = new Insets(0, 0, 400, -360);
        selection_screen.add(jlabel, c);

        c.insets = new Insets(0, 20, 0, 20);

        easy.setToolTipText("When the game is in easy mode the player can flip through the draw deck as many times as they want.");
        selection_screen.add(easy, c);

        hard.setToolTipText("When the game is in hard mode the player can only flip through the draw deck only once.");
        selection_screen.add(hard, c);

        easy.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startGame(false);
                selection_screen.setVisible(false);
                remove(selection_screen);
                mode = false;
            }
        });

        hard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                startGame(true);
                selection_screen.setVisible(false);
                remove(selection_screen);
                mode = true;
            }
        });
    }
}