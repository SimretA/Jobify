package com.ussz.jobify.data;



import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job implements Serializable {

    public final static String FIELD_TITLE = "title";
    public final static String FIELD_DESCRIPTION = "description";
    public final static String FIELD_STUDENT_LIMIT = "studentLimit";
    public final static String FIELD_DEPARTMENT = "department";


    private String title;
    private String description;
    private int studentLimit;
    private String department;
    private String postedByName;
    private String postedById;
    private String howToApply;
    private Timestamp deadline;


}
