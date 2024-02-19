package Minesweeper;
/**
 * @author Leon Rosamilia
 * @version 15.02.24
 *
 * is the main class, is used to load the first window and switch to the seconds when needed
 */
public class Entry {
    public static void main(String[] args) {
        ConfigScreen cs = new ConfigScreen();
        while (cs.startGame) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        GameScreen gs = new GameScreen(cs.dimX.getValue() , cs.dimY.getValue() , cs.bombs.getValue(), cs.outName);
        cs.dispose();
    }
}