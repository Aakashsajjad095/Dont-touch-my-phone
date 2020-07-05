package com.aakash.donottouch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import static com.aakash.donottouch.SetPin.MyPREFERENCES;
import static com.aakash.donottouch.SetPin.Password;

public class ResetPin extends AppCompatActivity {
    EditText etOldPin,etSetPin,etConfirmPin;
    Button btnSetPin,btnForgotOldPin;
    SharedPreferences sharedpreferences;
    private AdView mAdView,mAdView2;
    private PublisherInterstitialAd mPublisherInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pin);

        Banners();
        Banners2();

        etOldPin = (EditText)findViewById(R.id.etOldPin);
        etSetPin = (EditText)findViewById(R.id.etSetPin);
        etConfirmPin = (EditText)findViewById(R.id.etConfirmPin);
        btnSetPin = (Button)findViewById(R.id.btnSetPin);
        btnForgotOldPin = (Button)findViewById(R.id.btnForgotOldPin);

        //intertiate
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-3655676019061953/6314735211");
        mPublisherInterstitialAd.loadAd(new PublisherAdRequest.Builder().build());

        mPublisherInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                mPublisherInterstitialAd.show(); // add this line to show.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
//                requestNewInterstitial(); // never-ending loop of ads, avoid!!
            }
        });






        btnForgotOldPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                startActivity(new Intent(ResetPin.this, ForgotPin.class));
                finish();
            }
        });






        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String password = sharedpreferences.getString("passwordKey", "");
        btnSetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

                String oldPin = etOldPin.getText().toString();
                String pin = etSetPin.getText().toString();
                String confirmPin = etConfirmPin.getText().toString();
                if(TextUtils.isEmpty(oldPin) || oldPin.length()<4) {
                    etOldPin.setError("Required! Minimum length 4 digit");
                    etOldPin.requestFocus();
                    return;
                }

                else if(TextUtils.isEmpty(pin)|| pin.length()<4) {
                    etSetPin.setError("Required! Minimum length 4 digit");
                    etSetPin.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(confirmPin) || confirmPin.length()<4) {
                    etConfirmPin.setError("Required! Minimum length 4 digit");
                    etConfirmPin.requestFocus();
                    return;
                }
                if(oldPin.equals(password)){
                    if(pin.equals(confirmPin)){
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString(Password, confirmPin);
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "Password Reset Successful", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(ResetPin.this, MainActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Password Do Not Match", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    etOldPin.getText().clear();
                    etOldPin.setError("Wrong Pin");
                    etOldPin.requestFocus();
                }
            }
        });


    }
    public void Banners()
    {
        MobileAds.initialize(this,
                "ca-app-pub-3655676019061953/9210994315");
        mAdView = findViewById(R.id.adView1);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void Banners2()
    {
        MobileAds.initialize(this,
                "ca-app-pub-3655676019061953/2262442587");
        mAdView2 =(AdView) findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);

//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
//        mAdView = (AdView) findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);
        // Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();

    }

}
