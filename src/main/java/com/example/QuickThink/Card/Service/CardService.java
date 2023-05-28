package com.example.QuickThink.Card.Service;

import com.example.QuickThink.Card.Dto.*;
import com.example.QuickThink.Card.Entity.CardEntity;
import com.example.QuickThink.Card.Repository.CardRepository;
import com.example.QuickThink.Google.Entity.UserEntity;
import com.example.QuickThink.Google.Exception.InvalidAuthorization;
import com.example.QuickThink.Google.Exception.NotFoundException;
import com.example.QuickThink.Google.Repository.UserRepository;
import com.example.QuickThink.Google.Service.LoginService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CardService {
    CardRepository cardRepository;
    UserRepository userRepository;
    LoginService loginService;

    public CardService(CardRepository cardRepository, UserRepository userRepository, LoginService loginService) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.loginService = loginService;
    }

    @Transactional
    public void postCard(String accessToken, CardWriteRequestDto cardWriteRequestDto) {
        UserEntity tokenUser = loginService.getUserByToken(accessToken);

        CardEntity newCard = CardEntity
                .builder()
                .title(cardWriteRequestDto.getTitle())
                .content(cardWriteRequestDto.getContent())
                .hashTags(new HashSet<>(cardWriteRequestDto.getHashTags()))
                .writtenDate(cardWriteRequestDto.getWrittenDate())
                .latestReviewDate(cardWriteRequestDto.getLatestReviewDate())
                .reviewCount(cardWriteRequestDto.getReviewCount())
                .user(tokenUser)
                .build();

        List<String> hashTags = cardWriteRequestDto.getHashTags();
        for(int i = 0;i < hashTags.size();i++) {
            tokenUser.addHashTag(hashTags.get(i));
        }

        cardRepository.save(newCard);
    }

    @Transactional
    public void editCard(String accessToken, Long cardId, CardEditRequestDto cardEditRequestDto) {
        UserEntity tokenUser = loginService.getUserByToken(accessToken);
        CardEntity card = cardRepository.findById(cardId).orElseThrow(NotFoundException::new);
        if(!card.getUser().getGoogleId().equals(tokenUser.getGoogleId())) {
            throw new InvalidAuthorization();
        }

        for(String hash : card.getHashTags()) {
            tokenUser.removeHashTag(hash);
        }

        card.setTitle(cardEditRequestDto.getTitle());
        card.setContent(cardEditRequestDto.getContent());
        card.setHashTags(new HashSet<>(cardEditRequestDto.getHashTags()));
        card.setWrittenDate(cardEditRequestDto.getWrittenDate());
        card.setLatestReviewDate(cardEditRequestDto.getWrittenDate());

        for(String hash : cardEditRequestDto.getHashTags()) {
            tokenUser.addHashTag(hash);
        }

        cardRepository.save(card);
    }

    public Object getCards(String accessToken, String googleId, HashtagsDto hashtagsDto) {
        UserEntity tokenUser = loginService.getUserByToken(accessToken);
        UserEntity user = loginService.getUserByGoogleId(googleId);
        List<CardEntity> cards = cardRepository.findAllByUser(user);
        List<String> hashTags = hashtagsDto.getHashTags();

        if(tokenUser.getGoogleId().equals(googleId)) {
            MyCardListResponseDto cardResponse = new MyCardListResponseDto();
            cardResponse.setSize((long) cards.size());
            cardResponse.setIsYours(true);

            for (CardEntity card : cards) {

                int f = 0;

                if(hashTags.size() != 0) {
                    for(String hashTag: hashTags) {
                        if(!card.getHashTags().contains(hashTag)) {
                            f = 1;
                            break;
                        }
                    }
                }

                if(f == 1) continue;

                cardResponse.addCards(
                        MyCardResponseDto
                                .builder()
                                .title(card.getTitle())
                                .id(card.getId())
                                .isYours(true)
                                .content(card.getContent())
                                .hashTags(card.getHashTags())
                                .writtenDate(card.getWrittenDate())
                                .latestReviewDate(card.getLatestReviewDate())
                                .reviewCount(card.getReviewCount())
                                .build()
                );
            }

            return cardResponse;
        } else {
            OtherCardListResponseDto cardResponse = new OtherCardListResponseDto();
            cardResponse.setSize((long) cards.size());
            cardResponse.setIsYours(false);

            for (CardEntity card : cards) {

                int f = 0;

                if(hashTags.size() != 0) {
                    for(String hashTag: hashTags) {
                        if(!card.getHashTags().contains(hashTag)) {
                            f = 1;
                            break;
                        }
                    }
                }

                if(f == 1) continue;

                cardResponse.addCards(
                        OtherCardResponseDto
                                .builder()
                                .title(card.getTitle())
                                .id(card.getId())
                                .isYours(false)
                                .content(card.getContent())
                                .hashTags(card.getHashTags())
                                .writtenDate(card.getWrittenDate())
                                .googleId(card.getUser().getGoogleId())
                                .build()
                );
            }

            return cardResponse;
        }
    }

    public Object getCard(String accessToken, Long cardId) {
        loginService.getUserByToken(accessToken);
        Optional<CardEntity> card = cardRepository.findById(cardId);
        if(card.isPresent()) {
            CardEntity cardData = card.get();
            if(cardData.getUser().getAccessToken().equals(accessToken)){
                return MyCardResponseDto
                        .builder()
                        .title(cardData.getTitle())
                        .isYours(true)
                        .content(cardData.getContent())
                        .hashTags(cardData.getHashTags())
                        .writtenDate(cardData.getWrittenDate())
                        .latestReviewDate(cardData.getLatestReviewDate())
                        .reviewCount(cardData.getReviewCount())
                        .build();
            } else {
                return OtherCardResponseDto
                        .builder()
                        .title(cardData.getTitle())
                        .isYours(false)
                        .content(cardData.getContent())
                        .hashTags(cardData.getHashTags())
                        .writtenDate(cardData.getWrittenDate())
                        .googleId(cardData.getUser().getGoogleId())
                        .build();
            }
        } else {
            throw new NotFoundException();
        }
    }

    public void reviewCard(String accessToken, Long cardId) {
        UserEntity tokenUser = loginService.getUserByToken(accessToken);
        CardEntity card = cardRepository.findById(cardId).orElseThrow(NotFoundException::new);
        if(!card.getUser().getGoogleId().equals(tokenUser.getGoogleId())) {
            throw new InvalidAuthorization();
        }
        card.setLatestReviewDate(LocalDateTime.now());
        card.setReviewCount(card.getReviewCount() + 1);
        cardRepository.save(card);
    }

    public void removeCard(String accessToken, Long cardId) {
        UserEntity tokenUser = loginService.getUserByToken(accessToken);
        CardEntity card = cardRepository.findById(cardId).orElseThrow(NotFoundException::new);
        if(!card.getUser().getGoogleId().equals(tokenUser.getGoogleId())) {
            throw new InvalidAuthorization();
        }

        for(String hashTag : card.getHashTags()) {
            tokenUser.removeHashTag(hashTag);
        }

        userRepository.save(tokenUser);
        cardRepository.deleteById(cardId);
    }
}
