package puc.pi4.servidorpi4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class Servidorpi4Application {

	public static void main(String[] args) {
		SpringApplication.run(Servidorpi4Application.class, args);
	}


}

