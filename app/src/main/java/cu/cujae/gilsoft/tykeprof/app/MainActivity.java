package cu.cujae.gilsoft.tykeprof.app;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ProfessionalRolViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.UserViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.util.DialogHelper;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration myAppBarConfiguration;
    private NavController navController;
    private ImageView imageViewUser;
    private TextView textViewUserFullNameDrawer, textViewUserEmailDrawer;
    private View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        //COMPROBAR SI ES LA PRIMERA VEZ QUE EL USUARIO ENTRA EN LA APP
        if (getSharedPreferences("autenticacion", MODE_PRIVATE).getBoolean("firstLaunch", true)) {
            ToastHelper.showCustomToast(MainActivity.this, "success", getString(R.string.success_aut));
            ProfessionalRolViewModel professionalRolViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProfessionalRolViewModel.class);
            UserHelper.changefirstLaunch(MainActivity.this);
            getSystemConfig();
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O || Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                initConfig();
        }

        setContentView(binding.getRoot());

        //CONFIGURACIÓN DE LA TOOLBAR CON NAVIGATION COMPONENT
        setSupportActionBar(binding.toolbarMainActivity);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        myAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_Fragment, R.id.nav_questionTypeFragment,
                R.id.nav_clueTypeFragment, R.id.nav_giftTypeFragment, R.id.nav_grantFragment, R.id.nav_giftFragment, R.id.nav_professionalRolFragment,
                R.id.nav_insigniaFragment, R.id.nav_rankingFragment, R.id.nav_questionFragment, R.id.nav_strategyFragment)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //ACTUALIZAR INFORMACIÓN EN EL NAVIGATION DRAWER
        view1 = binding.navView.getHeaderView(0);
        imageViewUser = view1.findViewById(R.id.imageViewUserDrawer);
        textViewUserFullNameDrawer = view1.findViewById(R.id.textViewUserFullNameDrawer);
        textViewUserEmailDrawer = view1.findViewById(R.id.textViewUserEmailDrawer);
        imageViewUser = view1.findViewById(R.id.imageViewUserDrawer);

        UserViewModel userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {
            textViewUserEmailDrawer.setText(user.getEmail());
            textViewUserFullNameDrawer.setText(user.getFullName());
            loadUserImage(user.getImage_url());
        });

        //VERIFICAR SI EL USUARIO ESTA CONECTADO A LA RED
        if (!UserHelper.isConnected(MainActivity.this)) {
            Snackbar.make(binding.getRoot(), getResources().getString(R.string.no_connection), Snackbar.LENGTH_INDEFINITE).setAction("Ok", v -> {
            }).show();
            UserHelper.changeConnectedStatus(MainActivity.this, true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit_item:
                DialogHelper.showExitDialog(MainActivity.this);
                return true;
            default:
                return super.onOptionsItemSelected(item) || NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment_container));
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        return NavigationUI.navigateUp(navController, myAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    //CARGA LA IMAGEN DE USUARIO Y LA GUARDA EN CACHÉ
    public void loadUserImage(String url) {
        Picasso picasso = Picasso.get();
        if (!url.isEmpty()) {
            picasso
                    .load(url)
                    .placeholder(R.drawable.ic_account_circle_gray)
                    .error(R.drawable.ic_account_circle_white)
                    .into(imageViewUser);
        } else {
            picasso
                    .load("url")
                    .error(R.drawable.ic_account_circle_white)
                    .into(imageViewUser);
        }
    }

    //INICIAR CONFIGURACIÓN PERSONAL DE USUARIO
    public void initConfig() {
        boolean themeDark = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("DarkTheme", false);
        if (themeDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //OBTENER CONFIGURACIÓN ACTUAL DEL SISTEMA ANDROID
    public void getSystemConfig() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        UiModeManager uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        switch (uiModeManager.getNightMode()) {
            case UiModeManager.MODE_NIGHT_NO:
                editor.putBoolean("DarkTheme", false);
                break;
            case UiModeManager.MODE_NIGHT_YES:
                editor.putBoolean("DarkTheme", true);
                break;
        }
        editor.apply();
    }

        /*    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }*/
}

