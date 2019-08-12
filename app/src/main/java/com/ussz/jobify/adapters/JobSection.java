package com.ussz.jobify.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.utilities.Helper;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import mehdi.sakout.fancybuttons.FancyButton;

public class JobSection extends StatelessSection {

    final String title;

    final List<Job> jobs;


    public JobSection(String title , List<Job> jobs) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.jobs_list_item)
                .headerResourceId(R.layout.item_section)
                .build());

        this.title = title;
        this.jobs = jobs;
    }

    @Override
    public int getContentItemsTotal() {
        return jobs.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;


        Job job = jobs.get(position);

        itemViewHolder.title.setText(job.getTitle());
        itemViewHolder.orgName.setText(job.getOrganizationName());
        itemViewHolder.description.setText(job.getDescription());
        itemViewHolder.studentLimit.setText(job.getStudentLimit() +" spot");

        String orgCapital = job.getOrganizationName().substring(0,1).toUpperCase();
        char letter = orgCapital.charAt(0);
        String currentColor = Helper.getRandomColorString(letter);

        itemViewHolder.orgName1.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        itemViewHolder.orgName1.setText(orgCapital);

        itemViewHolder.studentLimit.getBackground().setColorFilter(Color.parseColor(currentColor),PorterDuff.Mode.SRC_ATOP);
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

        headerViewHolder.tvTitle.setText(title);
    }


    //view holders
    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = view.findViewById(R.id.title_section);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView description;
        private final TextView title;
        private final TextView orgName;
        private final FancyButton studentLimit;
        private final TextView orgName1;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            orgName = view.findViewById(R.id.textView16);
            studentLimit = view.findViewById(R.id.leftSpace);
            description = view.findViewById(R.id.textView17);
            title = view.findViewById(R.id.textView2);
            orgName1 = view.findViewById(R.id.organizationimage);
        }
    }
}
