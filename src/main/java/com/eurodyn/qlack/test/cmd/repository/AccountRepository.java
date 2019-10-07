package com.eurodyn.qlack.test.cmd.repository;

import com.eurodyn.qlack.test.cmd.model.Account;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository<T extends Account, I extends Serializable> extends
    JpaRepository<T, I>, QuerydslPredicateExecutor<T> {

}

