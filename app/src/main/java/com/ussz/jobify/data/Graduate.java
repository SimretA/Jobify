package com.ussz.jobify.data;

import com.google.firebase.firestore.DocumentReference;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Graduate {

    private String id;
    private String gender;
    private String name;
    private String department;
    private int graduationYear;
    private String profileImage;
    private DocumentReference universityRef;
    private String university;
    private String phoneNumber;
    private String email;
    private List<DocumentReference> following;


}
