package com.example.commons;
/*
 * chou created at 2019-03-28
 * @Description:
 *
 * */

import com.example.commons.domain.DaoBase;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestEntity extends DaoBase {
    private String name, gender;
    private int age;
}
