package com.ussz.jobify.data;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Meetup {

    private String name;
    private String description;
    private String meetupImageUrl;
    private int studentLimit;
}
