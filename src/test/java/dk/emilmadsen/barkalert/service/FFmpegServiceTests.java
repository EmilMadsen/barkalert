package dk.emilmadsen.barkalert.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@SpringBootTest
public class FFmpegServiceTests {

    @Autowired
    private FFmpegService fFmpegService;

    @Test
    public void convert_wav_to_png() throws IOException {

        // create copy in temp folder.
        Path tmp = Files.createTempDirectory("tmp");
        File input = ResourceUtils.getFile("classpath:recording.wav");
        Assertions.assertTrue(input.exists());
        File copy = new File(tmp.toFile().getAbsolutePath() + File.separator + input.getName());
        Files.copy(input.toPath(), copy.toPath(), StandardCopyOption.REPLACE_EXISTING);
        Assertions.assertTrue(copy.exists());

        // wav to png.
        File result = fFmpegService.convert(copy);

        Assertions.assertTrue(result.exists());
        Assertions.assertEquals(result.getName(), "recording.png");

    }
}
