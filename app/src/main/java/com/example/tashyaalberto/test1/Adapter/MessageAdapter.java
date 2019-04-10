package com.example.tashyaalberto.test1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tashyaalberto.test1.MessageActivity;
import com.example.tashyaalberto.test1.Model.Chat;
import com.example.tashyaalberto.test1.Model.User;
import com.example.tashyaalberto.test1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    private Context mContext;
    private List<Chat> mChat;
    private String imageurl;


    FirebaseUser firebaseUser;
    public MessageAdapter(Context mContext, List<Chat> mChat, String imageurl)
    {
        this.mChat=mChat;
        this.imageurl=imageurl;
        this.mContext=mContext;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if(i==MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, viewGroup, false);
            return new MessageAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder viewHolder, int i) {

        Chat chat = mChat.get(i);

        viewHolder.show_message.setText(chat.getMessage());

        if (imageurl.equals("default")) {
            viewHolder.profilepic.setImageResource(R.mipmap.ic_launcher_round);
        } else {
            Glide.with(mContext).load(imageurl).into(viewHolder.profilepic);
        }

        if (i == mChat.size() - 1) {
            if (chat.isIsseen()) {
                viewHolder.txt_seen.setText("Seen");
            } else {
                viewHolder.txt_seen.setText("Delivered");
            }
        } else {
            viewHolder.txt_seen.setVisibility(View.GONE);
        }
    }
    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView show_message;
        public ImageView profilepic;

        public TextView txt_seen;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            show_message=itemView.findViewById(R.id.show_message);
            profilepic=itemView.findViewById(R.id.profilepic);
            txt_seen=itemView.findViewById(R.id.txt_seen);

        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(mChat.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }
    }
}
