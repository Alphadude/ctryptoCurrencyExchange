package com.example.yoma.cryptocurrency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        Thread t =new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent i =new Intent(splashscreen.this,Home.class);
                    startActivity(i);
                }
            }
        };
        t.start();
    }
    @Override
    public void onPause(){
        super.onPause();
        finish();
    }
    }

