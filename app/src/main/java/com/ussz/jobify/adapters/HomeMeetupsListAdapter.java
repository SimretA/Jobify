package com.ussz.jobify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Job;
import com.ussz.jobify.data.Meetup;

import java.util.ArrayList;

public class HomeMeetupsListAdapter extends RecyclerView.Adapter<HomeMeetupsListAdapter.HomeMeetupsViewHolder>  {


    private Context context;
    private ArrayList<Meetup> meetupsArrayList;


    public HomeMeetupsListAdapter(Context context, ArrayList<Meetup> meetupsArrayList) {
        this.context = context;
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
        holder.studentLimit.setText("" + (meetup.getStudentLimit()-position*2));
    }

    @Override
    public int getItemCount() {
        return meetupsArrayList.size();
    }


    class HomeMeetupsViewHolder extends RecyclerView.ViewHolder{

        // public TextView companyImage;
        private TextView meetupTitle;
        private TextView meetupDescription;
        private ImageView meetupImage;
        private TextView studentLimit;

        public HomeMeetupsViewHolder(@NonNull View itemView) {
            super(itemView);
            meetupTitle = itemView.findViewById(R.id.textView2);
            meetupDescription = itemView.findViewById(R.id.textView17);
            meetupImage = itemView.findViewById(R.id.organizationimage);
            studentLimit = itemView.findViewById(R.id.textView18);

        }
    }
}
