package com.eurodyn.qlack.test.cmd.repository;

import com.eurodyn.qlack.test.cmd.model.Employee;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * @author European Dynamics
 */
@Repository
public interface EmployeeRepository<T extends Employee, I extends Serializable> extends JpaRepository<T, I>, QuerydslPredicateExecutor<T> {
    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);
    void deleteByFirstNameAndLastName(String firstName, String lastName);
}

