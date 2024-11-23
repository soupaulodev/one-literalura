package br.com.alura.literalura;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.literalura.CommandLine.CommandLine;

@SpringBootApplication
public class LiverAluraApplication implements CommandLineRunner {
	private CommandLine cmdLine;

	public LiverAluraApplication(CommandLine cmdLine) {
		this.cmdLine = cmdLine;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiverAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cmdLine.showMenu();
	}
}
