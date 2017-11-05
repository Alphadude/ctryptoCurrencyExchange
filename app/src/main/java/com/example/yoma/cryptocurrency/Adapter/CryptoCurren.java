package com.example.yoma.cryptocurrency.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.example.yoma.cryptocurrency.DisplayXchange;
import com.example.yoma.cryptocurrency.Helper.DataHelper;
import com.example.yoma.cryptocurrency.Models.CryptoCurrency;
import com.example.yoma.cryptocurrency.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CryptoCurren extends RecyclerView.Adapter<CryptoCurren.MyViewHolder> {

    private Context mContext;
    ArrayList<CryptoCurrency> array= new ArrayList<>();
    private String mFromCode;
    private String mToCode;

    public CryptoCurren(Context mContext, ArrayList<CryptoCurrency> array) {
        this.mContext = mContext;
        this.array = array;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.custom_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final CryptoCurrency item = array.get(position);
        holder.mFromImage.setImageResource(item.getmFromCoinImage());
        holder.mToImage.setImageResource(item.getmToCountryImage());
        holder.mToImage.setTag(item.getmToCountryImage());
        holder.mFromImage.setTag(item.getmFromCoinImage());
        holder.mFromText.setText(item.getmFromCoinCrypto());
        holder.mToText.setText(item.getmToCountry());
        holder.mFromCode.setText(item.getmFromCoinCode());
        holder.mToCode.setText(item.getmToCountryCode());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFromCode = item.getmFromCoinCode();
                mToCode = item.getmToCountryCode();
                Bundle bundle = new Bundle();
                bundle.putString("fromCode",mFromCode);
                bundle.putString("toCode",mToCode);
                Intent intent = new Intent(mContext,DisplayXchange.class);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return array.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mFromImage, mToImage;
        private TextView mFromText, mToText, mFromCode, mToCode;
        private ImageButton delete;
        private CardView mCardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            mFromImage = (CircleImageView)itemView.findViewById(R.id.first_flag);
            mToImage = (CircleImageView)itemView.findViewById(R.id.second_flag);
            mFromText = (TextView)itemView.findViewById(R.id.first_country);
            mToText = (TextView)itemView.findViewById(R.id.second_country);
            mFromCode = (TextView)itemView.findViewById(R.id.first_code);
            mToCode = (TextView)itemView.findViewById(R.id.second_code);
            delete = (ImageButton)itemView.findViewById(R.id.delete1);
            mCardView = (CardView)itemView.findViewById(R.id.cardView);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setMessage("Delete this item");
                    builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked the "Delete" button, so delete the pet.
                            Database database = DataHelper.getDatabase(mContext,DataHelper.CRYPTO_DATABASE);
                            if (database != null) {
                                Document document = database.getDocument(array.get(getAdapterPosition()).getId());
                                try {
                                    document.delete();
                                    CryptoCurren.this.array.remove(getAdapterPosition());
                                    CryptoCurren.this.notifyDataSetChanged();
                                    Toast.makeText(mContext,"Deleted",Toast.LENGTH_SHORT).show();
                                } catch (CouchbaseLiteException e) {
                                    e.printStackTrace();
                                    Toast.makeText(mContext,"Failed to remove ",Toast.LENGTH_SHORT).show();
                                }

                            }


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User clicked the "Cancel" button, so dismiss the dialog
                            // and continue editing the pet.
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                        }
                    });

                    // Create and show the AlertDialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            });

        }
    }
}
