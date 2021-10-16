package com.example.avishkar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.avishkar.Adapters.Chatadapter;
import com.example.avishkar.Models.MessageModel;
import com.example.avishkar.databinding.ActivityChatDetailBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.cert.PolicyNode;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.internal.cache.DiskLruCache;

public class CHatDetailActivity extends AppCompatActivity {


    ActivityChatDetailBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        getSupportActionBar().hide();
        auth =FirebaseAuth.getInstance();
        database =FirebaseDatabase.getInstance();




        final String senderId = auth.getUid();
        String recieveId = getIntent().getStringExtra("userId");
        String userName = getIntent().getStringExtra("userName");
        String profilepic = getIntent().getStringExtra("profilepic");
        binding.uname.setText(userName);
        Picasso.get().load(profilepic).placeholder(R.mipmap.ic_avatar_foreground).into(binding.propic);

        binding.backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CHatDetailActivity.this,MainActivitypage.class);
                startActivity(intent);
            }
        });
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final Chatadapter chatadapter = new Chatadapter(messageModels,this,recieveId);
    binding.chatRecyclerview.setAdapter(chatadapter);

        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        binding.chatRecyclerview.setLayoutManager(layoutManager);



        final String senderRoom = senderId+recieveId;
        final String recieverRoom = recieveId + senderId;

        database.getReference().child("chats").child(senderRoom)
                .addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messageModels.clear();
                        for(DataSnapshot snapshot1:  snapshot.getChildren())
                        {
                            MessageModel model=snapshot1.getValue(MessageModel.class);
                            model.setMessage(snapshot1.getKey());
                            messageModels.add(model);
                        }
                        chatadapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {




                    }
                });




         binding.send.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String message =binding.etmeassage.getText().toString();
                 final MessageModel model=new MessageModel(senderId,message);
                 model.setTimestamp(new Date().getTime());
                 binding.etmeassage.setText("");
                 database.getReference().child("chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         database.getReference().child("chats")
                                 .child(recieverRoom)
                                 .push()
                                 .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                             @Override
                             public void onSuccess(Void unused) {

                             }
                         });
                     }
                 });


             }
         });
    }
}