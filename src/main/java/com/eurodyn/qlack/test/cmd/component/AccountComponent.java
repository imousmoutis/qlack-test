package com.eurodyn.qlack.test.cmd.component;

import com.eurodyn.qlack.test.cmd.model.Account;
import com.eurodyn.qlack.test.cmd.repository.AccountRepository;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountComponent implements Serializable {

  @Autowired
  private AccountRepository accountRepository;

  public void save(Account account) {

    accountRepository.save(account);
  }

}
