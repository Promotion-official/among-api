package com.promotion.amongapi.repository;

import com.promotion.amongapi.dto.AccountDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountDto, String> {
    AccountDto getAccountDtoByEmail(String email);
}
