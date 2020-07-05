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

public class SetPin extends AppCompatActivity {
    EditText etSetPin,etConfirmPin,etEmail;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";

    SharedPreferences sharedpreferences;
    Button btnSetPin;
    private AdView mAdView,mAdView2;
    private PublisherInterstitialAd mPublisherInterstitialAd;


    @Override
    public void onResume() {
        super.onResume();
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        String email = preferences.getString("Email", "");
//        Toast.makeText(getApplicationContext(), "near"+email, Toast.LENGTH_SHORT).show();
//        mSensorManager.registerListener(this, mProximity,
//                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pin);
        Banners();
        Banners2();
        etSetPin = (EditText) findViewById(R.id.etSetPin);
        etConfirmPin = (EditText)findViewById(R.id.etConfirmPin);
        btnSetPin = (Button)findViewById(R.id.btnSetPin);
       // etEmail = (EditText)findViewById(R.id.etEmail);

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
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//        String email = sharedpreferences.getString("emailKey","");
//        if(email.length()>0){
//            startActivity(new Intent(SetPin.this, MainActivity.class));
//        }


//        Toast.makeText(getApplicationContext(), "email:"+email, Toast.LENGTH_SHORT).show();
//        SharedPreferences.Editor editor=sharedpreferences.edit();
//        boolean  firstTime=sharedpreferences.getBoolean("first", true);
//        if(firstTime) {
//            editor.putBoolean("first",false);
//            editor.commit();
////            startActivity(new Intent(SetPin.this, MainActivity.class));
////            finish();
//        }else {
//            startActivity(new Intent(SetPin.this, MainActivity.class));
////            finish();
//        }
//        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//        mProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);


//        final MediaPlayer mPlayer = MediaPlayer.create(SetPin.this, R.raw.siren);
//        AudioManager audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
//        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
        btnSetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mPublisherInterstitialAd.isLoaded()) {
                    mPublisherInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
                String pin = etSetPin.getText().toString();
                String confirmPin = etConfirmPin.getText().toString();
//                String email = etEmail.getText().toString();
//                if(TextUtils.isEmpty(email)) {
//                    etEmail.setError("Required");
//                    etEmail.requestFocus();
//                    return;
//                }

                 if(TextUtils.isEmpty(pin) || pin.length()<4) {
                    etSetPin.setError("Required! Minimum length 4 digit");
                    etSetPin.requestFocus();
                    return;
                }
                else if(TextUtils.isEmpty(confirmPin) || confirmPin.length()<4) {
                    etConfirmPin.setError("Required! Minimum length 4 digit");
                    etConfirmPin.requestFocus();
                    return;
                }
                if(pin.equals(confirmPin)){
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    //editor.putString(Email, email);
                    editor.putString(Password, confirmPin);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Password Set", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SetPin.this, MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Password Does Not Match", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

//


//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
//            if (event.values[0] == 0) {
//                //near
//                Toast.makeText(getApplicationContext(), "near", Toast.LENGTH_SHORT).show();
//            } else {
//                //far
//                Toast.makeText(getApplicationContext(), "Sensor Kaam Kr Raha Hai BC", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//    @Override
//    public void onAccuracyChanged(Sensor sensor, int accuracy) {
//
//    }

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
