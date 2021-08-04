package cu.cujae.gilsoft.tykeprof.app;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import cu.cujae.gilsoft.tykeprof.R;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.ProfessionalRolViewModel;
import cu.cujae.gilsoft.tykeprof.app.viewmodel.UserViewModel;
import cu.cujae.gilsoft.tykeprof.databinding.ActivityMainBinding;
import cu.cujae.gilsoft.tykeprof.service.User_Service;
import cu.cujae.gilsoft.tykeprof.util.Login;
import cu.cujae.gilsoft.tykeprof.util.RetrofitClient;
import cu.cujae.gilsoft.tykeprof.util.ToastHelper;
import cu.cujae.gilsoft.tykeprof.util.UserHelper;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppBarConfiguration myAppBarConfiguration;
    User_Service user_service = RetrofitClient.getRetrofit().create(User_Service.class);
    NavController navController;
    ImageView imageViewUser;
    TextView textViewUserFullNameDrawer, textViewUserEmailDrawer;
    View view1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String token = UserHelper.getToken(MainActivity.this);
        Login login = UserHelper.getUserLogin(MainActivity.this);
        Log.e("JWT en MainActivity ", token);
        Log.e("User Login ", login.getPassword() + "  " + login.getUserCredential());

        setSupportActionBar(binding.toolbarMainActivity);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        myAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home_Fragment, R.id.nav_questionTypeFragment,
                R.id.nav_clueTypeFragment, R.id.nav_giftTypeFragment, R.id.nav_grantFragment, R.id.nav_giftFragment, R.id.nav_professionalRolFragment,
                R.id.nav_insigniaFragment, R.id.nav_rankingFragment)
                .setOpenableLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        NavigationUI.setupActionBarWithNavController(this, navController, myAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        if (getSharedPreferences("autenticacion", MODE_PRIVATE).getBoolean("firstLaunch", true)) {
            ProfessionalRolViewModel professionalRolViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ProfessionalRolViewModel.class);
            ToastHelper.showCustomToast(MainActivity.this, "success", getString(R.string.success_aut));
            UserHelper.changefirstLaunch(this);
        }

        view1 = binding.navView.getHeaderView(0);
        imageViewUser = view1.findViewById(R.id.imageViewUserDrawer);
        textViewUserFullNameDrawer = view1.findViewById(R.id.textViewUserFullNameDrawer);
        textViewUserEmailDrawer = view1.findViewById(R.id.textViewUserEmailDrawer);
        imageViewUser = view1.findViewById(R.id.imageViewUserDrawer);

        UserViewModel userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(UserViewModel.class);
        userViewModel.getUser().observe(this, user -> {
            try {
                textViewUserEmailDrawer.setText(user.getEmail());
                textViewUserFullNameDrawer.setText(user.getFullName());
                loadUserImage(user.getImage_url());
            } catch (Exception e) {
                textViewUserEmailDrawer.setText(getString(R.string.email));
                textViewUserFullNameDrawer.setText(getString(R.string.user));
                loadUserImage("URL");
            }
        });
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
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(R.string.exit_confirm);
                dialog.setMessage(R.string.exit_confirm_description);
                dialog.setPositiveButton(R.string.yes, (dialog12, which) -> {
                    finish();
                    // finishAffinity();
                });
                dialog.setNegativeButton("No", (dialog13, which) -> dialog13.dismiss());
                dialog.setNeutralButton(R.string.cancel, (dialog1, which) -> dialog1.dismiss());
                dialog.show();
                return true;
            default:
                NavigationUI.onNavDestinationSelected(item, Navigation.findNavController(this, R.id.nav_host_fragment_container));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        return NavigationUI.navigateUp(navController, myAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void loadUserImage(String url) {
        Picasso picasso = Picasso.get();
        //picasso.setIndicatorsEnabled(true);
        if (!url.isEmpty()) {
            picasso
                    .load(url)
                    .placeholder(R.drawable.ic_account_circle_gray)
                    // .resize(80,80)
                    //.centerCrop(Gravity.CENTER_VERTICAL)
                    .error(R.drawable.ic_account_circle_white)
                    .into(imageViewUser);
        } else {
            picasso
                    .load("url")
                    .error(R.drawable.ic_account_circle_white)
                    .into(imageViewUser);
        }
    }

    /*    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main);
    }*/
}

