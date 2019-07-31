package com.ussz.jobify.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meetup {

    public final static String FIELD_NAME = "name";
    public final static String FIELD_DESCRIPTION = "description";
    public final static String FIELD_STUDENT_LIMIT = "studentLimit";

    private String name;
    private String description;
    private String meetupImageUrl;
    private int studentLimit;

}
