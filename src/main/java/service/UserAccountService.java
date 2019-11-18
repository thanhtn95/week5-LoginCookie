package service;

import model.UserAccount;

public interface UserAccountService {
    Iterable<UserAccount> findAll();
}
