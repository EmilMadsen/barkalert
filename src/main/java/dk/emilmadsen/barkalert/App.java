package dk.emilmadsen.barkalert;

import dk.emilmadsen.barkalert.service.RecordingService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

		final RecordingService recorder = new RecordingService();

		// creates a new thread that waits for a specified of time before stopping
		Thread thread = new Thread(() -> {
			try {
				Thread.sleep(30000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
			recorder.finish();
		});

		thread.start();

		// start recording
		try {
			recorder.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
