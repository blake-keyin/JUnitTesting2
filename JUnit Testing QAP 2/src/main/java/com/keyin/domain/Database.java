package com.keyin.domain;

import com.keyin.domain.appointment.AppointmentSlot;
import com.keyin.domain.donor.BloodDonor;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class Database {

    public List<AppointmentSlot> getAppointmentSlots() {
        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();

        AppointmentSlot appointmentSlot = new AppointmentSlot();

        appointmentSlot.setID(1);
        appointmentSlot.setLocation("321 Long St. St. John's NL");
        appointmentSlot.setBloodType("A");
        appointmentSlots.add(appointmentSlot);

        return appointmentSlots;
    }

    public BloodDonor getDonor(int id){
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setLastDonationDate(LocalDate.of(2021,Month.JANUARY,1));
        bloodDonor.setDateOfBirth(LocalDate.of( 1992, Month.MARCH, 13));
        bloodDonor.setNextAppointment(LocalDate.of(2021,Month.FEBRUARY,23));
        return bloodDonor;
    }
}
