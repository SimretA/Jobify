package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    public final static String FIELD_NAME = "name";

    private String id;
    private String organizationName;
    private String organizationImage;
    private String organizationBio;
    private int followers;

}
