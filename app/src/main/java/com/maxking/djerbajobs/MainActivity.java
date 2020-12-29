package com.maxking.djerbajobs;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirebaseRecyclerAdapter adapter;
    Intent adminActivity;
    String TAG="MainActivity";
    common common=new common();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager linearLayoutManager;
        recyclerView=findViewById(R.id.jobsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
         adminActivity=new Intent(getApplicationContext(),admin.class);
        loadJobs();
        runAd();
        addBtnListener();
//        Admin Only

    }

    private void loadJobs() {
        Log.d(TAG,"loadJobs() runned");
         Query query = FirebaseDatabase.getInstance().
                getReference("jobs").orderByKey();

        FirebaseRecyclerOptions<job> options =
                new FirebaseRecyclerOptions.Builder<job>()
                        .setQuery(query, new SnapshotParser<job>() {
                            @NonNull

                            @Override
                            public job parseSnapshot(@NonNull DataSnapshot snapshot) {
                                Log.d(TAG,"parseSnapshot runned");
                                String pushKey=snapshot.getKey().toString();
                                String jobTitle=snapshot.child("jobTitle").getValue().toString();
                                String companyName=snapshot.child("companyName").getValue().toString();
                                String location=snapshot.child("location").getValue().toString();
                                String experience=snapshot.child("experience").getValue().toString();
                                String skills=snapshot.child("skills").getValue().toString();

                                Log.d(TAG,jobTitle+companyName+location);
                                return new job(jobTitle,companyName,location,experience,skills,pushKey);

                            }
                        }).build();


        adapter = new FirebaseRecyclerAdapter<job,ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i,
                                            @NonNull final job job) {
                viewHolder.setJobTitle(common.makeFirstLetterCap(job.getJobTitle()));
                viewHolder.setCompanyName(common.makeFirstLetterCap(job.getCompanyName()));
                viewHolder.setLocation(common.makeFirstLetterCap(job.getLocation()));
                viewHolder.setExperienceText("Experience: "+job.getExperience());
                viewHolder.setSkillsText("Skills: "+common.makeFirstLetterCap(job.getSkills()));
                viewHolder.shareBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    shareAction(job.getJobTitle(),job.getCompanyName());
                  }
              });
              viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                  @Override
                  public boolean onLongClick(View v) {
                      adminActivity.putExtra("pushKey",job.getPushKey());
                      adminActivity.putExtra("jobTitle",job.getJobTitle());
                      adminActivity.putExtra("companyName",job.getCompanyName());
                      adminActivity.putExtra("location",job.getLocation());
                      adminActivity.putExtra("experience",job.getExperience());
                      adminActivity.putExtra("skills",job.getSkills());
                      com.maxking.djerbajobs.admin.isEdit =true;
                      startActivity(adminActivity);
                      return true;
                  }
                });
//                ADMIN ONLY

                Log.d(TAG,"onBindViewHolder"+job.getJobTitle()+job.getCompanyName()+job.getLocation());
            }


            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_card,
                        parent, false);
                return new ViewHolder(view);

            }

        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    }

    private void shareAction(String job,String company) {
        Intent shareIntent =new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,"There are vacancy in "+company
        +" for "+job+"Check Djerba Jobs");
        if (shareIntent.resolveActivity
                (getPackageManager())!=null)
        {
            startActivity(shareIntent);
        }

    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        private TextView jobTitle,experienceText,skillsText;
        private TextView companyName;
        private TextView location;
        private ImageButton shareBtn;


        private ViewHolder(View itemView) {
            super(itemView);
            jobTitle=itemView.findViewById(R.id.job_title);
            companyName = itemView.findViewById(R.id.company_title);
            location=itemView.findViewById(R.id.location_title);
            experienceText=itemView.findViewById(R.id.experienceText);
            skillsText=itemView.findViewById(R.id.skillsText);
            shareBtn=itemView.findViewById(R.id.shareBtn);

        }

        private void setJobTitle(String jobTitle) {
            this.jobTitle.setText(jobTitle);
        }
        private void setCompanyName(String companyName) {
            this.companyName.setText(companyName);
        } private void setLocation(String location) {
            this.location.setText(location);
        }

        public void setExperienceText(String experienceText) {
            this.experienceText.setText(experienceText);
        }

        public void setSkillsText(String skillsText) {
            this.skillsText.setText(skillsText);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }



    private void addBtnListener()
    {
        FloatingActionButton addBtn=findViewById(R.id.fabBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goAdminActivity();
                
            }
        });
        
    }
    private void goAdminActivity()
    {

        startActivity(adminActivity);
    }
    private void runAd()
    {
        AdView adView=findViewById(R.id.adView);
        MobileAds.initialize(this,"ca-app-pub-6502170001397731~4206053990");
        AdRequest adRequest=new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}
