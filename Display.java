/*
* the flow of this program is based on the state of the game.
* these states are notStarted---> player1---->player2---->endOfGame
* the state will switch between player1 and player2 so based on this state the program will define whose turn is
to play and at the end will select the winner
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class Display extends JFrame implements ActionListener, MouseListener {

    private JPanel topPanel, bottomPanel;
    private JButton topButton;        // the button to create a new game
    private JLabel playerLabel;       // to inform us who is playing
    private JLabel scoreLabel;        // to keep track of the players score
    private GamePlay[][] gridButtons; // to fill the display with the buttons
    private int rows, columns;         // the size of the grid
    private String state = "notStarted"; // defining sates to keep track of the game
    private int counter = 0;
    private int WINNING_NUMBER = 21;

    public Display(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.setSize(600, 600);

        // create the panels
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());


        bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(rows, columns));
        bottomPanel.setSize(500, 500);

        // create the component for each panel
        // top panel
        topButton = new JButton("New Game");
        playerLabel = new JLabel("keep the total score below 22 click on new game to play");
        scoreLabel = new JLabel("Score: -");
        topButton.addActionListener(this);


        topPanel.add(topButton);
        topPanel.add(playerLabel);
        topPanel.add(scoreLabel);

        //for the bottom panel:
        // create the buttons and add them to the grid

        gridButtons = new GamePlay[rows][columns];

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {
                gridButtons[x][y] = new GamePlay(x, y);
                gridButtons[x][y].setSize(20, 20);
                gridButtons[x][y].setColor();
                gridButtons[x][y].setText("-");
                gridButtons[x][y].setFont(new Font("Arial", Font.PLAIN, 30));
                gridButtons[x][y].addMouseListener(this);

                bottomPanel.add(gridButtons[x][y]);
            }
        }


        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.CENTER);        // needs to be center or will draw too small


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setTitle("Pontoon");


    }

    // this method will remove all added mouse listener So we can reAssign mouse listener when we hit new game
    public void removeAddedMouseListener() {

        for (int x = 0; x < columns; x++) {
            for (int y = 0; y < rows; y++) {

                gridButtons[x][y].removeMouseListener(this);
            }
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        /*       create a random number between 0 and 1
         * So if the number is equal 0 player 1 will start the game otherwise player 2 will start the game
         *
         * */

        Random randomPlayer = new Random();
        int rnPlyr = (randomPlayer.nextInt(2));
        if (rnPlyr == 0) {
            state = "player1";
        } else {
            state = "player2";
        }
        Object selected = e.getSource();
        if (selected.equals(topButton)) {
            counter = 0;
            playerLabel.setText(preSpacer + state + "'s turn" + spacer);
            scoreLabel.setText("score: -");
            Random ran = new Random();
            removeAddedMouseListener();

            for (int x = 0; x < columns; x++) {
                for (int y = 0; y < rows; y++) {
                    int rn = (ran.nextInt(5) + 1);  // to generate random numbers between 1 and 5
                    String s = Integer.toString(rn);    // to parse the integer in to string So we can pass it to set text
                    gridButtons[x][y].setColor();
                    gridButtons[x][y].setText(s);
                    gridButtons[x][y].setFont(new Font("Arial", Font.PLAIN, 17)); // to change the text format
                    gridButtons[x][y].setEnabled(true);
                    gridButtons[x][y].addMouseListener(this);
                }
            }
        }
    }

    public void checkTheWinner() {
        if (counter > WINNING_NUMBER) {
            playerLabel.setText(preSpacer + state + " wins!" + spacer);
            scoreLabel.setText("Well done!");
            for (int x = 0; x < columns; x++) {
                for (int y = 0; y < rows; y++) {
                    gridButtons[x][y].setEnabled(false);

                }
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent mevt) {


    }

    String preSpacer = "               ";
    String spacer = "                                          ";

    @Override
    public void mousePressed(MouseEvent event) {
        Object obj = event.getSource();
        JButton btn = null;
        String buttonText = "";
        if (state.equals("player1") && counter <= WINNING_NUMBER) {
            if (obj instanceof JButton)
                btn = (JButton) obj;

            if (btn != null) {
                buttonText = btn.getText();
                int result = Integer.parseInt(buttonText);
                counter = result + counter;
                scoreLabel.setText("score:" + Integer.toString(counter));
                playerLabel.setText(preSpacer + "player2's turn" + spacer);

            }

            GamePlay square = null;
            if (obj instanceof GamePlay) {
                square = (GamePlay) obj;
                square.switchColor(state);
                square.setEnabled(false);
                square.removeMouseListener(this);
                state = "player2";
                checkTheWinner();
            }

        } else if (state.equals("player2") && counter <= WINNING_NUMBER) {
            playerLabel.setText(preSpacer + "player1's turn" + spacer);
            if (obj instanceof JButton)
                btn = (JButton) obj;

            if (btn != null) {
                buttonText = btn.getText();
                int result = Integer.parseInt(buttonText);
                counter = result + counter;
                scoreLabel.setText("score:" + Integer.toString(counter));
                playerLabel.setText(preSpacer + "player1's turn" + spacer);
            }

            GamePlay square = null;
            if (obj instanceof GamePlay) {
                square = (GamePlay) obj;
                square.switchColor(state);
                square.setEnabled(false);         // disabling the button
                square.removeMouseListener(this); // remove mouse listener so, no action will occur when we hit the same butn twice
                state = "player1";
                checkTheWinner();
            }


        }


    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
