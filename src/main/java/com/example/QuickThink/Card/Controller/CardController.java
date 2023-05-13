package com.example.QuickThink.Card.Controller;

import com.example.QuickThink.Card.Dto.CardEditRequestDto;
import com.example.QuickThink.Card.Dto.CardWriteRequestDto;
import com.example.QuickThink.Card.Dto.HashtagsDto;
import com.example.QuickThink.Card.Service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card")
public class CardController {

    CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping("/write")
    public ResponseEntity<HttpStatus> postCard(@RequestHeader("accessToken") String accessToken, @RequestBody CardWriteRequestDto cardWriteRequestDto) {
        cardService.postCard(accessToken, cardWriteRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 카드 여러개 가져오기
    @PostMapping("")
    public ResponseEntity<?> getCards(@RequestHeader("accessToken") String accessToken, @RequestParam String googleId, @RequestBody HashtagsDto hashtagsDto) {
        return new ResponseEntity<>(cardService.getCards(accessToken, googleId, hashtagsDto), HttpStatus.OK);
    }

    // 단일 카드 가져오기
    @GetMapping("/single")
    public ResponseEntity<?> getCard(@RequestHeader("accessToken") String accessToken, @RequestParam Long cardId) {
        return new ResponseEntity<>(cardService.getCard(accessToken, cardId), HttpStatus.OK);
    }

    // 단일 카드 복습 완료
    @PostMapping("/review")
    public ResponseEntity<HttpStatus> reviewCard(@RequestHeader("accessToken") String accessToken, @RequestParam Long cardId) {
        cardService.reviewCard(accessToken, cardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<HttpStatus> removeCard(@RequestHeader("accessToken") String accessToken, @RequestParam Long cardId) {
        cardService.removeCard(accessToken, cardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<HttpStatus> editCard(@RequestHeader("accessToken") String accessToken, @RequestParam Long cardId, @RequestBody CardEditRequestDto cardEditRequestDto) {
        cardService.editCard(accessToken, cardId, cardEditRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
