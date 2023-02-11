package dk.emilmadsen.barkalert.service;

import dk.emilmadsen.barkalert.record.Recording;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordingService {

    private static final int RECORD_DURATON = 20000;

    @Value("${file.path}")
    private String filePath;

    private final FFmpegService fFmpegService;
    private final DiscordService discordService;


    @Scheduled(fixedRate = RECORD_DURATON)
    public void run() {

        File wavFile = new File(buildAudioFilename(filePath));
        Recording rec = new Recording(wavFile);

        log.info("starting {}", wavFile.getName());
        // thread that waits for a specified amount of time before stopping.
        Thread thread = new Thread(() -> {
            sleep(RECORD_DURATON);

            log.info("thread done {}", wavFile.getName());
            rec.finish();

            File pngFile = fFmpegService.convert(wavFile);
            log.info("convert done {}", wavFile.getName());

            discordService.sendFileRequest(wavFile, pngFile, "Recorded!");

        });
        thread.start();
        rec.start();

        log.info("started {}", wavFile.getName());

    }

    private void sleep(int duration) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException ex) {
            log.error("interrupted.", ex);
        }
    }

    private String buildAudioFilename(String root) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        return root + time + ".wav";
    }

}
