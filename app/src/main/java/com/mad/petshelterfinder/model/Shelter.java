package com.mad.petshelterfinder.model;

public class Shelter {

    private String mShelterId;
    private String mName;
    private String mAddress;
    private Long mPostcode;
    private String mSuburb;
    private String mState;
    private String mWebsite;
    private String mType;
    private String mMarkerId;
    private String mPhone;
    private String mEmail;

    public Shelter(String shelterId, String name, String address, Long postcode, String suburb, String state, String website, String type, String phone, String email) {
        mShelterId = shelterId;
        mName = name;
        mAddress = address;
        mPostcode = postcode;
        mSuburb = suburb;
        mState = state;
        mWebsite = website;
        mType = type;
        mPhone = phone;
        mEmail = email;
    }

    public Shelter() {

    }

    public String getShelterId() {
        return mShelterId;
    }

    public void setShelterId(String shelterId) {
        mShelterId = shelterId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public Long getPostcode() {
        return mPostcode;
    }

    public void setPostcode(Long postcode) {
        mPostcode = postcode;
    }

    public String getSuburb() {
        return mSuburb;
    }

    public void setSuburb(String suburb) {
        mSuburb = suburb;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getFullAddress() {
        return mAddress + " " + mSuburb + " " + mState + " " + mPostcode;
    }

    public String getMarkerId() {
        return mMarkerId;
    }

    public void setMarkerId(String markerId) {
        mMarkerId = markerId;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
