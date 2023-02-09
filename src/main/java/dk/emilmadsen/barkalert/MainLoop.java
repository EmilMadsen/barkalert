package dk.emilmadsen.barkalert;

import dk.emilmadsen.barkalert.service.FFmpegService;
import dk.emilmadsen.barkalert.service.RecordingService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class MainLoop {

    private final RecordingService recordingService;
    private final FFmpegService fFmpegService;

    @Scheduled(cron = "0 * * * * *")
    public void run() throws IOException {

        recordingService.start();

    }

}
