package com.ussz.jobify.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ussz.jobify.R;
import com.ussz.jobify.data.Company;
import com.ussz.jobify.data.Job;

import org.w3c.dom.Text;

import java.util.ArrayList;

import mehdi.sakout.fancybuttons.FancyButton;

public class HomeJobsListAdapter extends RecyclerView.Adapter<HomeJobsListAdapter.HomeJobsViewHolder> {

    private Context context;
    private ArrayList<Job> jobArrayList;


    public HomeJobsListAdapter(Context context, ArrayList<Job> jobArrayList) {
        this.context = context;
        this.jobArrayList = jobArrayList;
    }


    @NonNull
    @Override
    public HomeJobsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.jobs_list_item, parent, false);

        return new HomeJobsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeJobsViewHolder holder, int position) {
        Job j = jobArrayList.get(position);
        holder.jobTitle.setText(j.getTitle());
        holder.jobDescription.setText(j.getDescription());
        holder.companyName.setText("Google " + position);
        holder.companyImage.setImageResource(R.mipmap.profile_avatar_round);
        holder.leftSpace.setText("" + (j.getStudentLimit()-position*2) + " Spot");
    }

    @Override
    public int getItemCount() {
        return jobArrayList.size();
    }

    class HomeJobsViewHolder extends RecyclerView.ViewHolder{

        // public TextView companyImage;
        private TextView jobTitle;
        private TextView jobDescription;
        private TextView companyName;
        private ImageView companyImage;
        private FancyButton leftSpace;

        public HomeJobsViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.textView2);
            jobDescription = itemView.findViewById(R.id.textView17);
            companyName = itemView.findViewById(R.id.textView16);
            companyImage = itemView.findViewById(R.id.organizationimage); //textView18
            leftSpace = itemView.findViewById(R.id.leftSpace);

        }
    }
}
