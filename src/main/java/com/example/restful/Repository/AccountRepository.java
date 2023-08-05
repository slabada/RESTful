package com.example.restful.Repository;

import com.example.restful.Models.AccountsModels;
import com.example.restful.Response.AccountResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AccountRepository extends JpaRepository<AccountsModels, Long> {
    AccountsModels findByEmail(String username);

    AccountsModels findById(int AccountId);

    List<AccountResponse> findByFirstNameOrLastNameOrEmail(String firstName, String lastName, String email, PageRequest pageable);
}
