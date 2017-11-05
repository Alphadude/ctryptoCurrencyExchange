package com.example.yoma.cryptocurrency.Models;

public class SpinnerCoinModel {

    private int mImage;
    private String mFromCode;
    private String mFromCrypto;

    public SpinnerCoinModel(int image, String fromCode, String fromCrypto){
        mImage = image;
        mFromCode = fromCode;
        mFromCrypto = fromCrypto;
    }

    public int getmImage(){
        return mImage;
    }
    public String getmFromCode(){
        return mFromCode;
    }
    public String getmFromCrypto(){
        return mFromCrypto;
    }
}
