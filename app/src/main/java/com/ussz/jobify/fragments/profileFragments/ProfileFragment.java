package com.ussz.jobify.fragments.profileFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Graduate;
import com.ussz.jobify.network.EditRemote;
import com.ussz.jobify.network.ProfileImageRemote;
import com.ussz.jobify.utilities.IEditResult;
import com.ussz.jobify.utilities.IUploadImageResult;
import com.ussz.jobify.utilities.Tags;
import com.ussz.jobify.viewModel.ProfileViewModel;

import java.io.Serializable;
import java.util.Objects;

import mehdi.sakout.fancybuttons.FancyButton;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener , IUploadImageResult, IEditResult {

    private TextView profileName, profileUniversity, profileGraduationYear, profileDepartment, profileEmail, profilePhoneNumber;

    private View rootView;

    private BottomSheetDialog bottomSheetDialog;
    private static final int  GALLERY_REQUEST_CODE = 377;

    private ProgressBar progressBar4;

    private CircularImageView profile_image;

    private FirebaseAuth oAuth;

    private Graduate gGraduate;

    private RelativeLayout profileLayout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        FancyButton clickable = rootView.findViewById(R.id.profile_following_btn);

        profileName = rootView.findViewById(R.id.tv_name);
        profileUniversity = rootView.findViewById(R.id.profile_university);
        profileDepartment = rootView.findViewById(R.id.profile_department);
        profileGraduationYear = rootView.findViewById(R.id.profile_graduation_year);
        profileEmail = rootView.findViewById(R.id.profile_email);
        profilePhoneNumber = rootView.findViewById(R.id.profile_phone_number);

        profile_image = rootView.findViewById(R.id.profile_image);

        progressBar4 = rootView.findViewById(R.id.progressBar4);

        profileLayout = rootView.findViewById(R.id.profilelayout);


        oAuth = FirebaseAuth.getInstance();

        ProfileViewModel profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);



        profileViewModel.getMyProfile(Objects.requireNonNull(oAuth.getCurrentUser()).getUid()).observe(this, new Observer<Graduate>() {
            @Override
            public void onChanged(Graduate graduate) {
                setProfileData(rootView, graduate);
                gGraduate = graduate;

            }
        });


        clickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                if(gGraduate != null){
                    args.putSerializable(Tags.BUNDLE_KEY, (Serializable) gGraduate.getFollowing());
                }
                Navigation.findNavController(v).navigate(R.id.following_fragment_dest, args);

            }
        });


        profile_image.setOnClickListener(this);

        rootView.findViewById(R.id.tv_name).setOnClickListener(this);
//        rootView.findViewById(R.id.emailLL).setOnClickListener(this);
        rootView.findViewById(R.id.phoneNumberLL).setOnClickListener(this);
        rootView.findViewById(R.id.universityLL).setOnClickListener(this);
        rootView.findViewById(R.id.classOfLL).setOnClickListener(this);
        rootView.findViewById(R.id.departmentLL).setOnClickListener(this);


        createBottomSheetDialog();


        progressBar4.setVisibility(View.GONE);



        return rootView;

    }

    private void openUpGallery(String intentAction) {
        Intent getIntent = new Intent(intentAction);
        getIntent.setType("image/*");
        startActivityForResult(getIntent,GALLERY_REQUEST_CODE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == getActivity().RESULT_OK){
            if (requestCode == GALLERY_REQUEST_CODE) {//data.getData returns the content URI for the selected Image
                Uri selectedImage = data.getData();

                profile_image.setImageURI(selectedImage);

                ProfileImageRemote.uploadImage(selectedImage,oAuth.getCurrentUser().getUid(),ProfileFragment.this);

                showView();
            }
        }
    }

    private void showView() {
        progressBar4.setVisibility(View.VISIBLE);
        profile_image.setEnabled(false);
    }

    private void hideView(){
        progressBar4.setVisibility(View.GONE);
        profile_image.setEnabled(true);
    }


    private void createBottomSheetDialog(){
        if (bottomSheetDialog == null){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.selectimagebottomsheet,null);

            TextView uploadFromGalleryTv = view.findViewById(R.id.uploadFromGalleryTv);
            TextView uploadFromFile = view.findViewById(R.id.uploadFromFile);

            uploadFromGalleryTv.setOnClickListener(this);
            uploadFromFile.setOnClickListener(this);

            bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(view);
        }
    }

    private void setProfileData(View view, Graduate graduate){

        FancyButton profileFollowing = view.findViewById(R.id.profile_following_btn);

        profileName.setText(graduate.getName());
        profileUniversity.setText(graduate.getUniversity());

        profileEmail.setText(graduate.getEmail());
        profileDepartment.setText(graduate.getDepartment());
        profileGraduationYear.setText(String.valueOf(graduate.getGraduationYear()));
        profilePhoneNumber.setText(graduate.getPhoneNumber());

        if (graduate.getProfileImage()!=null){
            Picasso.get().load(graduate.getProfileImage()).into(profile_image);
        }

        profileFollowing.setText(graduate.getFollowing() != null ? "Following " + graduate.getFollowing().size() : "Following 0 ");



    }


    @Override
    public void onClick(View view) {
        String[] messageToEditFragment = new String[2];
        switch (view.getId()){
            case R.id.tv_name:
                messageToEditFragment[0] = "username";
                messageToEditFragment[1] = profileName.getText().toString();
                //username
                break;
            case R.id.emailLL:
                messageToEditFragment[0] = "email";
                messageToEditFragment[1] = profileEmail.getText().toString();
                //email
                break;
            case R.id.phoneNumberLL:
                messageToEditFragment[0] = "phonenumber";
                messageToEditFragment[1] = profilePhoneNumber.getText().toString();
                //phone number
                break;
            case R.id.universityLL:
                messageToEditFragment[0] = "university";
                messageToEditFragment[1] = profileUniversity.getText().toString();
                //university
                break;
            case R.id.classOfLL:
                messageToEditFragment[0] = "graduationyear";
                messageToEditFragment[1] = profileGraduationYear.getText().toString();
                //class of
                break;
            case R.id.departmentLL:
                messageToEditFragment[0] = "department";
                messageToEditFragment[1] = profileDepartment.getText().toString();
                //department
                break;
            case R.id.profile_image:
                bottomSheetDialog.show();
                return;
            case  R.id.uploadFromGalleryTv:
                openUpGallery(Intent.ACTION_PICK);
                bottomSheetDialog.cancel();
                return;
            case R.id.uploadFromFile:
                openUpGallery(Intent.ACTION_GET_CONTENT);
                bottomSheetDialog.cancel();
                return;
        }


        navigateToEditFragment(messageToEditFragment,view);
    }

    private void navigateToEditFragment(String[] messageToEditFragment, View view) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("messageForEditing",messageToEditFragment);
        Navigation.findNavController(view).navigate(R.id.toEditProfile,bundle);

    }


    private void showSuccessMessage(String message){
        Snackbar snackbar = Snackbar.make(profileLayout,message,Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.green));
        snackbar.show();
    }

    private void showFailureMessage(String message){
        Snackbar snackbar = Snackbar.make(profileLayout,message,Snackbar.LENGTH_LONG);
        snackbar.setTextColor(getResources().getColor(R.color.red));
        snackbar.show();
    }


    @Override
    public void uploadImageResult(String result) {
        if (result.equals("Upload failed")){
            showFailureMessage(result);
        }
        else{
            EditRemote.updateProfileImage(result,ProfileFragment.this);
        }
        hideView();
    }


    @Override
    public void editResult(String result) {
        if(result.equals("Transaction success!")){
            showSuccessMessage("update success");
        }
        else{
            showFailureMessage("Update failed");
        }
    }
}
