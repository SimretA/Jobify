package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Graduate {

    public Graduate(){

    }

    private String id;
    private String gender;
    private String name;
    private String department;
    private int graduationYear;
    private String profileImage;
    private University university;
    private String phoneNumber;
    private String email;


}
