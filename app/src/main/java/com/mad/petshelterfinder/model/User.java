package com.mad.petshelterfinder.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String mEmail;
    private String mId;
    private ArrayList<String> mPets;
    private ArrayList<String> mShelters;

    public User() {
    }

    public User(String email, String id, ArrayList<String> pets, ArrayList<String> shelters) {

        mEmail = email;
        mId = id;
        mPets = pets;
        mShelters = shelters;
    }

    public User(String email) {
        mEmail = email;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public ArrayList<String> getPets() {
        return mPets;
    }

    public void setPets(ArrayList<String> pets) {
        mPets = pets;
    }

    public ArrayList<String> getShelters() {
        return mShelters;
    }

    public void setShelters(ArrayList<String> shelters) {
        mShelters = shelters;
    }
}
