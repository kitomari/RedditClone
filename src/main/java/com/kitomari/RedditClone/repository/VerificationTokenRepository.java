package com.kitomari.RedditClone.repository;

import com.kitomari.RedditClone.models.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
