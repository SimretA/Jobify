package com.ussz.jobify.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.ussz.jobify.R;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.utilities.Helper;

import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import mehdi.sakout.fancybuttons.FancyButton;

public class MeetupSection extends StatelessSection {

    final String title;

    final List<Meetup> meetups;


    public MeetupSection(String title , List<Meetup> meetups) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.meetup_list_item)
                .headerResourceId(R.layout.item_section)
                .build());

        this.title = title;
        this.meetups = meetups;
    }

    @Override
    public int getContentItemsTotal() {
        return meetups.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MeetupSection.ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MeetupSection.ItemViewHolder itemViewHolder = (MeetupSection.ItemViewHolder) holder;


        Meetup meetup = meetups.get(position);

        itemViewHolder.meetUpname.setText(meetup.getName());
        itemViewHolder.meetUpOrganizer.setText(meetup.getOrganizationName());
        itemViewHolder.meetupDescription.setText(meetup.getDescription());
        itemViewHolder.leftSpace.setText(meetup.getStudentLimit() +" spot");


        String orgCapital = meetup.getOrganizationName().substring(0,1).toUpperCase();
        char letter = orgCapital.charAt(0);
        String currentColor = Helper.getRandomColorString(letter);

        itemViewHolder.meetUpname1.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        itemViewHolder.meetUpname1.setText(orgCapital);

        itemViewHolder.leftSpace.getBackground().setColorFilter(Color.parseColor(currentColor),PorterDuff.Mode.SRC_ATOP);
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new MeetupSection.HeaderViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        MeetupSection.HeaderViewHolder headerViewHolder = (MeetupSection.HeaderViewHolder) holder;

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
        private final TextView meetupDescription;
        private final TextView meetUpname;
        private final TextView meetUpname1;
        private final FancyButton leftSpace;
        private final TextView meetUpOrganizer;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            meetUpname = view.findViewById(R.id.textView2);
            meetUpname1 = view.findViewById(R.id.organizationimage);
            meetUpOrganizer = view.findViewById(R.id.textView3);
            meetupDescription = view.findViewById(R.id.textView14);
            leftSpace = view.findViewById(R.id.leftSpace);
        }
    }
}
