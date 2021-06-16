package com.promotion.amongapi.repository;

import com.promotion.amongapi.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    int countByName(String name);
    int countByGeneration(int generation);
    int countByClazz(int clazz);
}
