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
import com.ussz.jobify.data.Job;
import com.ussz.jobify.fragments.homeFragments.HomeJobsFragment;
import com.ussz.jobify.utilities.CustomOnClickListener;
import com.ussz.jobify.utilities.Helper;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class HomeJobsListAdapter extends RecyclerView.Adapter<HomeJobsListAdapter.HomeJobsViewHolder> {

    private Fragment fragment;
    private ArrayList<Job> jobArrayList;
    private CustomOnClickListener listener;




    public HomeJobsListAdapter(Fragment fragment, ArrayList<Job> jobArrayList, CustomOnClickListener listener) {
        this.fragment = fragment;
        this.jobArrayList = jobArrayList;
        this.listener = listener;
    }


    @NonNull
    @Override
    public HomeJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobs_list_item, parent, false);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return new HomeJobsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeJobsViewHolder holder, int position) {



        Job job = jobArrayList.get(position);
        holder.jobTitle.setText(job.getTitle());
        holder.jobDescription.setText(job.getDescription());
        holder.companyName.setText(job.getOrganizationName());

        String orgCaptial = job.getOrganizationName().substring(0,1).toUpperCase();
        char letter = orgCaptial.charAt(0);
        String currentColor = Helper.getRandomColorString(letter);

        holder.companyName1.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        holder.companyName1.setText(orgCaptial);

        if (fragment instanceof HomeJobsFragment){
            //here call function that will calculate duration left
            holder.leftSpace.setText("In " + (job.getStudentLimit()-position*2) + " Days");
        }
        else{
            holder.leftSpace.setText("" + (job.getStudentLimit()-position*2) + " Spot");
        }
        holder.leftSpace.getBackground().setColorFilter(Color.parseColor(currentColor), PorterDuff.Mode.SRC_ATOP);
        holder.bind(job, listener);

    }

    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    public void setJobs(ArrayList<Job> jobs){
        this.jobArrayList = jobs;
        notifyDataSetChanged();
    }

    class HomeJobsViewHolder extends RecyclerView.ViewHolder{

        private TextView jobTitle;
        private TextView jobDescription;
        private TextView companyName;
        private TextView companyName1;
        private FancyButton leftSpace;

        public HomeJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.textView2);
            jobDescription = itemView.findViewById(R.id.textView17);
            companyName = itemView.findViewById(R.id.textView16);
            companyName1 = itemView.findViewById(R.id.organizationimage); //textView18
            leftSpace = itemView.findViewById(R.id.leftSpace);

        }

        public void bind(Job job, CustomOnClickListener listener) {
            itemView.setOnClickListener(v -> listener.showDetails(job, v));
        }
    }
}
