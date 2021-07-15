package cu.cujae.gilsoft.tykeprof.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import cu.cujae.gilsoft.tykeprof.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         new Handler().postDelayed(() -> {

            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();

        }, 500);
    }
}