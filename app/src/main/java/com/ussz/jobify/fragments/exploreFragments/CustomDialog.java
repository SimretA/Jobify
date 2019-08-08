package com.ussz.jobify.fragments.exploreFragments;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.ussz.jobify.R;

public class CustomDialog {

    private Dialog dialog;

    public CustomDialog(Context context){
        dialog = new Dialog(context);

    }


    public WindowManager.LayoutParams getWindowLayoutParams(){
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        return lp;
    }

    public Dialog build(){
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.filterdialog);
        dialog.setCancelable(true);


//        departmentEt = dialog.findViewById(R.id.textInputLayout);
//        orgEt = dialog.findViewById(R.id.textInputLayout5);
//        salaryEt = dialog.findViewById(R.id.textInputLayout9);
//        locationEt = dialog.findViewById(R.id.textInputLayout19);
//
//
//        handleVisibility();


//        ((ImageButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });




//        dialog.show();
        dialog.getWindow().setAttributes(getWindowLayoutParams());
        return dialog;
    }

}
