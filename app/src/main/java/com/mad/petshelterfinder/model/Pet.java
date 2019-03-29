package com.mad.petshelterfinder.model;

public class Pet {
    private String mPetId;
    private String mName;
    private int mAge;
    private String mBreed;
    private String mGender;
    private String mShelterId;
    private String mSpecies;
    private String mUrl;
    private boolean mMicrochipped;
    private boolean mDesexed;
    private String mImageUrl;
    private String mStatus;

    public Pet(String petId, String name, int age, String breed, String gender, String shelterId, String species, String url, boolean microchipped, boolean desexed, String imageUrl, String status) {
        mPetId = petId;
        mName = name;
        mAge = age;
        mBreed = breed;
        mGender = gender;
        mShelterId = shelterId;
        mSpecies = species;
        mUrl = url;
        mMicrochipped = microchipped;
        mDesexed = desexed;
        mImageUrl = imageUrl;
        mStatus = status;
    }

    public Pet() {
    }

    public String getPetId() {
        return mPetId;
    }

    public void setPetId(String petId) {
        mPetId = petId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public String getBreed() {
        return mBreed;
    }

    public void setBreed(String breed) {
        mBreed = breed;
    }

    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    public String getSpecies() {
        return mSpecies;
    }

    public void setSpecies(String species) {
        mSpecies = species;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public boolean isMicrochipped() {
        return mMicrochipped;
    }

    public void setMicrochipped(boolean microchipped) {
        mMicrochipped = microchipped;
    }

    public boolean isDesexed() {
        return mDesexed;
    }

    public void setDesexed(boolean desexed) {
        mDesexed = desexed;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public String getShelterId() {
        return mShelterId;
    }

    public void setShelterId(String shelterId) {
        mShelterId = shelterId;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}
