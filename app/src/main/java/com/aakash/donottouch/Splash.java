package com.aakash.donottouch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    ImageView splash;

    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splash=(ImageView) findViewById(R.id.ivLogo);
        Thread td=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(4000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }finally {
                    sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    String value = sharedpreferences.getString("passwordKey", "");
//                    Toast.makeText(Splash.this, value.toString(), Toast.LENGTH_SHORT).show();;
                    if (value.length()>0)
                    {
                        Intent it=new Intent(Splash.this,MainActivity.class);
                        startActivity(it);
                    }else
                    {
                        Intent it=new Intent(Splash.this,SetPin.class);
                        startActivity(it);
                    }

                }
            }
        };td.start();
        Animation myanimation= AnimationUtils.loadAnimation(this,R.anim.fade_in);
        splash.startAnimation(myanimation);

    }
}
