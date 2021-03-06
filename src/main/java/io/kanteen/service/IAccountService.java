package io.kanteen.service;

import io.kanteen.dto.AccountDto;
import io.kanteen.persistance.entity.Account;

import java.util.List;

public interface IAccountService {
    AccountDto saveAccount(AccountDto accountDto);
    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(long id);
    void deleteAccount(long id);
}
