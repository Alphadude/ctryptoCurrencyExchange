package com.example.yoma.cryptocurrency.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yoma.cryptocurrency.Models.SpinnerCountryModel;
import com.example.yoma.cryptocurrency.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class CustomSpinner2 extends ArrayAdapter<SpinnerCountryModel> {
    private Context mContext;
    private ArrayList<SpinnerCountryModel> mArray = new ArrayList<>();

    public CustomSpinner2(Context ctx, int txtViewResourceId, ArrayList<SpinnerCountryModel> array) {
        super(ctx, txtViewResourceId, array);
        mContext = ctx;
        this.mArray = array;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    private View getCustomView(int position, View view, ViewGroup container){
        SpinnerCountryModel item = mArray.get(position);
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.custom_spinner2,container,false);
        CircleImageView image = (CircleImageView)rootview.findViewById(R.id.spinner_image);
        image.setImageResource(item.getmImage());

        TextView code = (TextView)rootview.findViewById(R.id.spinner_code);
        code.setText(item.getmCode());

        TextView country = (TextView)rootview.findViewById(R.id.spinner_country);
        country.setText(item.getmCountry());

        return rootview;
    }

    public SpinnerCountryModel getItem(int position){
        return mArray.get(position);
    }
}
