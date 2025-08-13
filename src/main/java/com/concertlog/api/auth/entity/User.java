package com.concertlog.api.auth.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 50)
    @Column(name = "username", length = 50)
    private String username;

    @Size(max = 50)
    @Column(name = "first_name", length = 50)
    private String firstName;

    @Size(max = 50)
    @Column(name = "last_name", length = 50)
    private String lastName;

    @Size(max = 500)
    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @NotNull
    @ColumnDefault("false")
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified = false;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = false;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<EmailVerificationToken> emailVerificationTokens = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<PasswordResetToken> passwordResetTokens = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserOauthAccount> userOauthAccounts = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserPassword> userPasswords = new LinkedHashSet<>();

    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<UserSession> userSessions = new LinkedHashSet<>();
}