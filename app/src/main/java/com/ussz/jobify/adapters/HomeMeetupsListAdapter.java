package com.ussz.jobify.adapters;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Meetup;
import com.ussz.jobify.fragments.exploreFragments.ExploreMeetupsFragment;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.Helper;
import com.ussz.jobify.utilities.TimeStampConverter;

import java.sql.Time;
import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class HomeMeetupsListAdapter extends RecyclerView.Adapter<HomeMeetupsListAdapter.HomeMeetupsViewHolder>  {


    private Fragment fragment;
    private ArrayList<Meetup> meetupsArrayList;
    private CustomOnClickListener listener;


    public HomeMeetupsListAdapter(Fragment fragment, ArrayList<Meetup> meetupsArrayList, CustomOnClickListener listener) {
        this.fragment = fragment;
        this.meetupsArrayList = meetupsArrayList;
        this.listener = listener;
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
        holder.meetupTitle.setText(meetup.getName());
        holder.meetupDescription.setText(meetup.getDescription());


        String orgCapital = meetup.getOrganizationName().substring(0,1).toUpperCase();

        String currentColor = Helper.getRandomColorString(orgCapital.charAt(0));

        holder.meetupName1.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        holder.meetupName1.setText(orgCapital);


        holder.submitedBy.setText(meetup.getOrganizationName());

        if (fragment instanceof ExploreMeetupsFragment){
            holder.studentLimit.setText("" + (meetup.getStudentLimit()-position*2) + " Spot");
        }
        else{
            //here call function that will calculate duration left
            if (meetup.getWhen()!=null){
                holder.studentLimit.setText("In " + (TimeStampConverter.timeLeft(meetup.getWhen().toDate())) + " days");
            }

        }
        holder.studentLimit.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        holder.bind(meetup,listener);


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
        private TextView meetupName1;
        private TextView submitedBy;
        private FancyButton studentLimit;

        public HomeMeetupsViewHolder(@NonNull View itemView) {
            super(itemView);
            meetupTitle = itemView.findViewById(R.id.textView2);
            meetupDescription = itemView.findViewById(R.id.textView14);
            submitedBy = itemView.findViewById(R.id.textView3);
            meetupName1 = itemView.findViewById(R.id.organizationimage);
            studentLimit = itemView.findViewById(R.id.leftSpace);

        }
        public void bind(Meetup meetup, CustomOnClickListener listener){
            itemView.setOnClickListener(v-> listener.showDetails(meetup,v));
        }
    }
}
