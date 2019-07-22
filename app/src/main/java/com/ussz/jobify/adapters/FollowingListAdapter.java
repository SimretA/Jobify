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
import com.ussz.jobify.data.Company;
import java.util.ArrayList;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.FollowingViewHolder> {

    private Context context;
    private ArrayList<Company> followingCompanies;


    public FollowingListAdapter(Context context, ArrayList<Company> following) {
        this.context = context;
        this.followingCompanies = following;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.following_list_item, parent, false);

        return new FollowingViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        Company currentCompany = followingCompanies.get(position);
        holder.companyName.setText(currentCompany.getCompanyName());
        holder.companyBio.setText(currentCompany.getCompanyBio());
        holder.companyImage.setImageResource(R.mipmap.profile_avatar_round);


    }

    @Override
    public int getItemCount() {
        return followingCompanies.size();
    }


    class FollowingViewHolder extends RecyclerView.ViewHolder{

        public ImageView companyImage;
        public TextView companyName;
        public TextView companyBio;

        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
        companyImage = itemView.findViewById(R.id.company_image_recycler);
            companyBio = itemView.findViewById(R.id.company_bio_recycler);
            companyName = itemView.findViewById(R.id.company_name_recycler);

        }
    }
}

