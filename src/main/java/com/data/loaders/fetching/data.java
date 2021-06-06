package com.data.loaders.fetching;

import com.data.loaders.model.Employee;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class data {

    @JsonProperty("allEmployeeUsingFilter")
    List<Employee> employeeList;
}
