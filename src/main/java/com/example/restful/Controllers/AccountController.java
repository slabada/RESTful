package com.example.restful.Controllers;

import com.example.restful.Models.AccountsModels;
import com.example.restful.Response.AccountResponse;
import com.example.restful.Service.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
public class AccountController {

    protected final AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/{AccountId}")
    public ResponseEntity<AccountResponse> GetInfoAccount(@PathVariable("AccountId") Integer AccountId){

        if(AccountId == null || AccountId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        AccountsModels account = accountService.getAccountId(AccountId);

        if(account == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AccountResponse accountResponse = new AccountResponse(account.id, account.firstName,
                account.lastName, account.email);

        return ResponseEntity.status(HttpStatus.OK).body(accountResponse);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AccountResponse>> SearchAccount(@ModelAttribute AccountsModels account,
                                                               @RequestParam(defaultValue = "0") int from,
                                                               @RequestParam(defaultValue = "10") int size){

        if(from < 0 || size <= 0){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }

        List<AccountResponse> responses = accountService.search(account, from, size);

        return ResponseEntity.status(HttpStatus.OK).body(responses);
    }

    @PutMapping("/{AccountId}")
    public ResponseEntity<AccountResponse> UpdateDataAccount(@PathVariable("AccountId") Integer AccountId,
                                                             @AuthenticationPrincipal AccountsModels AuthAccount,
                                                             @ModelAttribute AccountsModels account){

        if(accountService.CheckEmailDb(account)){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        AccountResponse response = accountService.updateDataAccount(AccountId, account, AuthAccount);

        if(AccountId == null || AccountId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(response == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{AccountId}")
    public ResponseEntity<HttpStatus> DeleteAccount(@PathVariable Integer AccountId,
                                        @AuthenticationPrincipal AccountsModels AuthAccount){

        if(AccountId == null || AccountId <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if(!accountService.Delete(AccountId, AuthAccount)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
