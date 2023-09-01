import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Som {

    public Som() {
    }

    private Clip clip;

    private Thread thread;
    private final Object loopLock = new Object();

    public Som(String audioFile) {
        AudioInputStream audioStream = null;
        URL audioURL = this.getClass().getClassLoader().getResource(audioFile);

        // Obtain audio input stream from the audio file and load the information
        // into main memory using the URL path retrieved from above.
        try {
            audioStream = AudioSystem.getAudioInputStream(audioURL);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            // Retrieve the object of class Clip from the Data Line.
            this.clip = AudioSystem.getClip();

            // Load the audio input stream into memory for future play-back.
            this.clip.open(audioStream);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    long clipTimePosition;

    public void stop() {
        synchronized (loopLock) {
            clipTimePosition = clip.getMicrosecondPosition();
            loopLock.notifyAll();
        }
    }

    public void start() {
        Runnable r = new Runnable() {
            public void run() {
                // clip.setFramePosition(0);
                clip.setMicrosecondPosition(clipTimePosition);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                synchronized (loopLock) {
                    try {
                        loopLock.wait();
                    } catch (InterruptedException ex) {
                    }
                }
                clip.stop();
            }
        };
        thread = new Thread(r);
        thread.start();
    }

}
