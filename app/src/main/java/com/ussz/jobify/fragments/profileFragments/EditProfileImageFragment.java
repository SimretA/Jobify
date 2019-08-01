package com.ussz.jobify.fragments.profileFragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ussz.jobify.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileImageFragment extends Fragment implements View.OnClickListener {


    private BottomSheetDialog bottomSheetDialog;
    private CircularImageView profile_image;
    private static final int  GALLERY_REQUEST_CODE = 377;
    TextView uploadFromGalleryTv,uploadFromFile;
    
    public EditProfileImageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_profile_image, container, false);

        profile_image = rootView.findViewById(R.id.profile_image);


        profile_image.setOnClickListener(this);

        createBottomSheetDialog();
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
            }
        }
    }

    private void createBottomSheetDialog(){
        if (bottomSheetDialog == null){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.selectimagebottomsheet,null);

            uploadFromGalleryTv = view.findViewById(R.id.uploadFromGalleryTv);
            uploadFromFile = view.findViewById(R.id.uploadFromFile);

            uploadFromGalleryTv.setOnClickListener(this);
            uploadFromFile.setOnClickListener(this);

            bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(view);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.profile_image:
                bottomSheetDialog.show();
                break;
            case  R.id.uploadFromGalleryTv:
                openUpGallery(Intent.ACTION_PICK);
                bottomSheetDialog.cancel();
                break;
            case R.id.uploadFromFile:
                openUpGallery(Intent.ACTION_GET_CONTENT);
                bottomSheetDialog.cancel();
                break;
        }
    }
}
