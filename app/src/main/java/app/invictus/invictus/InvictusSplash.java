package app.invictus.invictus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import app.invictus.R;

public class InvictusSplash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        init();
    }

    public void startApplication() {

        Intent intent = new Intent(this, InvictusMain.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    private void init() {

        new CountDownTimer(3000, 1000) {

            public void onTick(long millisUntilFinished) {            }

            public void onFinish() {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
                progressBar.setVisibility(View.INVISIBLE);
                startApplication();
            }
        }.start();
    }
}
