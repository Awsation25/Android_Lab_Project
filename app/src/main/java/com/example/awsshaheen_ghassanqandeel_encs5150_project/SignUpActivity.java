package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Intent toHomeActivity = new Intent(SignUpActivity.this, HomeActivity.class);
        DataBaseHelper dataBaseHelper = DataBaseHelper.getInstance(this);

        LinearLayout parentLinearLayout = new LinearLayout(this);
        parentLinearLayout.setOrientation(LinearLayout.VERTICAL);
        parentLinearLayout.setPadding(16, 16, 16, 16);


        // Email Layout
        LinearLayout emailLinearLayout = new LinearLayout(this);
        emailLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        emailLinearLayout.setPadding(16, 80, 16, 16);

        TextView emailTextView = new TextView(this);
        emailTextView.setText("Email: ");
        emailTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        EditText emailEditText = new EditText(this);
        emailEditText.setHint("Enter Email");
        emailEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        emailEditText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailLinearLayout.addView(emailTextView);
        emailLinearLayout.addView(emailEditText);

// First Name Layout
        LinearLayout firstNameLinearLayout = new LinearLayout(this);
        firstNameLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        firstNameLinearLayout.setPadding(16, 16, 16, 16);

        TextView firstNameTextView = new TextView(this);
        firstNameTextView.setText("First Name: ");
        firstNameTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        EditText firstNameEditText = new EditText(this);
        firstNameEditText.setHint("Enter First Name");
        firstNameEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        firstNameLinearLayout.addView(firstNameTextView);
        firstNameLinearLayout.addView(firstNameEditText);

// Last Name Layout
        LinearLayout lastNameLinearLayout = new LinearLayout(this);
        lastNameLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        lastNameLinearLayout.setPadding(16, 16, 16, 16);

        TextView lastNameTextView = new TextView(this);
        lastNameTextView.setText("Last Name: ");
        lastNameTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        EditText lastNameEditText = new EditText(this);
        lastNameEditText.setHint("Enter Last Name");
        lastNameEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));

        lastNameLinearLayout.addView(lastNameTextView);
        lastNameLinearLayout.addView(lastNameEditText);

// Password Layout
        LinearLayout passwordLinearLayout = new LinearLayout(this);
        passwordLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        passwordLinearLayout.setPadding(16, 16, 16, 16);

        TextView passwordTextView = new TextView(this);
        passwordTextView.setText("Password: ");
        passwordTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        EditText passwordEditText = new EditText(this);
        passwordEditText.setHint("Enter Password");
        passwordEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        passwordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        passwordLinearLayout.addView(passwordTextView);
        passwordLinearLayout.addView(passwordEditText);

// Confirm Password Layout
        LinearLayout confirmPasswordLinearLayout = new LinearLayout(this);
        confirmPasswordLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        confirmPasswordLinearLayout.setPadding(16, 16, 16, 16);

        TextView confirmPasswordTextView = new TextView(this);
        confirmPasswordTextView.setText("Confirm Password: ");
        confirmPasswordTextView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        EditText confirmPasswordEditText = new EditText(this);
        confirmPasswordEditText.setHint("Re-enter Password");
        confirmPasswordEditText.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        confirmPasswordEditText.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        confirmPasswordLinearLayout.addView(confirmPasswordTextView);
        confirmPasswordLinearLayout.addView(confirmPasswordEditText);

// Adding all layouts to a parent layout
        LinearLayout parentLayout = new LinearLayout(this);
        parentLayout.setOrientation(LinearLayout.VERTICAL);
        parentLayout.setPadding(16, 16, 16, 16);

        parentLayout.addView(emailLinearLayout);
        parentLayout.addView(firstNameLinearLayout);
        parentLayout.addView(lastNameLinearLayout);
        parentLayout.addView(passwordLinearLayout);
        parentLayout.addView(confirmPasswordLinearLayout);
        Button signUpButton = new Button(this);
        signUpButton.setText("Sign Up");
        signUpButton.setTextSize(28);
        parentLayout.addView(signUpButton);


        signUpButton.setOnClickListener(new View.OnClickListener() {
            Boolean emailValidity = false;
            Boolean emailExist = false;
            Boolean firstnameValidity = false;
            Boolean lastnameValidity = false;
            Boolean passwordRightLength = false;
            Boolean passwordLowerCase = false;
            Boolean passwordUpperCase = false;
            Boolean passwordExistNumber = false;
            Boolean confirmPasswordValidity = false;

            @Override
            public void onClick(View v) {

                String email = emailEditText.getText().toString();
                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String coPassword = confirmPasswordEditText.getText().toString();

                String checkIfExistEmail = dataBaseHelper.findUserEmail(email);

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                    emailEditText.setError("Please enter a valid email ");
                else
                    emailValidity = true;

                if (checkIfExistEmail != null)
                    emailEditText.setError("this email is already exist");
                else
                    emailExist = true;

                if (firstName.length() < 5 || firstName.length() > 20)
                    firstNameEditText.setError("First Name must be 5-20 characters");
                else
                    firstnameValidity = true;

                if (lastName.length() < 5 || lastName.length() > 20)
                    lastNameEditText.setError("Last Name must be 5-20 characters");
                else
                    lastnameValidity = true;

                if (password.length() < 6 || password.length() > 12)
                    passwordEditText.setError("Password must be 6-12 characters");
                else
                    passwordRightLength = true;

                if (!password.matches(".*[a-z].*"))
                    passwordEditText.setError("Password must contain at least one lowercase letter");
                else
                    passwordLowerCase = true;
                if (!password.matches(".*[A-Z].*"))
                    passwordEditText.setError("Password must contain at least one uppercase letter");
                else
                    passwordUpperCase = true;

                if (!password.matches(".*\\d.*"))
                    passwordEditText.setError("Password must contain at least one number");
                else
                    passwordExistNumber = true;

                if (!password.equals(coPassword))
                    confirmPasswordEditText.setError("Passwords do not match");
                else
                    confirmPasswordValidity = true;

                if(emailValidity
                && emailExist
                && firstnameValidity
                && lastnameValidity
                && passwordRightLength
                && passwordLowerCase
                && passwordUpperCase
                && passwordExistNumber
                && confirmPasswordValidity){
                    //Write on database and go to home page
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFirstName(firstName);
                    newUser.setSecondName(lastName);
                    newUser.setPassword(password);
                    dataBaseHelper.insertUser(newUser);

                    SignUpActivity.this.startActivity(toHomeActivity);

                }

            }
        });

// Set the parent layout as the content view
        setContentView(parentLayout);


    }
}