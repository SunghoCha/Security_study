package com.sh.oauth2.model.users;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String registrationId;
    private String username;
    private String password;
    private String provider;
    private String email;
    @Column(name = "social_id", unique = true, nullable = false)
    private String socialId;

    @ElementCollection
    @CollectionTable(name = "user_authorities", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "authorities")
    private List<String> authorities;

    public List<GrantedAuthority> getGrantedAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList()); // 권한 관련이니 수정불가가 좋을수도..
    }

    @Builder
    public User(String registrationId, String username, String password, String provider,
                String email, String socialId, List<String> authorities) {

        this.registrationId = registrationId;
        this.username = username;
        this.password = password;
        this.provider = provider;
        this.email = email;
        this.socialId = socialId;
        this.authorities = authorities;
    }
}
