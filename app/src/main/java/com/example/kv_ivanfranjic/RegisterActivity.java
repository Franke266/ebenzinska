package com.example.kv_ivanfranjic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText enteredemail;
    EditText enteredpassword;
    Button register;
    private FirebaseAuth mAuth;
    private AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        mAuth = FirebaseAuth.getInstance();
        enteredemail = findViewById(R.id.email);
        enteredpassword = findViewById(R.id.password);
        register = findViewById(R.id.register);

        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.email_missing);
        awesomeValidation.addValidation(this, R.id.password, "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", R.string.password_missing);

        register.setOnClickListener(this);
    }

    private void submitForm() {
        String email, password;
        email = enteredemail.getText().toString();
        password = enteredpassword.getText().toString();
        //first validate the form then move ahead
        //if this becomes true that means validation is successfull
        if (awesomeValidation.validate()) {
            mAuth
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),
                                        getString(R.string.registration_complete),
                                        Toast.LENGTH_LONG)
                                        .show();

                                Intent intent
                                        = new Intent(RegisterActivity.this,
                                        FuelActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            } else {

                                Toast.makeText(
                                        getApplicationContext(),
                                        getString(R.string.registration_error),
                                        Toast.LENGTH_LONG)
                                        .show();

                            }
                        }
                    });
        }
    }

        @Override
        public void onClick (View view){
            if (view == register) {
                submitForm();
            }
        }
    }
