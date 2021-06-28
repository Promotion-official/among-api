package com.promotion.amongapi.repository;

import com.promotion.amongapi.domain.entity.AuthorizeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorizeKeyRepository extends JpaRepository<AuthorizeKey, String> {
}
