package tetris;
 
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Sound implements Runnable {

    public Sound(int flag) {
        setSound(flag);
    }

    public void playSound() {

        try {
            soundFile = new File(getClass().getResource(strFilename).getPath());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        audioFormat = audioStream.getFormat();

        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        sourceLine.start();

        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
//        try {
//            audioStream.skip(nBytesRead);
//        } catch (IOException ex) {
//            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
//        }
        while (nBytesRead != -1) {
            try {

                nBytesRead = audioStream.read(abData, 0, abData.length);

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }

        sourceLine.drain();
        sourceLine.close();
    }

    public void setSound(int flag) {

        switch (flag) {
            case 1:    
                strFilename = "/material/sounds/TetrisCoverTheme.wav";
                break;
            case 2:
                strFilename = "/material/sounds/TetrisDubstepRemix.wav";
                break;
            case 3:
                strFilename = "/material/sounds/TetrisHouseRemix.wav";
                break;
            case 4:
                strFilename = "/material/sounds/TetrisMetaltheme.wav";
                break;
            case 5:
                strFilename = "/material/sounds/TetrisTechnoTrance.wav";
                break;
            case 6:
                strFilename = "/material/sounds/HitSoundEffect.wav";
                break;
            case 7:
                strFilename = "/material/sounds/LineCompletedSoundEffect.wav";
                break;
            case 8:
                strFilename = "/material/sounds/TurnSoundEffect.wav";
                break;
            default:
                strFilename = "/material/sounds/OriginalTetrisTheme.wav";
        }
    }

    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    private String strFilename;

    @Override
    public void run() {
        playSound();
    }

}
