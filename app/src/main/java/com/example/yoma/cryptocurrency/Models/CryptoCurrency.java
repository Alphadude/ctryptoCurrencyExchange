package com.example.yoma.cryptocurrency.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.internal.database.util.TextUtils;

import java.util.HashMap;
import java.util.Map;

public class CryptoCurrency extends BaseModel implements Parcelable {

    private int mFromCoinImage;
    private String id;
    private String mFromCoinCrypto;
    private String mFromCoinCode;
    private int mToCountryImage;
    private String mToCountry;
    private String mToCountryCode;

    public CryptoCurrency() {
    }

    public static CryptoCurrency fromDictionary(Object dictionary){
        return fromDictionary(dictionary,CryptoCurrency.class);
    }

    protected CryptoCurrency(Parcel in) {
        id= in.readString();
        mToCountryImage = in.readInt();
        mFromCoinImage = in.readInt();
        mFromCoinCrypto = in.readString();
        mFromCoinCode = in.readString();
        mToCountry = in.readString();
        mToCountryCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mFromCoinCrypto);
        dest.writeString(mFromCoinCode);
        dest.writeString(mToCountry);
        dest.writeString(mToCountryCode);
        dest.writeString(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CryptoCurrency> CREATOR = new Creator<CryptoCurrency>() {
        @Override
        public CryptoCurrency createFromParcel(Parcel in) {
            return new CryptoCurrency(in);
        }

        @Override
        public CryptoCurrency[] newArray(int size) {
            return new CryptoCurrency[size];
        }
    };


    public int getmFromCoinImage() {
        return mFromCoinImage;
    }

    public void setmFromCoinImage(int mFromCoinImage) {
        this.mFromCoinImage = mFromCoinImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmFromCoinCrypto() {
        return mFromCoinCrypto;
    }

    public void setmFromCoinCrypto(String mFromCoinCrypto) {
        this.mFromCoinCrypto = mFromCoinCrypto;
    }

    public String getmFromCoinCode() {
        return mFromCoinCode;
    }

    public void setmFromCoinCode(String mFromCoinCode) {
        this.mFromCoinCode = mFromCoinCode;
    }

    public int getmToCountryImage() {
        return mToCountryImage;
    }

    public void setmToCountryImage(int mToCountryImage) {
        this.mToCountryImage = mToCountryImage;
    }

    public String getmToCountry() {
        return mToCountry;
    }

    public void setmToCountry(String mToCountry) {
        this.mToCountry = mToCountry;
    }

    public String getmToCountryCode() {
        return mToCountryCode;
    }

    public void setmToCountryCode(String mToCountryCode) {
        this.mToCountryCode = mToCountryCode;
    }

    public void saveToDatabase(final AppCompatActivity activity, final Database database){

        if (database == null)
        {
            Toast.makeText(activity, "Cannot to save to store. Database unavailable.", Toast.LENGTH_SHORT).show();
            return;
        }

        Document CryptoDocument;
        Map<String, Object> properties;

        if (TextUtils.isEmpty(this.getId())){
            //new style
            CryptoDocument  = database.createDocument();
            this.setId(CryptoDocument.getId());
            properties = this.toDictionary();
        }
        else{
            CryptoDocument = database.getDocument(this.getId());
            properties = new HashMap<>();
            properties.putAll(CryptoDocument.getProperties());
            properties.putAll(this.toDictionary());
        }

        try {
            CryptoDocument.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to save to store. Fatal error occurred.", Toast.LENGTH_SHORT).show();
        }
    }
}
