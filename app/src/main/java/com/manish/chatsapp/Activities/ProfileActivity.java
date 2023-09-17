package com.manish.chatsapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manish.chatsapp.Models.User;
import com.manish.chatsapp.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    FirebaseDatabase database;
    User user;
    TextView user_name;
    TextView user_phone_number;
    CircleImageView user_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        database = FirebaseDatabase.getInstance();
        user_name = findViewById(R.id.name_text_view);
        user_phone_number = findViewById(R.id.number_text_view);
        user_image = findViewById(R.id.profile_image);

        database.getReference().child("users").child(FirebaseAuth.getInstance().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        user = snapshot.getValue(User.class);
                        if (user!=null){
                            user_name.setText(user.getName());
                            user_phone_number.setText(user.getPhoneNumber());
                            Glide.with(getApplicationContext()).load(user.getProfileImage())
                                    .placeholder(R.drawable.avatar)
                                    .into(user_image);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }
}