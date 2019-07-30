package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Organization {

    public final static String FIELD_NAME = "name";

    private String organizationName;
    private String organizationImage;
    private String organizationBio;
    private int followers;

}
