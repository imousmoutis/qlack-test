package com.eurodyn.qlack.test.cmd.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author European Dynamics
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    @Column(nullable = false, length = 36)
    private String id;

    @Column(name="first_name", nullable = false)
    private String firstName;

    @Column(name="last_name", nullable = false)
    private String lastName;

    @Column(nullable = false)
    private int age;
}
