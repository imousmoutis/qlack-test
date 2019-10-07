package com.eurodyn.qlack.test.cmd.dto;

import static org.springframework.data.elasticsearch.annotations.FieldType.Integer;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @author European Dynamics
 */
@Getter
@Setter
@Document(indexName = "employee_", type = "employee_")
public class EmployeeDTO implements Serializable {

  @Field(type = Text)
  private String id;
  @Field(type = Text)
  private String firstName;
  @Field(type = Text)
  private String lastName;
  @Field(type = Integer)
  private int age;

  @Override
  public String toString() {
    return "First Name: " + firstName + " Last Name: " + lastName + " Age: " + age;
  }
}
