package Minesweeper;
import Minesweeper.Audio.Sound;
import Minesweeper.Exeptions.BombNumberExeption;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.Position;

/**
 * @author Leon Rosamilia
 * @version 11.02.24
 * is the main class of the game
 */
public class GameScreen extends JFrame implements ItemListener, ActionListener {
    private final JLabel[][] gameView;
    private final int totalBombs;
    private int[][] matrix;
    private boolean death = false;
    private final ImageIcon imageNumber1 = new ImageIcon("Images/1.png");
    private final ImageIcon imageNumber2 = new ImageIcon("Images/2.png");
    private final ImageIcon imageNumber3 = new ImageIcon("Images/3.png");
    private final ImageIcon imageNumber4 = new ImageIcon("Images/4.png");
    private final ImageIcon imageNumber5 = new ImageIcon("Images/5.png");
    private final ImageIcon imageNumber6 = new ImageIcon("Images/6.png");
    private final ImageIcon imageNumber7 = new ImageIcon("Images/7.png");
    private final ImageIcon imageNumber8 = new ImageIcon("Images/8.png");
    private final ImageIcon imageBomb = new ImageIcon("Images/bomb.png");
    private final ImageIcon imageWrongBomb = new ImageIcon("Images/wrongBomb.png");
    private final ImageIcon imageExplodedBomb = new ImageIcon("Images/explodedBomb.png");
    private final ImageIcon imageFlag = new ImageIcon("Images/flag.png");
    private final ImageIcon imageEmpty = new ImageIcon("Images/empty.png");
    private final ImageIcon imageUnknown = new ImageIcon("Images/unknown.png");
    private final JLabel time = new JLabel("Time: 000");
    private final JLabel selectedBombsLabel = new JLabel("Selected bombs: 000");
    private final JLabel remainingBombs = new JLabel("Remaining bombs: 000");
    private final JLabel openedCellsLabel = new JLabel("Opened cells: 000");
    private int selectedBombs = 0;
    private int openedCells = 0;


    public void itemStateChanged(ItemEvent e){

    }
    public void actionPerformed(ActionEvent e){

    }

    /**
     * @param dimX is the width of the game grid
     * @param dimY is the height of the game grid
     * @param bombs is the num,ber of bombs in the game grid
     * @param name is the player name displayed
     *             this method generates the game window
     */
    GameScreen(int dimX, int dimY, int bombs, String name) {
        JLabel playerName = new JLabel("Player: " + name);
        gameView = new JLabel[dimX][dimY];
        totalBombs = bombs;
        setLayout(new GridLayout(dimY,dimX));
        for (int i = 0; i < gameView.length; i++) {
            for (int j = 0; j < gameView[0].length; j++) {
                gameView[i][j] = new JLabel("?");
                gameView[i][j].setIcon(imageUnknown);
            }
        }
        Image icon = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        this.setIconImage(icon);
        setLayout(null);
        setSize((dimX*16)+16,dimY*16+200);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
        updateView();
        playerName.setBounds(16,16,200,16);
        add(playerName);
        time.setBounds(16,32,100,16);
        add(time);
        remainingBombs.setBounds(16,48,200,16);
        add(remainingBombs);
        selectedBombsLabel.setBounds(16,64,200,16);
        add(selectedBombsLabel);
        openedCellsLabel.setBounds(16,80,200,16);
        add(openedCellsLabel);
        remainingBombs.setText("Remaining bombs: " + totalBombs);
        setVisible(true);
        addMouseClickListener();
        try {
            MatrixControl temporaryMatrix = new MatrixControl(dimY,dimX,bombs);
            temporaryMatrix.populate();
            matrix = temporaryMatrix.getMatrix();
        }
        catch (BombNumberExeption bn) {
            Thread.currentThread().interrupt();
            System.out.println(bn.getMessage());
        }
        Sound.play("Audio\\theme.wav");
        time();
    }

    /**
     * @since 20.01.24
     * this method is used to set the position of every cell in the game view
     */
    private void updateView() {
        for (int i = 0; i < gameView.length; i++) {
            for (int j = 0; j < gameView[0].length; j++) {
                gameView[i][j].setBounds((16*i),((16*j)+150),16,16);
                add(gameView[i][j]);
            }
        }
    }

    /**
     * @since 28.01.24
     * this method add the mouse click listener and set the control if the player is still playing or have lost
     */
    private void addMouseClickListener() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (!death) {
                    controlAction(me);
                }
            }
        });
    }

    /**
     * @since 28.01.24
     * @param me is the mouse event passed for control which cell is pressed and with which button
     * the method controls which cell is pressed and uncover the selected or put/remove a flag from it
     */
    private void controlAction(MouseEvent me) {
        double mouseX,mouseY,cellX,cellY;
        mouseX = me.getX();
        mouseY = me.getY();
        if (me.getButton() == 1) {
            if (mouseY > 182) {
                cellX = (mouseX-8)/16;
                cellY = (mouseY-182)/16;
                if (gameView[(int) cellX][(int) cellY].getText().equals("?")) {
                    if (matrix[(int) cellX][(int) cellY] == -1) {
                        deathScreen();
                    } else {
                        gameView[(int) cellX][(int) cellY].setText(String.valueOf(matrix[(int) cellX][(int) cellY]));
                        switch (matrix[(int) cellX][(int) cellY]) {
                            case 0 -> {
                                gameView[(int) cellX][(int) cellY].setIcon(imageEmpty);
                                //controlCellEmptyRecursive((int) cellX, (int) cellY);
                            }
                            case 1 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber1);
                            case 2 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber2);
                            case 3 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber3);
                            case 4 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber4);
                            case 5 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber5);
                            case 6 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber6);
                            case 7 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber7);
                            case 8 -> gameView[(int) cellX][(int) cellY].setIcon(imageNumber8);
                        }
                        openedCells++;
                        openedCellsLabel.setText("Opened cells: " + openedCells);
                        if (openedCells == (gameView.length*gameView[0].length)-totalBombs) {
                            JOptionPane.showMessageDialog(this, "YOU WON","WON", JOptionPane.INFORMATION_MESSAGE);
                            death = true;
                        }
                    }
                }
            }
        } else if (me.getButton() == 3) {
            if (mouseY > 182) {
                cellX = (mouseX-8)/16;
                cellY = (mouseY-182)/16;
                if (gameView[(int) cellX][(int) cellY].getText().equals("?")) {
                    gameView[(int) cellX][(int) cellY].setText("⚑");
                    gameView[(int) cellX][(int) cellY].setIcon(imageFlag);
                    selectedBombs++;
                    selectedBombsLabel.setText("Selected bombs: " + selectedBombs);
                    remainingBombs.setText("Remaining bombs: " + (totalBombs-selectedBombs));
                } else if (gameView[(int) cellX][(int) cellY].getText().equals("⚑")) {
                    gameView[(int) cellX][(int) cellY].setText("?");
                    gameView[(int) cellX][(int) cellY].setIcon(imageUnknown);
                    selectedBombs--;
                    selectedBombsLabel.setText("Selected bombs: " + selectedBombs);
                    remainingBombs.setText("Remaining bombs: " + (totalBombs-selectedBombs));
                }
            }
        }
    }

    /**
     * for now this is useless, it is for open the big 0 zone
     * @param cellX is the start x cell
     * @param cellY is the start y cell
     */
    private void controlCellEmptyRecursive(int cellX, int cellY) {
        boolean zero = true;
        Point[] cells;
        while (zero) {

        }

        /*while (zero) {
            zero = true;
            if (matrix[cellX-1][cellY-1] != -1) {
                gameView[cellX-1][cellY-1].setText("0");
                gameView[cellX-1][cellY-1].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX][cellY-1] != -1) {
                gameView[cellX][cellY-1].setText("0");
                gameView[cellX][cellY-1].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX+1][cellY-1] != -1) {
                gameView[cellX+1][cellY-1].setText("0");
                gameView[cellX+1][cellY-1].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX-1][cellY] != -1) {
                gameView[cellX-1][cellY].setText("0");
                gameView[cellX-1][cellY].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX+1][cellY] != -1) {
                gameView[cellX+1][cellY].setText("0");
                gameView[cellX+1][cellY].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX+1][cellY+1] != -1) {
                gameView[cellX+1][cellY+1].setText("0");
                gameView[cellX+1][cellY+1].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX-1][cellY+1] != -1) {
                gameView[cellX-1][cellY+1].setText("0");
                gameView[cellX-1][cellY+1].setIcon(imageEmpty);
                zero = false;
            }
            if (matrix[cellX][cellY+1] != -1) {
                gameView[cellX][cellY+1].setText(String.valueOf(matrix));
                gameView[cellX][cellY+1].setIcon(imageEmpty);
                zero = false;
            }
        }*/
    }


    /**
     * @since 28.01.24
     * the method triggers when the player clicks a bomb and lose
     */
    private void deathScreen() {
        System.out.println("You died");
        death = true;
        for (int i = 0; i < gameView.length; i++) {
            for (int j = 0; j < gameView[0].length; j++) {
                if (matrix[i][j] == -1) {
                    gameView[i][j].setText("\uD83D\uDCA3");
                    gameView[i][j].setIcon(imageExplodedBomb);
                } else if (matrix[i][j] != -1 && gameView[i][j].getText().equals("⚑")) {
                    gameView[i][j].setIcon(imageWrongBomb);
                }
            }
        }
        JOptionPane.showMessageDialog(this, "\uD83D\uDCA3 YOU DIED \uD83D\uDCA3    Points: " + openedCells,"DIED", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * @since 01.02.24
     * the method creates a new thread that controls the timer for the game
     */
    private void time() {
        new Thread(() -> {
            int seconds = 0;
            while (!death) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.out.println(ie.getMessage());
                }
                seconds++;
                time.setText(toNumberOut(seconds));
            }
        }).start();
    }

    /**
     * @param value is the number to convert
     * @return returns the number with at least 3 digits
     * @since 28.01.24
     */
    private String toNumberOut(int value) {
        if (value < 10) {
            return "Time: 00" + value;
        } else if (value < 100) {
            return "Time: 0" + value;
        } else {
            return "Time: " + value;
        }

    }
}

