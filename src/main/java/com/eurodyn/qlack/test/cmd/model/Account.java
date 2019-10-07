package com.eurodyn.qlack.test.cmd.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(nullable = false, length = 36)
  private String id;

  private Integer balance;

  @Enumerated(EnumType.ORDINAL)
  private AccountStatus status;

  public void deposit(int ammount) {
    balance += ammount;
  }

  public void withdraw(int ammount) {
    balance -= ammount;
  }
}
