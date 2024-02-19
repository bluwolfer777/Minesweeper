package Minesweeper;
import Minesweeper.Exeptions.BombNumberExeption;
import java.util.Random;
/**
 * @author Leon Rosamilia
 * @version 22.01.24
 */
public class MatrixControl {
    private final int[][] matrice;
    private final int bombs;
    private final Random random = new Random();
    public MatrixControl(int h, int w, int bombs) throws BombNumberExeption {
        if (bombs > (h*w)/2) {
            throw new BombNumberExeption("Max number of bombs is a half of the total cells");
        } else if (bombs < 1) {
            throw new BombNumberExeption("Must have at least one bomb");
        } else {
            matrice = new int[w][h];
            this.bombs = bombs;
        }
    }


    /**
     * @since 10.12.23
     * @deprecated deprecated because it's useless
     * the method prints the matrix in the terminal
     */
    public void print() {
        StringBuilder stringa;
        for (int[] ints : matrice) {
            stringa = new StringBuilder("   ");
            for (int anInt : ints) {
                stringa.append(anInt).append("   ");
            }
            System.out.println(stringa);
        }
    }

    /**
     * @since 10.12.23
     * is used to populate the matrix with the required bombs and numbers
     */
    public void populate() {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] = 0;
            }
        }
        for (int i = 0; i < bombs; i++) {
            int randomX = random.nextInt(matrice.length);
            int randomY = random.nextInt(matrice[0].length);
            if (matrice[randomX][randomY] != -1) {
                matrice[randomX][randomY] = -1;
            } else {
                i--;
            }
        }
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[0].length; j++) {
                matrice[i][j] = check(i,j);
            }
        }
    }

    /**
     * @param posX is the x position to check
     * @param posY is the y position to check
     * @return the numbers of bombs against the cell
     */
    private int check(int posX, int posY) {
        int bombCount = 0;
        if (matrice[posX][posY] != -1) {
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    try {
                        if (matrice[posX+i][posY+j] == -1) {
                            bombCount++;
                        }
                    } catch (ArrayIndexOutOfBoundsException ai) {
                        continue;
                    }
                }
            }
            return bombCount;
        }
        else {
            return -1;
        }
    }


    /**
     * @return returns the matrix
     */
    public int[][] getMatrix() {
        return matrice;
    }
}


