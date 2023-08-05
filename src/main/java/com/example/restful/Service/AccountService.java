package com.example.restful.Service;

import com.example.restful.Models.AccountsModels;
import com.example.restful.Repository.AccountRepository;
import com.example.restful.Response.AccountResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class AccountService implements UserDetailsService {

    protected final AccountRepository accountRepository;

    protected final PasswordEncoder passwordEncoder;

    AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder){
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AccountsModels account = accountRepository.findByEmail(username);
        return account;
    }

    public AccountsModels getAccountId(int AccountId){
        AccountsModels account = accountRepository.findById(AccountId);
        return account;
    }

    public boolean CheckEmailDb(AccountsModels account){

        AccountsModels CheckEmail = accountRepository.findByEmail(account.getEmail());

        if(CheckEmail != null){
            return true;
        }

        return false;
    }

    public boolean registration(AccountsModels account){

        AccountsModels AccountDb = accountRepository.findByEmail(account.getEmail());

        if(AccountDb != null){
            return false;
        }

        account.setFirstName(account.getFirstName());
        account.setLastName(account.getLastName());
        account.setEmail(account.getEmail());
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        accountRepository.save(account);

        return true;
    }

    public AccountResponse updateDataAccount(Integer AccountID,
                                             AccountsModels account,
                                             AccountsModels AuthAccount){

        AccountsModels AccountDb = accountRepository.findById(AccountID);

        if(AccountDb == null || !AccountID.equals(AuthAccount.getId())){
            return null;
        }

        AccountDb.setFirstName(account.getFirstName());
        AccountDb.setLastName(account.getLastName());
        AccountDb.setEmail(account.getEmail());

        accountRepository.save(AccountDb);

        AccountResponse response = new AccountResponse(
                AccountDb.getId(),
                AccountDb.getFirstName(),
                AccountDb.getLastName(),
                AccountDb.getEmail()
        );

        return response;
    }

    public boolean Delete(Integer AccountId,
                          AccountsModels AuthAccount){

        AccountsModels account = accountRepository.findById(AccountId);

        if(account == null || !AccountId.equals(AuthAccount.getId())){
            return false;
        }

        accountRepository.deleteById(AccountId.longValue());

        return true;
    }

    public List<AccountResponse> search(@ModelAttribute AccountsModels account,
                                        int from,
                                        int size){

        PageRequest pageable = PageRequest.of(from, size);

        List<AccountResponse> accounts = accountRepository.findByFirstNameOrLastNameOrEmail(
                account.getFirstName(),
                account.getLastName(),
                account.getEmail(),
                pageable
        );

        return accounts;
    }
}
