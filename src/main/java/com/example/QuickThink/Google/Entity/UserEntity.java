package com.example.QuickThink.Google.Entity;

import com.example.QuickThink.Card.Entity.CardEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 유저 엔티티
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(nullable = false, length = 255)
    private String googleName;

    @Column(nullable = false, length = 255)
    private String googleId;

    @Column(nullable = false, length = 255)
    private String profilePicture;

    @Column(columnDefinition = "")
    private String profileText;

    @ElementCollection
    @MapKeyColumn(name="hashTag")
    @Column(name="hashTagCount")
    @CollectionTable(name="hashTags", joinColumns=@JoinColumn(name="id"))
    @Builder.Default
    private Map<String, Long> hashTags = new HashMap<>();

    @Column(nullable = false, length = 255)
    private String accessToken;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<CardEntity> stones = new ArrayList<>();

    public void addHashTag(String hashtag) {
        if(hashTags.containsKey(hashtag)) {
            hashTags.put(hashtag, hashTags.get(hashtag) + 1);
        } else {
            hashTags.put(hashtag, 1L);
        }
    }

    public void removeHashTag(String hashtag) {
        if(hashTags.containsKey(hashtag)) {
            Long count = hashTags.get(hashtag);
            if(count > 0) {
                if(hashTags.get(hashtag) - 1 == 0) {
                    hashTags.remove(hashtag);
                } else {
                    hashTags.put(hashtag, hashTags.get(hashtag) - 1);
                }
            }
        }
    }
}
