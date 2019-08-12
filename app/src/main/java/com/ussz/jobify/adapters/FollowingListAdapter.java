package com.ussz.jobify.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Organization;
import com.ussz.jobify.network.OrganizationRemote;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.Helper;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class FollowingListAdapter extends RecyclerView.Adapter<FollowingListAdapter.FollowingViewHolder> {

    private Context context;
    private ArrayList<Organization> followingCompanies;
    private CustomOnClickListener listener;


    public FollowingListAdapter(Context context, ArrayList<Organization> following, CustomOnClickListener listener) {
        this.context = context;
        this.followingCompanies = following;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FollowingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.following_list_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(v).navigate(R.id.org_profile_fragment_dest);
            }
        });

        return new FollowingViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull FollowingViewHolder holder, int position) {
        Organization currentOrganization = followingCompanies.get(position);
        holder.companyName.setText(currentOrganization.getOrganizationName());
        holder.companyBio.setText(currentOrganization.getOrganizationBio());


        String orgCapital = currentOrganization.getOrganizationName().substring(0,1).toUpperCase();
        char letter = orgCapital.charAt(0);
        String currentColor = Helper.getRandomColorString(letter);


        holder.companyName1.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        holder.companyName1.setText(orgCapital);

        if(currentOrganization.isFollowing()){
            holder.unfollow.setText("Unfollow");
        }
        else if(!currentOrganization.isFollowing()){
            holder.unfollow.setText("Follow");
        }
        else{
            holder.unfollow.setText("---");
        }
        holder.unfollow.setOnClickListener(view ->{
          if(currentOrganization.isFollowing()){

              holder.unfollow.setText("Follow");
              // holder.unfollow.setBackgroundColor(R.color.white);
              OrganizationRemote.unfollow(currentOrganization);
              this.followingCompanies.get(position).setFollowing(false);

          }
          else {
              holder.unfollow.setText("Unfollow");
              // holder.unfollow.setBackgroundColor(R.color.white);
              OrganizationRemote.follow(currentOrganization);
              this.followingCompanies.get(position).setFollowing(true);

          }
            notifyDataSetChanged();

        });


        holder.unfollow.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        holder.bind(currentOrganization);


    }

    @Override
    public int getItemCount() {
        return followingCompanies.size();
    }

    public void setOrganizations(ArrayList<Organization> organizations) {
        this.followingCompanies = organizations;
        notifyDataSetChanged();
    }


    class FollowingViewHolder extends RecyclerView.ViewHolder{

        private TextView companyName1;
        private TextView companyName;
        private TextView companyBio;
        private FancyButton unfollow;

        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            companyName1 = itemView.findViewById(R.id.company_image_recycler);
            companyBio = itemView.findViewById(R.id.company_bio_recycler);
            companyName = itemView.findViewById(R.id.company_name_recycler);
            unfollow = itemView.findViewById(R.id.following_list_unfollow);

        }
        public void bind(Organization organization){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.showDetails(organization, itemView);
                }
            });
        }
    }
}

