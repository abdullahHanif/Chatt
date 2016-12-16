package com.ubits.chatt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Abdullah
 */

public class Splash extends Activity {

    boolean isRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        isRunning = true;
        startSplash();
    }
    private void startSplash() {
        new Thread(new Runnable()
        {
            public void run()
            {
                try
                {
                    Thread.sleep(3000L);
                }
                catch (Exception localException)
                {
                    localException.printStackTrace();
                }
                finally {
                    Splash.this.doFinish();
                }
            }
        }).start();
    }

    private void doFinish() {

        if (this.isRunning) {
            this.isRunning = false;
            startActivity(new Intent(Splash.this,Login.class));
            finish();
        }
    }
}
