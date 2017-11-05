package com.example.yoma.cryptocurrency;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.yoma.cryptocurrency.Adapter.CryptoCurren;
import com.example.yoma.cryptocurrency.Adapter.CustomSpinner;
import com.example.yoma.cryptocurrency.Adapter.CustomSpinner2;
import com.example.yoma.cryptocurrency.Helper.DataHelper;
import com.example.yoma.cryptocurrency.Models.CryptoCurrency;
import com.example.yoma.cryptocurrency.Models.SpinnerCoinModel;
import com.example.yoma.cryptocurrency.Models.SpinnerCountryModel;
import com.example.yoma.cryptocurrency.NetworkUtil.NetworkCall;

import java.util.ArrayList;
import java.util.HashMap;

public class Home extends AppCompatActivity  implements android.app.LoaderManager.LoaderCallbacks<HashMap<String,Double>>{

    private static  String crypto;
    private static String cryptoCode;
    private static String currency;
    private static int cryptoImg;
    ArrayList<SpinnerCoinModel> model;
    ArrayList<SpinnerCountryModel> model2;
    private static int currencyImg;
    private String to_country;
    private static final int ID = 1;
    private static HashMap<String,Double> hashMap;
    private TextView valText,empty;
    private ProgressBar pbar;
    private static ArrayList<CryptoCurrency> myList = new ArrayList<>();
    private RecyclerView recyclerView;
    CryptoCurren adapter;
    Database database;
    LinearLayout lin;
    private boolean pbarVisible = false;



    @Override
    public android.content.Loader<HashMap<String, Double>> onCreateLoader(int id, Bundle args) {
        return new DataLoader(this);
    }

    @Override
    public void onLoadFinished(android.content.Loader<HashMap<String, Double>> loader, HashMap<String, Double> data) {
        lin.setVisibility(View.VISIBLE);
        hashMap = data;
        Log.e(ConversionScreen.class.getSimpleName(),"onLoadFinished() method: Hashmap: "+hashMap);
        Double value = hashMap.get(currency);

        if(pbarVisible == true){
            pbar.setVisibility(View.GONE);
            pbarVisible = false;
        }
        valText.setText(String.valueOf(value));
    }

    @Override
    public void onLoaderReset(android.content.Loader<HashMap<String, Double>> loader) {
        hashMap = null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        empty = (TextView)findViewById(R.id.tvEmpty);


        getAllConversions();
        database = DataHelper.getDatabase(Home.this, DataHelper.CRYPTO_DATABASE);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               openDialog();
            }
        });
    }

    private void getAllConversions() {
        recyclerView = (RecyclerView) findViewById(R.id.myCard_Rv);
        recyclerView.setHasFixedSize(true);


        LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        if (database == null){
            return;
        }
        empty.setVisibility(View.GONE);



        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS); //ALL_DOCS by id, BY_SEQUENCE by last modified

        try {
            QueryEnumerator result = query.run();
            myList = new ArrayList<>();

            for (; result.hasNext(); ) {
                QueryRow row = result.next();
                CryptoCurrency customer = CryptoCurrency.fromDictionary(row.getDocument().getProperties());
                myList.add(customer);
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(Home.this, "Get customers info failed", Toast.LENGTH_SHORT).show();
        }

        adapter = new CryptoCurren(Home.this,myList);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<SpinnerCountryModel> getItems2() {
        model2 = new ArrayList<>();

        model2.add(new SpinnerCountryModel(R.drawable.ars, "ARS", "Argentina Peso"));
        model2.add(new SpinnerCountryModel(R.drawable.aud, "AUD", "Australia Dollar"));
        model2.add(new SpinnerCountryModel(R.drawable.cad, "CAD", "Canada Dollar"));
        model2.add(new SpinnerCountryModel(R.drawable.cny, "CNY", "China Yuan"));
        model2.add(new SpinnerCountryModel(R.drawable.dkr, "DKR", "Denmark Krone"));
        model2.add(new SpinnerCountryModel(R.drawable.egp, "EGP", "Egypt Pound"));
        model2.add(new SpinnerCountryModel(R.drawable.eur, "EUR", "European EURO"));
        model2.add(new SpinnerCountryModel(R.drawable.ghs, "GHS", "Ghana Cedi"));
        model2.add(new SpinnerCountryModel(R.drawable.hkd, "HKD", "Honk Kong Dollar"));
        model2.add(new SpinnerCountryModel(R.drawable.ils, "ILS", "Israel Shekel"));
        model2.add(new SpinnerCountryModel(R.drawable.inr, "INR", "India Rupee"));
        model2.add(new SpinnerCountryModel(R.drawable.idr, "IDR", "Indonesian Rupia"));
        model2.add(new SpinnerCountryModel(R.drawable.jpy, "JPY", "Japan Yen"));
        model2.add(new SpinnerCountryModel(R.drawable.ngn, "NGN", "Nigeria Naira"));
        model2.add(new SpinnerCountryModel(R.drawable.rub, "RUB", "Russia Ruble"));
        model2.add(new SpinnerCountryModel(R.drawable.zar, "ZAR", "South Africa Rand"));
        model2.add(new SpinnerCountryModel(R.drawable.chf, "CHF", "Swiss Franc"));
        model2.add(new SpinnerCountryModel(R.drawable.sar, "SAR", "Saudi Arabia Riyal"));
        model2.add(new SpinnerCountryModel(R.drawable.gbp, "GBP", "Great Britain Pounda"));
        model2.add(new SpinnerCountryModel(R.drawable.usd, "USD", "United States Dollar"));

        return model2;
    }

    private ArrayList<SpinnerCoinModel> getItems1() {
        model = new ArrayList<>();

        model.add(new SpinnerCoinModel(R.drawable.btc, "BTC","Bitcoin"));
        model.add(new SpinnerCoinModel(R.drawable.eth, "ETH","Etherum"));

        return model;
    }

    private void openDialog() {
        final View customDialog = Home.this.getLayoutInflater().inflate(R.layout.custom_dialog, null);

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(Home.this, R.style.MyDialogTheme);
        dialogBuilder.setView(customDialog);

        initSpiners(customDialog);

        lin = (LinearLayout)customDialog.findViewById(R.id.linlay1);
        pbar = (ProgressBar)customDialog.findViewById(R.id.converting);
        valText = (TextView)customDialog. findViewById(R.id.value);

        final Button convert = (Button) customDialog.findViewById(R.id.convert);
        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCoin();
            }
        });


        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                CryptoCurrency cryptoModel = new CryptoCurrency();
                cryptoModel.setmFromCoinCode(cryptoCode);
                cryptoModel.setmFromCoinCrypto(crypto);
                cryptoModel.setmFromCoinImage(cryptoImg);
                cryptoModel.setmToCountryCode(currency);
                cryptoModel.setmToCountry(to_country);
                cryptoModel.setmToCountryImage(currencyImg);

                Database database = DataHelper.getDatabase(getApplicationContext(), DataHelper.CRYPTO_DATABASE);
                cryptoModel.saveToDatabase(Home.this,database);

               getAllConversions();

            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogBuilder.setCancelable(false);
        AlertDialog addCategoryDialog = dialogBuilder.create();
        addCategoryDialog.setTitle("Add new Item");
        addCategoryDialog.show();


}

    private void initSpiners(View customDialog) {
        ArrayList<SpinnerCoinModel> items1 = getItems1();
        final Spinner from_spinner = (Spinner)customDialog.findViewById(R.id.from_spinner);
        from_spinner.setPrompt("Choose coin");
        from_spinner.setAdapter(new CustomSpinner(this,R.layout.custom_spinner2, items1));

        from_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCoinModel selection = (SpinnerCoinModel) from_spinner.getSelectedItem();
                cryptoImg =selection.getmImage();
                cryptoCode = selection.getmFromCode();
                crypto = selection.getmFromCrypto();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayList<SpinnerCountryModel> items2 = getItems2();
        final Spinner to_spinner = (Spinner)customDialog.findViewById(R.id.to_spinner);
        to_spinner.setAdapter(new CustomSpinner2(this,R.layout.custom_spinner2,items2));
        to_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerCountryModel selection2 = (SpinnerCountryModel) to_spinner.getSelectedItem();
                currencyImg = selection2.getmImage();
                currency = selection2.getmCode();
                to_country= selection2.getmCountry();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void convertCoin() {

        pbar.setVisibility(View.VISIBLE);
        pbarVisible = true;
        getLoaderManager().initLoader(ID,null,this);
    }

    private static class DataLoader extends android.content.AsyncTaskLoader<HashMap<String,Double>> {

        public DataLoader(Context context){
            super(context);
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            forceLoad();
        }

        @Override
        public HashMap<String, Double> loadInBackground() {
            String url = "https://min-api.cryptocompare.com/data/price?fsym="+cryptoCode+"&tsyms="+currency;
            HashMap<String,Double> hashMap = NetworkCall.getResponse(url,currency);
            Log.e(ConversionScreen.class.getSimpleName(),"LoadInBackground() method: Hashmap: "+hashMap);
            return hashMap;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllConversions();
    }
}
