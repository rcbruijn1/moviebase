package rbdb.moviebase.presentation;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import rbdb.moviebase.MainActivity;
import rbdb.moviebase.R;

public class Splash extends AppCompatActivity /*Activity **/{


    /** Called when the activity is first created. */

    // Thread splashTread;

    private static int WELCOME_TIMEOUT = 4000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // StartAnimations();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent welcome = new Intent(Splash.this, MainActivity.class);
                startActivity(welcome);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                finish();
            }
        }, WELCOME_TIMEOUT);
    }
}
