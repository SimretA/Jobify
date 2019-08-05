package com.ussz.jobify.adapters;

import android.content.Context;
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
        holder.companyImage.setImageResource(R.mipmap.profile_avatar_round);

        holder.unfollow.setOnClickListener(view ->{
            this.followingCompanies.remove(position);
            OrganizationRemote.unfollow(null, currentOrganization);

            //notifyDataSetChanged();

        });

        holder.bind(currentOrganization);


    }

    @Override
    public int getItemCount() {
        return followingCompanies.size();
    }

    public void setOrginizations(ArrayList<Organization> organizations) {
        this.followingCompanies = organizations;
        notifyDataSetChanged();
    }


    class FollowingViewHolder extends RecyclerView.ViewHolder{

        private ImageView companyImage;
        private TextView companyName;
        private TextView companyBio;
        private FancyButton unfollow;

        public FollowingViewHolder(@NonNull View itemView) {
            super(itemView);
            companyImage = itemView.findViewById(R.id.company_image_recycler);
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

