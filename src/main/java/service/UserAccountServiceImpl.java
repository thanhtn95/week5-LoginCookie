package service;

import model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import repository.UserAccountRepository;

public class UserAccountServiceImpl implements UserAccountService {
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Override
    public Iterable<UserAccount> findAll() {
        return userAccountRepository.findAll();
    }
}
