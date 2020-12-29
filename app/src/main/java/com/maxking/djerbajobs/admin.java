package com.maxking.djerbajobs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class admin extends AppCompatActivity {
    TextInputEditText jobText;
    TextInputEditText companyNameText;
    TextInputEditText locationText;
    TextInputEditText expEditText;
    TextInputEditText skillsEditText;

    MaterialButton addBtn,deleteBtn;
    RelativeLayout mainLayout;
    String  job,company,location;
    String TAG="ADMIN";
    static boolean isEdit=false;
    private String pushKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        jobText=findViewById(R.id.jobName);
        companyNameText=findViewById(R.id.companyName);
        locationText=findViewById(R.id.locationName);
        expEditText=findViewById(R.id.experienceEditText);
        skillsEditText=findViewById(R.id.skillsEditText);
        addBtn=findViewById(R.id.addBtn);
        deleteBtn=findViewById(R.id.deleteBtn);
        mainLayout=findViewById(R.id.mainLayout);
        if (isEdit)
        {
            editModeOn();
            deleteBtnListener();

        }
        textInputEditTextEmptyChecker();
        btnListener();


    }

    private void deleteBtnListener() {
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteJob();
            }
        });
    }

    private void deleteJob() {
        DatabaseReference jobs= FirebaseDatabase.getInstance().getReference("jobs");
        jobs.child(pushKey).removeValue();
        makeSnackBar("Deleted");
        backToLastActivity();
    }

    private void editModeOn() {
        setIntentData();
        visibleAddBtn();
        visibleDeleteBtn();
    }

    private void visibleDeleteBtn() {
        deleteBtn.setVisibility(View.VISIBLE);
    }

    public String getJob(){
        try {
            return jobText.getText().toString().trim().toLowerCase();
        }
        catch (NullPointerException e)
        {
            Log.d(TAG,e.getMessage());
            return null;
        }
    }
    public String getCompany(){
        try {
            return companyNameText.getText().toString().trim().toLowerCase();
        }
        catch (NullPointerException e)
        {
            Log.d(TAG,e.getMessage());
            return null;
        }
    }
    public String getLocation(){
        try {
            return locationText.getText().toString().trim().toLowerCase();
        }
        catch (NullPointerException e)
        {
            Log.d(TAG,e.getMessage());
            return null;
        }
    }
    public String getExperience(){
        try {
            return expEditText.getText().toString().trim().toLowerCase();
        }
        catch (NullPointerException e)
        {
            Log.d(TAG,e.getMessage());
            return null;
        }
    }
    public String getSkills(){
        try {
            return skillsEditText.getText().toString().trim().toLowerCase();
        }
        catch (NullPointerException e)
        {
            Log.d(TAG,e.getMessage());
            return null;
        }
    }
    public void textInputEditTextEmptyChecker()
    {
        jobText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (validation())
                {
                   visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }

            }
        });
        companyNameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }

            }
        });
        locationText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }

            }
        });
        expEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }

            }
        });
        skillsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                if (validation())
                {
                    visibleAddBtn();
                }
                else {
                    goneAddBtn();
                }

            }
        });



    }
    public void visibleAddBtn()
    {
        addBtn.setVisibility(View.VISIBLE);
    }
    public void goneAddBtn()
    {
        addBtn.setVisibility(View.GONE);
    }
    public boolean validation()
    {
        return !getJob().isEmpty() && !getCompany().isEmpty() && !getLocation().isEmpty()
                && !getExperience().isEmpty() && !getSkills().isEmpty();
    }
    public void btnListener()
    {

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEdit)
                {
                    updateJob();

                }
                else {
                    addJob();
                }
            }
        });
    }
    public void makeSnackBar(String msg)
    {
        Snackbar snackbar = Snackbar.make(mainLayout,msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
    DatabaseReference jobs= FirebaseDatabase.getInstance().getReference("jobs");
    public void addJob()
    {


        com.maxking.djerbajobs.job job=new job(getJob(),getCompany(),getLocation(),getExperience(),getSkills());
        jobs.push().setValue(job).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    makeSnackBar("Success");
                    dataReset();
                }
                else {
                    makeSnackBar("Something Went Wrong..Try Again");
                }
            }
        });

    }
    public void updateJob()
    {
        com.maxking.djerbajobs.job job=new job(getJob(),getCompany(),getLocation(),getExperience(),getSkills());
        jobs.child(pushKey).setValue(job).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@NonNull Task<Void> task) {
            if (task.isSuccessful())
            {
                makeSnackBar("Success");
                dataReset();
                isEdit=false;
            }
            else {
                makeSnackBar("Something Went Wrong..Try Again");
            }
        }
    });

    }
    private void dataReset()
    {
        jobText.setText("");
        companyNameText.setText("");
        locationText.setText("");
        expEditText.setText("");
        skillsEditText.setText("");
        goneAddBtn();
    }
    private void setIntentData() {
        Intent intent = getIntent();
        pushKey = intent.getStringExtra("pushKey");
        jobText.setText(intent.getStringExtra("jobTitle"));
        companyNameText.setText(intent.getStringExtra("companyName"));
        locationText.setText(intent.getStringExtra("location"));
        expEditText.setText(intent.getStringExtra("experience"));
        skillsEditText.setText(intent.getStringExtra("skills"));

    }
    private void backToLastActivity() {

        admin.super.finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        isEdit=false;
        dataReset();
    }
}
