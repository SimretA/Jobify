package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Organization {
    private String organizationName;
    private String organizationImage;
    private String organizationBio;
    private int followers;

}
