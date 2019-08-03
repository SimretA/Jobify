package com.ussz.jobify.adapters;

import android.view.View;

import com.ussz.jobify.data.Job;

public interface OnJobClickedListener{
    void showJobDetails(Job job, View view);
}
