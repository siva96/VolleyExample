package com.example.volleyexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    Context context;
    List<User> userList = new ArrayList<>();
    ItemRowActionListener itemRowActionListener;

    public UserAdapter(Context context, List<User> usersList) {
        this.context = context;
        this.userList = usersList;
        itemRowActionListener = (ItemRowActionListener) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        User user = userList.get(position);
        holder.tv1.setText(user.getUserId() + "");
        holder.tv2.setText(user.getName());
        holder.tv3.setText(user.getEmail());
        holder.tv4.setText(user.getPassword());
        holder.tv5.setText(user.getPhoneNum());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemRowActionListener.onEditButtonClick(position);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemRowActionListener.onDeleteButtonClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList1) {
       /* List<User> updatedArrayList = new ArrayList<>();
        updatedArrayList.addAll(userList1);
        this.userList.clear();
        this.userList.addAll(updatedArrayList);*/
        this.userList = userList1;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv1, tv2, tv3, tv4, tv5;
        ImageView ivDelete, ivEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivEdit = itemView.findViewById(R.id.ivEdit);
        }
    }

    interface ItemRowActionListener {
        public void onEditButtonClick(int position);
        public void onDeleteButtonClick(int position);
    }
}
