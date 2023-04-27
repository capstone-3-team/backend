package com.example.QuickThink.Card.Controller;

import com.example.QuickThink.Card.Dto.CardDtoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CardController {
    @PostMapping("/api/write")
    public ResponseEntity<HttpStatus> writeCard(@RequestBody CardDtoRequest card) {
        System.out.println(card);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
