package com.example.avishkar.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.avishkar.Models.MessageModel;
import com.example.avishkar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Chatadapter extends RecyclerView.Adapter{
        ArrayList<MessageModel> messageModels;
        Context context;
        String recId;
int SENDER=1;
int RECIEVER =2;
    public Chatadapter(ArrayList<MessageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    public Chatadapter(ArrayList<MessageModel> messageModels, Context context, String recId) {
        this.messageModels = messageModels;
        this.context = context;
        this.recId = recId;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

    if(viewType==SENDER)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_sender,parent,false);
        return new SenderViewHolder(view);
    }
    else{
        View view = LayoutInflater.from(context).inflate(R.layout.sample_reciever,parent,false);
        return new RecieverViewHolder(view);
    }


    }

    @Override
    public int getItemViewType(int position) {
        if(messageModels.get(position).getuId().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER;
        }
        else
        {
            return RECIEVER;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    MessageModel messageModel=messageModels.get(position);

    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            new AlertDialog.Builder(context)
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this message")
                    .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            String senderRoom = FirebaseAuth.getInstance().getUid()+recId;
                            database.getReference().child("chats").child(senderRoom)
                                    .child(messageModel.getMessageid())
                                    .setValue(null);
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();



            return false;
        }
    });


    if(holder.getClass()==SenderViewHolder.class){
        ((SenderViewHolder)holder).Sendermsg.setText(messageModel.getMessage());
    }
    else{
        ((RecieverViewHolder)holder).recieverMsg.setText(messageModel.getMessage());
    }

    }

    @Override
    public int getItemCount() {




        return messageModels.size();
    }

    public class RecieverViewHolder extends RecyclerView.ViewHolder{

        TextView recieverMsg,recieverTime;
        public RecieverViewHolder(@NonNull View itemView) {
            super(itemView);
            recieverMsg =itemView.findViewById(R.id.recievertxt);
            recieverTime =itemView.findViewById(R.id.reciveretime);
        }
    }

    public class SenderViewHolder extends RecyclerView.ViewHolder {
        TextView Sendermsg,sendertime;
        public SenderViewHolder(@NonNull View itemView) {
            super(itemView);
            Sendermsg =itemView.findViewById(R.id.sendertxt);
            sendertime =itemView.findViewById(R.id.sendertime);
        }
    }
}
