package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String userEmail;
    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPrefManager = SharedPrefManager.getInstance(this);
        Intent toHomeActivity = new Intent(MainActivity.this, HomeActivity.class);
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(this);


        // Make the email field auto complete with email from shared pref
        String[] emailsInSharedPref = new String[]{
                sharedPrefManager.readString("Email", "nan")
        };

        ArrayAdapter<String> emailsToComplete = new ArrayAdapter<>(
                this
                , android.R.layout.simple_expandable_list_item_1
                , emailsInSharedPref
        );

        AutoCompleteTextView email = (AutoCompleteTextView) findViewById(R.id.email);
        email.setThreshold(2);
        email.setAdapter(emailsToComplete);
        ///////////////////////////////////////////////////


        EditText password = (EditText) findViewById(R.id.password);
        Button signIn = (Button) findViewById(R.id.signIn);
        CheckBox rememberMe = findViewById(R.id.rememberMe);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordString = password.getText().toString();
                String emailString = email.getText().toString();
                //  There two way to check valid of our email and password
                //1- return all emails and passwords from database -> is easier
                //2- or take our input email and password and check it in data base -> is heavy go to database many times
                //but we should learn to deal with database so will use second approach

                // check nullity of fields
                if (passwordString.isEmpty() || emailString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Email or Password null", Toast.LENGTH_SHORT).show();
                    return;
                }

                userEmail = dataBaseHelper.findUserEmail(emailString);//query
                String passwordFromDataBase =dataBaseHelper.findUserPassword(passwordString);//query


                //the email exist in database
                if (!userEmail.isEmpty()) {
                    //the password is correct
                    if (!passwordFromDataBase.isEmpty()) {
                        //store in shared pref
                        if (rememberMe.isChecked())
                            sharedPrefManager.writeString("Email", userEmail);

                          toHomeActivity.putExtra("Email",userEmail);
                          MainActivity.this.startActivity(toHomeActivity);
                    } else
                        Toast.makeText(MainActivity.this, "Password Uncorrected ", Toast.LENGTH_LONG).show();
                } else
                    Toast.makeText(MainActivity.this, "No account for this email,check email or Sign up", Toast.LENGTH_LONG).show();
            }
        });


        Button signUp =findViewById(R.id.signUp);
        //Suppose we want to go another activity
        Intent toSignUpActivity =new Intent(MainActivity.this,SignUpActivity.class);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.startActivity(toSignUpActivity);
                finish();
            }
        });
    }

}