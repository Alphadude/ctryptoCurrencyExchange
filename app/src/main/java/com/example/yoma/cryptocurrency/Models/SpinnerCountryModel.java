package com.example.yoma.cryptocurrency.Models;

public class SpinnerCountryModel {

    private int mImage;
    private String mCode;
    private String mCountry;

    public SpinnerCountryModel(int image, String code, String country){
        this.mImage = image;
        this.mCode = code;
        this.mCountry = country;
    }

    public int getmImage(){
        return mImage;
    }
    public String getmCode(){
        return mCode;
    }
    public String getmCountry(){
        return mCountry;
    }
}
