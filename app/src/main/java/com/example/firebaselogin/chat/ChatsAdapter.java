package com.example.firebaselogin.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebaselogin.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder> {

    private List<Chats> list;
    private Context context;
    private FirebaseUser firebaseUser;

    public ChatsAdapter(List<Chats> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<Chats> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_LEFT) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_left, parent, false);
            return new ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.row_chat_right, parent, false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("list is :",list.get(position).getTextMessage());
//        Toast.makeText(context, ""+list.get(position).getTextMessage(), Toast.LENGTH_SHORT).show();
        holder.Bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textMessage;
        private LinearLayout layoutText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = (TextView) itemView.findViewById(R.id.messageTv);
            layoutText = itemView.findViewById(R.id.messageLayout);
        }

        void Bind(Chats chats){
            //check chat type..
            layoutText.setVisibility(View.VISIBLE);
            textMessage.setText(chats.getTextMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Toast.makeText(context, ""+list.get(position).getSender().equals(firebaseUser.getUid()), Toast.LENGTH_SHORT).show();

        if (list.get(position).getSender().equals(firebaseUser.getUid())) {
            return MSG_TYPE_RIGHT;
        }
        else
        {
            return MSG_TYPE_LEFT;
        }

    }
}

