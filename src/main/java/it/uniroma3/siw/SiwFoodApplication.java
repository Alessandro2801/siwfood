package it.uniroma3.siw;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SiwFoodApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SiwFoodApplication.class, args);
		
		/* String imagePath = "src/main/resources/static/images/immagine8.jpg";
	        byte[] imageBytes = convertImageToBytes(imagePath);
	        System.out.println(imageBytes);*/
		
	  /*     // Chiamata al metodo per criptare la password
        String rawPassword = "ale";
        String encodedPassword = encryptPassword(rawPassword);
        System.out.println("Encoded password: " + encodedPassword);*/
    }

    /*public static String encryptPassword(String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password);
	}*/
	
	
	/*public static byte[] convertImageToBytes(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }*/

}
