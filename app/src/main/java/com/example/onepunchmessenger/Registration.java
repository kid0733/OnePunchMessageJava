package com.example.onepunchmessenger;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registration extends AppCompatActivity {


    //Container Variables
    TextView login_button;
    EditText rg_email,rg_password,rg_rePassword,rg_username;
    Button rg_signup;
    CircleImageView rg_profile_image;

    //Auth
    FirebaseAuth auth;
    String emailPattern = "[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+(?:\\.[a-zA-Z0-9-]+)*";
    FirebaseDatabase database;
    FirebaseStorage storage;
    LottieAnimationView progressAnimation;
    Uri imageURI;
    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Setting animation
        progressAnimation = findViewById(R.id.loadingAnimation);
        progressAnimation.setVisibility(View.GONE);


        //initiating firebase variables
        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

        //setting variables as form values
        rg_profile_image=findViewById(R.id.profilerg0);
        rg_email=findViewById(R.id.rgEmail);
        rg_password=findViewById(R.id.rgPassword);
        rg_rePassword=findViewById(R.id.rgRePassword);
        rg_username=findViewById(R.id.rgUsername);
        rg_profile_image=findViewById(R.id.profilerg0);
        rg_signup=findViewById(R.id.rgSignupButton);
        //setting login button
        login_button=findViewById(R.id.rgLoginButton);

        //Click Listener ->Login Button [click]
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Registration.this,Login.class);
                startActivity(intent);
                finish();
            }
        });

        rg_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=rg_email.getText().toString();
                String username=rg_username.getText().toString();
                String password=rg_password.getText().toString();
                String rePassword=rg_rePassword.getText().toString();
                String status="Ah, a visitor! Make yourself at home.";
                //input validation
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(email) || TextUtils.isEmpty(password)||TextUtils.isEmpty(rePassword)){
                    Toast.makeText(Registration.this, "Please complete all fields :o", Toast.LENGTH_SHORT).show();
                } else if (!email.matches(emailPattern)) {
                    rg_email.setError("Please enter a valid email :o");
                } else if (password.length()<=6) {
                    rg_password.setError("Password should be 6 or more characters :o ");
                } else if (!password.equals(rePassword)) {
                    rg_password.setError("Passwords dont match :o");
                    rg_rePassword.setError("Passwords dont match :o");
                    //if all inputs are valid
                }else{
                    //create user with email and password
                    auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //if task [create user with email and password] complete
                            if(task.isSuccessful()){
                                //GETTING UNIQUE UID
                                String id=task.getResult().getUser().getUid();
                                //creating unique reference id
                                DatabaseReference reference=database.getReference().child("user").child(id);
                                StorageReference storageReference=storage.getReference().child("Upload").child(id);

                                if(imageURI!= null){
                                    storageReference.putFile(imageURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                            if(task.isSuccessful()){
                                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        imageUri=uri.toString();
                                                        Users users=new Users(id,username,email,password,imageUri,status);
                                                        reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if(task.isSuccessful()){
                                                                    progressAnimation.setVisibility(View.VISIBLE);
                                                                    Intent intent = new Intent(Registration.this,MainActivity.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }else{
                                                                    Toast.makeText(Registration.this, "Error in creating user :(", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        });
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }else{
                                    String status="Ah, a visitor! Make yourself at home.";
                                    imageUri="https://firebasestorage.googleapis.com/v0/b/onepunchmessenger.appspot.com/o/logo.png?alt=media&token=e721b21b-9518-4b8c-bcf8-07f1195d4901";
                                    Users users=new Users(id,username,email,password,imageUri,status);
                                    reference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                progressAnimation.setVisibility(View.VISIBLE);
                                                Intent intent = new Intent(Registration.this,MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }else{
                                                Toast.makeText(Registration.this, "Error in creating user :(", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }

                            }else{
                                Toast.makeText(Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //Attempting to open gallery whe n we click on the add image button
        rg_profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SelectPicture"),10);

            }
        });
    }
    //If we are able to get result [open gallery]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==10){
            //if some picture is provided [data!=null]
            if(data!=null){
                imageURI=data.getData();
                rg_profile_image.setImageURI(imageURI);

            }
        }
    }
}