package com.keyin.manager;

import com.keyin.domain.Database;
import com.keyin.domain.appointment.AppointmentSlot;
import com.keyin.domain.appointment.BloodDonationAppointment;
import com.keyin.domain.donor.BloodDonor;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BloodDonationAppointmentManager {
    private Database database;

    public BloodDonationAppointmentManager(Database database) {
        this.database = database;
    }

    public BloodDonationAppointment bookAppointment(int bloodDonorId) throws InvalidDonationSchedulingException{
        BloodDonationAppointment bloodDonationAppointment = new BloodDonationAppointment();
        bloodDonationAppointment.setID(bloodDonorId);
        BloodDonor bloodDonor = database.getDonor(bloodDonorId);

        LocalDate today = LocalDate.now();
        LocalDate tooYoung = today.minus(18, ChronoUnit.YEARS);
        LocalDate tooOld = today.minus(80,ChronoUnit.YEARS);
        LocalDate tooSoon = today.minus(56, ChronoUnit.DAYS);
        LocalDate tooLate = today.minus(365,ChronoUnit.DAYS);

        if (bloodDonor.getLastDonationDate() == null){
            bloodDonationAppointment.setFirstTimeDonor(true);
        }

        if (bloodDonor.getDateOfBirth().isAfter(tooYoung)) {
            throw new InvalidDonationSchedulingException("Donor is too young.");
        }

        if (bloodDonor.getDateOfBirth().isBefore(tooOld)){
            throw new InvalidDonationSchedulingException("Donor is too old.");
        }

        List<AppointmentSlot> appointmentSlotList = database.getAppointmentSlots();

        for (AppointmentSlot appointmentSlot: appointmentSlotList) {
            if (appointmentSlot.getBloodType().equalsIgnoreCase(bloodDonor.getBloodType())) {

            } else {
                throw new InvalidDonationSchedulingException("Invalid blood type.");
            }
            if (appointmentSlot.getDate().isBefore(tooLate)){
                throw new InvalidDonationSchedulingException("Appointments must be no more than 1 year apart.");
            }
            if (appointmentSlot.getDate().isAfter(tooSoon)){
                throw new InvalidDonationSchedulingException("Appointments must be at least 56 days apart.");
            }
            if (appointmentSlot.getID() == bloodDonor.getID()){
                throw new InvalidDonationSchedulingException("Appointment is already booked.");
            }

        }

        return bloodDonationAppointment;
    }
}
