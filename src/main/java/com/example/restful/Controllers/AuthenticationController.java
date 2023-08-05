package com.example.restful.Controllers;

import com.example.restful.Models.AccountsModels;
import com.example.restful.Response.AccountResponse;
import com.example.restful.Service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class AuthenticationController {

    protected final AccountService accountService;

    AuthenticationController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<AccountResponse> registrationAccount(@Valid @ModelAttribute AccountsModels accounts,
                                                               BindingResult bindingResult,
                                                               Authentication authentication){

        if(accountService.CheckEmailDb(accounts)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        if(authentication != null && authentication.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (!accountService.registration(accounts)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AccountResponse accountResponse = new AccountResponse(
                accounts.getId(),
                accounts.getFirstName(),
                accounts.getLastName(),
                accounts.getEmail()
        );

        return new ResponseEntity(accountResponse, HttpStatus.CREATED);
    }
}
