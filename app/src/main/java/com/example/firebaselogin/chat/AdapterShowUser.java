package com.example.firebaselogin.chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaselogin.R;

import java.util.List;

public class AdapterShowUser extends RecyclerView.Adapter<AdapterShowUser.Myholder> {

    Context context;
    List<ModelShowUser> list;

    public AdapterShowUser(Context context, List<ModelShowUser> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public AdapterShowUser.Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Myholder(LayoutInflater.from(context).inflate(R.layout.row_chatlist,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterShowUser.Myholder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context,ChatActivity.class);
                i.putExtra("receiverName",list.get(position).getName());
                i.putExtra("receiverPhone",list.get(position).getPhoneNo());
                i.putExtra("receiverID",list.get(position).getReceiverID());
                i.putExtra("senderID",list.get(position).getSenderID());
                context.startActivity(i);
            }
        });

        holder.name.setText(list.get(position).getName());

        holder.phone.setText(list.get(position).getPhoneNo());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Myholder extends RecyclerView.ViewHolder {

        TextView name , phone;

        public Myholder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tvName);
            phone = itemView.findViewById(R.id.tvPhone);
        }
    }
}
