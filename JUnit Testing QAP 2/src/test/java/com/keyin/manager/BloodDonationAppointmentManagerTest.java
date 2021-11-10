package com.keyin.manager;

import com.keyin.domain.Database;
import com.keyin.domain.appointment.AppointmentSlot;
import com.keyin.domain.appointment.BloodDonationAppointment;
import com.keyin.domain.donor.BloodDonor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class BloodDonationAppointmentManagerTest {
    @Mock
    private Database mockDatabase;

    @Test
    public void testBloodDonorTooYoung() {
        BloodDonor bloodDonorTooYoung = new BloodDonor();
        bloodDonorTooYoung.setID(1);
        bloodDonorTooYoung.setFirstName("Blake");
        bloodDonorTooYoung.setLastName("Legge");
        bloodDonorTooYoung.setBloodType("A");
        bloodDonorTooYoung.setDateOfBirth(LocalDate.of(2004, Month.JANUARY, 12));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonorTooYoung
        );

        BloodDonationAppointmentManager bloodDonationAppointmentManager =
                new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Donor is too young."));
        }

    }

    @Test
    public void testBloodDonorTooOld(){
        BloodDonor bloodDonorTooOld = new BloodDonor();
        bloodDonorTooOld.setID(1);
        bloodDonorTooOld.setFirstName("Blake");
        bloodDonorTooOld.setLastName("Legge");
        bloodDonorTooOld.setBloodType("A");
        bloodDonorTooOld.setDateOfBirth(LocalDate.of( 1945, Month.JANUARY, 12));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonorTooOld
        );

        BloodDonationAppointmentManager bloodDonationAppointmentManager =
                new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Donor is too old."));
        }
    }

    @Test
    public void testBloodDonorInvalidType() {
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setID(1);
        bloodDonor.setFirstName("Blake");
        bloodDonor.setLastName("Legge");
        bloodDonor.setBloodType("A");
        bloodDonor.setDateOfBirth(LocalDate.of(1990, Month.JANUARY, 12));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonor
        );

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();

        AppointmentSlot appointmentSlot = new AppointmentSlot();

        appointmentSlot.setID(1);
        appointmentSlot.setLocation("321 Long St. St. John's NL");
        appointmentSlot.setBloodType("B");
        appointmentSlots.add(appointmentSlot);

        Mockito.when(mockDatabase.getAppointmentSlots()).thenReturn(appointmentSlots);

        BloodDonationAppointmentManager bloodDonationAppointmentManager =
                new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Invalid blood type."));
        }

    }

    @Test
    public void testAppointmentTooSoon() {
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setID(1);
        bloodDonor.setFirstName("Blake");
        bloodDonor.setLastName("Legge");
        bloodDonor.setBloodType("A");
        bloodDonor.setDateOfBirth(LocalDate.of(1945, Month.JANUARY, 12));
        bloodDonor.setLastDonationDate(LocalDate.of(2012, Month.JANUARY,23));
        bloodDonor.setNextAppointment(LocalDate.of(2012,Month.JANUARY,25));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonor
        );

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();

        AppointmentSlot appointmentSlot = new AppointmentSlot();

        appointmentSlot.setID(1);
        appointmentSlot.setLocation("321 Long St. St. John's NL");
        appointmentSlot.setBloodType("B");
        appointmentSlot.setDate(LocalDate.of(2013,Month.JANUARY,25));
        appointmentSlots.add(appointmentSlot);


        BloodDonationAppointmentManager bloodDonationAppointmentManager =
                new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Appointments must be at least 56 days apart."));
        }
    }

    @Test
    public void testAppointmentTooLate(){
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setID(1);
        bloodDonor.setFirstName("Blake");
        bloodDonor.setLastName("Legge");
        bloodDonor.setBloodType("A");
        bloodDonor.setDateOfBirth(LocalDate.of(1945, Month.JANUARY, 12));
        bloodDonor.setLastDonationDate(LocalDate.of(2021, Month.JANUARY,25));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonor
        );

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();

        AppointmentSlot appointmentSlot = new AppointmentSlot();

        appointmentSlot.setID(1);
        appointmentSlot.setLocation("321 Long St. St. John's NL");
        appointmentSlot.setBloodType("B");
        appointmentSlot.setDate(LocalDate.of(2022,Month.JANUARY,20));
        appointmentSlots.add(appointmentSlot);

        BloodDonationAppointmentManager bloodDonationAppointmentManager =
                new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Appointments must be no more than 1 year apart."));
        }
    }

    @Test
    public void testAppointmentAlreadyBooked() {
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setID(1);
        bloodDonor.setFirstName("Blake");
        bloodDonor.setLastName("Legge");
        bloodDonor.setBloodType("A");
        bloodDonor.setDateOfBirth(LocalDate.of(1945, Month.JANUARY, 12));
        bloodDonor.setLastDonationDate(LocalDate.of(2010, Month.JANUARY,23));
        bloodDonor.setNextAppointment(LocalDate.of(2011,Month.JANUARY,20));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonor
        );

        ArrayList<AppointmentSlot> appointmentSlots = new ArrayList<AppointmentSlot>();

        AppointmentSlot appointmentSlot = new AppointmentSlot();

        appointmentSlot.setID(1);
        appointmentSlot.setLocation("321 Long St. St. John's NL");
        appointmentSlot.setBloodType("B");
        appointmentSlot.setDate(LocalDate.of(2011,Month.JANUARY,20));
        appointmentSlots.add(appointmentSlot);

        BloodDonationAppointmentManager bloodDonationAppointmentManager = new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        }catch (InvalidDonationSchedulingException e) {
            Assertions.assertTrue(e.getMessage().equalsIgnoreCase("Appointment is already booked."));
        }
    }

    @Test
    public void testFirstTimeDonor(){
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setID(1);
        bloodDonor.setFirstName("Blake");
        bloodDonor.setLastName("Legge");
        bloodDonor.setBloodType("A");
        bloodDonor.setDateOfBirth(LocalDate.of(1945, Month.JANUARY, 12));
        bloodDonor.setNextAppointment(LocalDate.of(2012,Month.JANUARY,25));

        Mockito.when(mockDatabase.getDonor(1)).thenReturn(
                bloodDonor
        );

        BloodDonationAppointmentManager bloodDonationAppointmentManager =
                new BloodDonationAppointmentManager(mockDatabase);

        try {
            BloodDonationAppointment bloodDonationAppointment = bloodDonationAppointmentManager.bookAppointment(1);
        } catch (InvalidDonationSchedulingException e) {

        }
    }

}
