package Minesweeper;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * @author Leon Rosamilia
 * @version 15.02.24
 * is the class that manages the configuration before play the game
 */
public class ConfigScreen extends JFrame implements ItemListener, ActionListener {
    public void itemStateChanged(ItemEvent e){

    }
    public void actionPerformed(ActionEvent e){

    }
    final JSlider dimX;
    final JSlider dimY;
    final JSlider bombs;
    private final JLabel width;
    private final JLabel height;
    private final JLabel bombNumber;
    private final JTextField name = new JTextField("John Doe");
    boolean startGame = true;
    String outName;
    ConfigScreen() {
        dimX = new JSlider();
        dimY = new JSlider();
        bombs = new JSlider();
        JButton start = new JButton("Start Game");
        width = new JLabel("Width - 20");
        height = new JLabel("Height - 20");
        bombNumber = new JLabel("Bombs - 20");
        JLabel title = new JLabel("Minesweeper");
        title.setFont(new Font("Comic Sans MS",Font.PLAIN,25));
        width.setBounds(20,50,400,40);
        dimX.setBounds(20,100,400,40);
        height.setBounds(20,150,400,40);
        dimY.setBounds(20,200,400,40);
        bombNumber.setBounds(20,250,400,40);
        bombs.setBounds(20,300,400,40);
        start.setBounds(20,400,120,25);
        title.setBounds(20,20,200,25);
        name.setBounds(200,20,200,25);
        add(name);
        dimX.setValue(20);
        dimY.setValue(20);
        add(dimX);
        add(dimY);
        add(bombs);
        add(start);
        add(width);
        add(height);
        add(bombNumber);
        add(title);
        dimX.setMaximum(50);
        dimY.setMaximum(50);
        bombs.setMaximum(200);
        dimX.setMinimum(10);
        dimY.setMinimum(10);
        bombs.setMinimum(10);
        //title.setMinimumSize();
        start.addActionListener(e -> StartGame());
        dimX.addChangeListener(e -> updateNumber());
        dimY.addChangeListener(e -> updateNumber());
        bombs.addChangeListener(e -> updateNumber());
        Image icon = Toolkit.getDefaultToolkit().getImage("Images/icon.png");
        this.setIconImage(icon);
        setLayout(null);
        setSize(450,500);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Minesweeper");
    }


    /**
     * @since 25.01.24
     * changes some variables to start the game
     */
    private void StartGame() {
        outName = name.getText();
        startGame = false;
    }

    /**
     * @since 25.01.24
     * updates the numbers in the view
     */
    private void updateNumber() {
        width.setText("Width - " + dimX.getValue());
        height.setText("Height - " + dimY.getValue());
        bombNumber.setText("Bombs - " + bombs.getValue());
        bombs.setMaximum(Math.round((float) ((dimX.getValue()*dimY.getValue())/2)));
    }
}





