package dk.emilmadsen.barkalert.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class RecordingService {

    // path of the wav file
    File wavFile = new File("recording.wav");

    TargetDataLine line;

    public void start() throws IOException {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                log.error("line not supported");
                throw new IllegalArgumentException("line not supported");
            }

            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            log.info("capturing...");
            AudioInputStream ais = new AudioInputStream(line);

            log.info("recording...");
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, wavFile);

        } catch (LineUnavailableException | IOException e) {
            log.error("recording failed: {} - msg: {}", e.getClass(), e.getMessage(), e);
        }
    }

    public void finish() {
        line.stop();
        line.close();
        log.info("finished");
    }


    private AudioFormat getAudioFormat() {
        float sampleRate = 64000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }

}
