package Minesweeper.Audio;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
* @author unknown
 * @author Leon Rosamilia
 * @version 15.02.24
 */
public class Sound {
    public static synchronized void play(final String fileName)
    {
        // Note: use .wav files
        new Thread(() -> {
            try {
                String absolutePath = new File(fileName).getAbsolutePath();
                System.out.println(absolutePath);
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(absolutePath));
                System.out.println(inputStream);
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
            }
        }).start();

    }
}