package com.data.loaders.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee {


    @JsonProperty("id")
    private Integer id;
    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("position")
    private String position;
    @JsonProperty("salary")
    private int salary;
    @JsonProperty("age")
    private int age;
    @Column(name = "organization_id")
    String organizationId;

}
