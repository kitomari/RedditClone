package com.kitomari.RedditClone.services;

import com.kitomari.RedditClone.dto.RegisterRequest;
import com.kitomari.RedditClone.models.NotificationEmail;
import com.kitomari.RedditClone.models.User;
import com.kitomari.RedditClone.models.VerificationToken;
import com.kitomari.RedditClone.repository.UserRepository;
import com.kitomari.RedditClone.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;

    @Transactional
    public void signup(RegisterRequest registerRequest){
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);

        userRepository.save(user);

        //Method to generate verification token
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(), "Welcome to Reddit, "+
                "Please click below url to activate your account: "+
                "http://localhost:8080/api/auth/accountVerification/"+token));
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}