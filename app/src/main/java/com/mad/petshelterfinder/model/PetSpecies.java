package com.mad.petshelterfinder.model;

public class PetSpecies {
    private String mName;
    private boolean mIsChecked;

    public PetSpecies(String name, Boolean isChecked) {
        mName = name;
        mIsChecked = isChecked;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
