package it.uniroma3.siw.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Immagine;
import it.uniroma3.siw.repository.ImmagineRepository;


@Controller
public class ImmagineController {
    @Autowired
    ImmagineRepository imageRepository;

    @GetMapping("/display/image/{id}")
    public ResponseEntity<byte[]> displayItemImage(@PathVariable("id") Long id) {
        Immagine img = this.imageRepository.findById(id).get();
        byte[] image = img.getBytes();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }    
}
