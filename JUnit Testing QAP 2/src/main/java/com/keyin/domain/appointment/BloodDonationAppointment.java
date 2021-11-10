package com.keyin.domain.appointment;

import java.util.Date;

public class BloodDonationAppointment {
    private int ID;
    private Date dateTime;
    private int duration;
    private String location;
    private String bloodType;
    private Boolean firstTimeDonor;
    private String donorID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Boolean getFirstTimeDonor() {
        return firstTimeDonor;
    }

    public void setFirstTimeDonor(Boolean firstTimeDonor) {
        this.firstTimeDonor = firstTimeDonor;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }
}
