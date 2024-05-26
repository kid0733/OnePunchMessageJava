package com.example.onepunchmessenger;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWin extends AppCompatActivity {


    String receiverImg,receiverUID,receiverName,senderUID;
    CircleImageView profile;
    TextView receiver_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //setting receiver data for the chat window
        receiverName=getIntent().getStringExtra("name_user");
        receiverImg=getIntent().getStringExtra("receiver_img");
        receiverUID=getIntent().getStringExtra("uid");
        //Setting UI values
        profile=findViewById(R.id.profilerg0);
        receiver_name=findViewById(R.id.receiver_name);
        Picasso.get().load(receiverImg).into(profile);
        receiver_name.setText(""+receiverName);

    }
}