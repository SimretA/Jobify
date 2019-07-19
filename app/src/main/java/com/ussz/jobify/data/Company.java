package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Company {
    private String companyName;
    private String companyImage;
    private String companyBio;
    private int followers;

}
