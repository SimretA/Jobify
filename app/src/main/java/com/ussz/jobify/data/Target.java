package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Target {

    private String department;

    private String university;

    private String gender;

    private Integer graduationYear;

    private String[] languages;
}
