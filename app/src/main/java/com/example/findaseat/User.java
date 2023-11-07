package com.example.findaseat;

import java.util.ArrayList;
import java.util.Vector;

public class User {
    public String name;
    public String usc_id;
    public String email;
    public String affiliation;
    public String avatarURL;
    public String pwd;
    public ArrayList<TimeSlot> timeSlots;
    public Reservation currentReservation;

    User() {
        name = null;
        usc_id = null;
        email = null;
        avatarURL= null;
        affiliation = null;
        pwd = null;
        timeSlots = new ArrayList<TimeSlot>();
        currentReservation = null;
    }

    User(String e, String id, String n, String a, String p, String u, ArrayList<TimeSlot> timeSlots) {
        name = n;
        usc_id = id;
        email = e;
        affiliation = a;
        pwd = p;
        avatarURL = u;
        timeSlots = new ArrayList<TimeSlot>();
        currentReservation = null;
    }

    public String getName() {
        return name;
    }

    public String getUsc_id() {
        return usc_id;
    }

    public String getEmail() {
        return email;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public String getPwd() {
        return pwd;
    }




}