package dk.emilmadsen.barkalert.config;

import net.bramp.ffmpeg.FFmpeg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Configuration
@EnableScheduling
public class Config {

    @Value("${ffmpeg.path}")
    private String ffmpegPath;

    @Bean
    public FFmpeg ffmpeg() throws IOException {
        return new FFmpeg(ffmpegPath);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
