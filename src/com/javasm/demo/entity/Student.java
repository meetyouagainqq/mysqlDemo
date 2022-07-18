package com.javasm.demo.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data

public class Student implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private String address;
}
