package com.example.findaseat;

import java.util.ArrayList;

public class User {
    public String name;
    public String usc_id;
    public String email;
    public String affiliation;
    public String avatarURL;
    public String pwd;
    public boolean loggedIn;
    public ArrayList<TimeSlot> timeSlots;
    //public ReservationFragment currentReservation;

    User() {
        name = null;
        usc_id = null;
        email = null;
        avatarURL= null;
        affiliation = null;
        pwd = null;
        timeSlots = new ArrayList<TimeSlot>();
        //currentReservation = null;
    }

    User(String e, String id, String n, String a, String p, ArrayList<TimeSlot> ts) {
        name = n;
        usc_id = id;
        email = e;
        affiliation = a;
        pwd = p;
        timeSlots = ts;
        //currentReservation = null;
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


    public String login(String user, String pwd) {
        return "User logged in.";
    }

    public boolean multipleReservations(String user) {
        return false;
    }

    public boolean deleteReservation(String u, String d, String q, String l) {return true; }

    public boolean showReservationHistory(String u) {return true; }

}