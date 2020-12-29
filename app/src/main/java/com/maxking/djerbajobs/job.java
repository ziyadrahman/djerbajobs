package com.maxking.djerbajobs;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class job {

    private String jobTitle,companyName,location,experience,skills,pushKey;
    public job()
    {

    }
    public job(String jobTitle,String companyName,String location,String experience,String skills,String pushKey)
    {
        this.jobTitle=jobTitle;
        this.companyName=companyName;
        this.location=location;
        this.experience=experience;
        this.skills=skills;
        this.pushKey=pushKey;
    }
    public job(String jobTitle,String companyName,String location,String experience,String skills)
    {
        this.jobTitle=jobTitle;
        this.companyName=companyName;
        this.location=location;
        this.experience=experience;
        this.skills=skills;

    }


    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public String getExperience() {
        return experience;
    }

    public String getSkills() {
        return skills;
    }

    public String getPushKey() {
        return pushKey;
    }
}
