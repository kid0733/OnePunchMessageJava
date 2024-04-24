package com.example.onepunchmessenger;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    //Declaring container variables
    Button button ;
    EditText email;
    EditText password;


    //Auth
    FirebaseAuth auth;


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
        getSupportActionBar().hide();
        //setting auth
        auth=FirebaseAuth.getInstance();
        //setting form fields into the container variables
        button=findViewById(R.id.logButton);
        email= findViewById(R.id.LogEmail);
        password= findViewById(R.id.LogPassword);

        //Input Vlaidation variables
        String emailPattern = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+(?:\\.[a-zA-Z0-9-]+)*";

        //Click Listener ->Login Button [click]
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting values from the form input to the
                //Container Variables
                String Email=email.getText().toString();
                String Password=password.getText().toString();
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
                }else{
                    //attempt sign in
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //If we can complete teh task [login]
                            if(task.isSuccessful()){
                                try {
                                    //If login successful redirect to main screen
                                    Intent intent=new Intent(Login.this,MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    //If error in switching activities
                                }catch (Exception e){
                                    Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                //If task is unsuccessful [login]
                            }else{
                                Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}