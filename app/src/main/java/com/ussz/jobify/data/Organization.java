package com.ussz.jobify.data;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization implements Serializable {

    public final static String FIELD_NAME = "name";

    private String id;
    private String organizationName;
    private String organizationImage;
    private String organizationBio;

    private int followers;

    private String address;

    private boolean following;


    private List<DocumentReference> jobs;
    private List<DocumentReference> meetups;

}
