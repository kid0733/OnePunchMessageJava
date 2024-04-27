package com.example.onepunchmessenger;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    //Declaring container variables
    Button button ;
    EditText email;
    EditText password;

    TextView signUp;


    //Auth
    FirebaseAuth auth;

    android.app.ProgressDialog progressDialog;

    LottieAnimationView progressAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Animation variable
        progressAnimation = findViewById(R.id.loadingAnimation);

        progressAnimation.setVisibility(View.GONE);
        //setting auth
        auth=FirebaseAuth.getInstance();
        // Check if the user is already logged in

        //setting form fields into the container variables
        button=findViewById(R.id.logButton);
        email= findViewById(R.id.LogEmail);
        password= findViewById(R.id.LogPassword);
        signUp=findViewById(R.id.signupButton);
        //Input Vlaidation variables
        String emailPattern = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+(?:\\.[a-zA-Z0-9-]+)*";

        //Click Listener -> Signup button [click]
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this, Registration.class);
                startActivity(intent);
                finish();
            }
        });

        //Click Listener ->Login Button [click]
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting values from the form input to the
                //Container Variables
                String Email=email.getText().toString();
                String Password=password.getText().toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                //Input validation logic
                if(TextUtils.isEmpty(Email)){

                    Toast.makeText(Login.this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(Password)) {

                    Toast.makeText(Login.this, "Enter the password.", Toast.LENGTH_SHORT).show();
                } else if (!Email.matches(emailPattern)) {

                    email.setError("Please enter a valid email address.");
                } else if (Password.length()<6) {

                    password.setError("Password length must be longer than 6 characters.");
                    Toast.makeText(Login.this, "Password length must be longer than 6 characters.", Toast.LENGTH_SHORT).show();
                } else {
                    progressAnimation.setVisibility(View.VISIBLE);
                    progressAnimation.playAnimation();
                    //attempt sign in
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //If we can complete teh task [login]
                            if(task.isSuccessful()){

                                progressAnimation.setVisibility(View.GONE);
                                try {
                                    //If login successful redirect to main screen
                                    Intent intent=new Intent(Login.this,MainActivity.class);

                                    startActivity(intent);
                                    finish();
                                } catch (Exception e) {
                                    //If error in switching activities
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                //If task is unsuccessful [login]
                                progressAnimation.setVisibility(View.GONE);
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}