package com.eurodyn.qlack.test.cmd.model;

import com.eurodyn.qlack.fuse.aaa.model.AAAModel;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author European Dynamics
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee extends AAAModel {

  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid2")
  @Column(nullable = false, length = 36)
  private String id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(nullable = false)
  @Min(value = 60)
  private int age;

  @Column(nullable = false)
  private int weight;
}
