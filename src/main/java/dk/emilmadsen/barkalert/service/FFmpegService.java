package dk.emilmadsen.barkalert.service;

import lombok.RequiredArgsConstructor;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FFmpegService {

    private final FFmpeg ffmpeg;

    public File convert(File audioFile) throws IOException {

        String outputName = getOutputName(audioFile.getAbsolutePath());

        // ffmpeg -i input -filter_complex "showwavespic=s=640x120" -frames:v 1 output.png
        List<String> args = new ArrayList<>();
        // input
        args.add("-i");
        args.add(audioFile.getAbsolutePath());
        // filter
        args.add("-filter_complex");
        args.add("showwavespic=s=1280x240");
        // frames
        args.add("-frames:v");
        args.add("1");
        // output
        args.add(outputName);
        ffmpeg.run(args);

        return new File(outputName);

    }

    private String getOutputName(String absolutePath) {
        String filename = absolutePath.substring(0, absolutePath.lastIndexOf(".")+1);
        return filename + "png";
    }
}
