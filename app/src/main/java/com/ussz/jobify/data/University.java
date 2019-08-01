package com.ussz.jobify.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public
class University {

    public final static String FIELD_NAME = "name";
    public final static String FIELD_EMAIL = "email";

    private String id;
    private String name;
    private String email;

}
