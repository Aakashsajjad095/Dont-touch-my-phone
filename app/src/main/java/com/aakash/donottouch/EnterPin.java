package com.aakash.donottouch;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherInterstitialAd;

import static com.aakash.donottouch.SetPin.MyPREFERENCES;

public class EnterPin extends AppCompatActivity {
    EditText etEnterPin;
    SharedPreferences sharedpreferences;
    private AdView mAdView,mAdView2;
    private PublisherInterstitialAd mPublisherInterstitialAd;
    View view;


    //Disable Back Key
    @Override
    public void onBackPressed() {
        //return nothing
        return;
    }

    //Disable Tasks Key
    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);

        activityManager.moveTaskToFront(getTaskId(), 0);
    }

    //Disable Volume Key
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            // Do your thing

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);
        Banners();
        Banners2();


        //intertiate
        mPublisherInterstitialAd = new PublisherInterstitialAd(this);
        mPublisherInterstitialAd.setAdUnitId("ca-app-pub-3655676019061953/1687727516");
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

        /*final Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 100, 1000};
        vb.vibrate(pattern, 0);*/

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String password = sharedpreferences.getString("passwordKey", "");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        final MediaPlayer mPlayer = MediaPlayer.create(EnterPin.this, R.raw.siren);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
        mPlayer.start();
        mPlayer.setLooping(true);
        etEnterPin = (EditText) findViewById(R.id.etEnterPin);
        etEnterPin.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String pin = etEnterPin.getText().toString();
                    if (pin.equals(password)) {
                        mPlayer.stop();
                        //vb.cancel();
                        startActivity(new Intent(EnterPin.this, MainActivity.class));
                        finish();
                        handled = true;
                    } else {
                        etEnterPin.getText().clear();
                        etEnterPin.setError("Wrong Pin!");
                        etEnterPin.requestFocus();


                    }

                }
                return handled;
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
