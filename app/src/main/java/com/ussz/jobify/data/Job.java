package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Job {

    private String title;
    private String description;

    private int StudentLimit;
}
