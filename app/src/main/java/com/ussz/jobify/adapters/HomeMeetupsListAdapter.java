package com.ussz.jobify.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.fragments.exploreFragments.ExploreMeetupsFragment;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class HomeMeetupsListAdapter extends RecyclerView.Adapter<HomeMeetupsListAdapter.HomeMeetupsViewHolder>  {


    private Fragment fragment;
    private ArrayList<Meetup> meetupsArrayList;


    public HomeMeetupsListAdapter(Fragment fragment, ArrayList<Meetup> meetupsArrayList) {
        this.fragment = fragment;
        this.meetupsArrayList = meetupsArrayList;
    }

    @NonNull
    @Override
    public HomeMeetupsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meetup_list_item, parent, false);

        return new HomeMeetupsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMeetupsViewHolder holder, int position) {
        Meetup meetup = meetupsArrayList.get(position);
        holder.meetupTitle.setText(meetup.getName()+ " " + position);
        holder.meetupDescription.setText(meetup.getDescription());
        holder.meetupImage.setImageResource(R.mipmap.profile_avatar_round);

        holder.submitedBy.setText("Google "+position);

        if (fragment instanceof ExploreMeetupsFragment){
            holder.studentLimit.setText("" + (meetup.getStudentLimit()-position*2) + " Spot");
        }
        else{
            //here call function that will calculate duration left
            holder.studentLimit.setText("In " + (meetup.getStudentLimit()-position*2) + " days");
        }


    }

    @Override
    public int getItemCount() {
        return meetupsArrayList.size();
    }

    public void setMeetupsArrayList(ArrayList<Meetup> meetupsArrayList){
        this.meetupsArrayList = meetupsArrayList;
        notifyDataSetChanged();
    }


    class HomeMeetupsViewHolder extends RecyclerView.ViewHolder{

        // public TextView organizationImage;
        private TextView meetupTitle;
        private TextView meetupDescription;
        private ImageView meetupImage;
        private TextView submitedBy;
        private FancyButton studentLimit;

        public HomeMeetupsViewHolder(@NonNull View itemView) {
            super(itemView);
            meetupTitle = itemView.findViewById(R.id.textView2);
            meetupDescription = itemView.findViewById(R.id.textView14);
            submitedBy = itemView.findViewById(R.id.textView3);
            meetupImage = itemView.findViewById(R.id.organizationimage);
            studentLimit = itemView.findViewById(R.id.leftSpace);

        }
    }
}
