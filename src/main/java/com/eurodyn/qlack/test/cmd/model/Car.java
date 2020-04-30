package com.eurodyn.qlack.test.cmd.dto;

import static org.springframework.data.elasticsearch.annotations.FieldType.Text;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Getter
@Setter
@Document(indexName = "car_annot", type = "car_annot")
public class Car {
  @Field(type = Text)
  private String make;
  @Field(type = Text)
  private String model;

}
