package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.ui.NewTask.newTaskFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.awsshaheen_ghassanqandeel_encs5150_project.databinding.ActivityHome2Binding;

public class Home2Activity extends AppCompatActivity {
    private EmailSharedPrefManager sharedEmail = EmailSharedPrefManager.getInstance(this);
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHome2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userEmail=intent.getStringExtra("Email");

        binding = ActivityHome2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

      setSupportActionBar(binding.appBarMain2.toolbar);

        binding.appBarMain2.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        DrawerLayout drawer = binding.drawerLayout;

        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_newTask, R.id.nav_allTasks,R.id.nav_completedTasks,R.id.nav_search,R.id.nav_profile)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_logOut) {
                // Log out: Navigate back to MainActivity (Login Page)
                Intent backToLogin = new Intent(Home2Activity.this, MainActivity.class);
                backToLogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                sharedEmail.writeString("Email",null);

                startActivity(backToLogin);
                finish(); // Close Home2Activity
            } else {
                // Let NavigationUI handle other menu items
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
                boolean handled = NavigationUI.onNavDestinationSelected(item, navController);

                if (!handled) {
                    Snackbar.make(binding.getRoot(), "Unhandled Navigation Item", Snackbar.LENGTH_SHORT).show();
                }
            }

            // Close the navigation drawer
            drawer.closeDrawers();
            return true;
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home2, menu);




        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}