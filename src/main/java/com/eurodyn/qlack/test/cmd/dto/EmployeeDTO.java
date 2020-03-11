package com.eurodyn.qlack.test.cmd.dto;

import static org.springframework.data.elasticsearch.annotations.FieldType.Integer;
import static org.springframework.data.elasticsearch.annotations.FieldType.Nested;
import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import java.io.Serializable;

import com.eurodyn.qlack.fuse.aaa.exception.InvalidGroupHierarchyException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author European Dynamics
 */
@Getter
@Setter
@Document(indexName = "employee_annot", type = "employee_annot")
public class EmployeeDTO implements Serializable {

  @Field(type = Text)
  private String id;
  @Field(type = Text)
  private String firstName;
  @Field(type = Text)
  private String lastName;
  @Field(type = Integer)
  private int age;
  @Field(type = Integer)
  private int weight;
  @Field(type = Nested)
  private Car car;

  @Override
  public String toString() {
    return "First Name: " + firstName + " Last Name: " + lastName + " Age: " + age;
  }
}
