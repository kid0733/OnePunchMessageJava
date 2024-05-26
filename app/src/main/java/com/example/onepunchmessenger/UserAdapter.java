package com.example.onepunchmessenger;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {
    MainActivity mainActivity;
    ArrayList<Users> userArrayList;
    public UserAdapter(MainActivity mainActivity, ArrayList<Users> userArrayList) {
        this.mainActivity=mainActivity;
        this.userArrayList=userArrayList;

    }

    @NonNull
    @Override
    public UserAdapter.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //creating a new View in the user item template
        View view= LayoutInflater.from(mainActivity).inflate(R.layout.user_item,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.viewholder holder, int position) {

        //setting the user name and placing it in the ui component
        Users users=userArrayList.get(position);
        holder.userName.setText(users.username);
        holder.status.setText(users.status);
        Picasso.get().load(users.profileImg).into(holder.userImg);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mainActivity, chatWin.class);
                intent.putExtra("name_user",users.getUsername());
                intent.putExtra("receiver_img",users.getProfileImg());
                intent.putExtra("uid",users.getUserId());
                mainActivity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {

        return userArrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        CircleImageView userImg;
        TextView userName;
        TextView status;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            userImg=itemView.findViewById(R.id.userImg);
            userName=itemView.findViewById(R.id.userName);
            status=itemView.findViewById(R.id.status);

        }
    }
}
