package cu.cujae.gilsoft.tykeprof.app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //VERIFICA SI EL USUARIO SE AUTENTICÓ EN LA PLATAFORMA TYKE Y SI ES LA PRIMERA VEZ QUE LO HIZO
        if (getSharedPreferences("autenticacion",MODE_PRIVATE).getBoolean("firstLaunch",true))
        UserHelper.changefirstLaunch(this);

        String token = UserHelper.getToken(MainActivity.this);
        Login login = UserHelper.getUserLogin(MainActivity.this);
        Log.e("JWT en MainActivity ", token);
        Log.e("User Login ", login.getPassword() +"  "+login.getUserCredential());
    }
}