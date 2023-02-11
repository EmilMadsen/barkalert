package dk.emilmadsen.barkalert.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bramp.ffmpeg.FFmpeg;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FFmpegService {

    private final FFmpeg ffmpeg;

    public File convert(File audioFile) {

        String outputName = getOutputName(audioFile.getAbsolutePath());

        // ffmpeg -i input -filter_complex "showwavespic=s=640x120" -frames:v 1 output.png
        // https://trac.ffmpeg.org/wiki/Waveform
        List<String> args = List.of(
                "-i", audioFile.getAbsolutePath(),              // input
                "-filter_complex", "showwavespic=s=1280x240",   // filter
                "-frames:v", "1",                               // frames
                outputName);                                    // output

        try {
            ffmpeg.run(args);
        } catch (IOException e) {
            log.error("ffmpeg failed: {}", e.getMessage(), e);
        }

        return new File(outputName);

    }

    private String getOutputName(String absolutePath) {
        String filename = absolutePath.substring(0, absolutePath.lastIndexOf(".")+1);
        return filename + "png";
    }
}
