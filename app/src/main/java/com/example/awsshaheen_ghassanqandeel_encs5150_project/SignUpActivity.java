package com.example.awsshaheen_ghassanqandeel_encs5150_project;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

// Set the parent layout as the content view
        setContentView(parentLayout);


    }
}