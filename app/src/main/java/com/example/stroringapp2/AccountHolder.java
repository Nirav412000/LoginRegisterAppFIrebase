package com.example.stroringapp2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AccountHolder extends RecyclerView.Adapter<AccountHolder.AccountViewHolder> {
    private String[][] data;
    public AccountHolder(String[][] data){
        this.data = data;
    }
    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_layout, parent,false);

        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        String s1 = data[position][0];
        String s2 = data[position][1];
        String s3 = data[position][2];
        holder.acc1.setText(s1);
        holder.acc2.setText(s2);
        holder.acc3.setText(s3);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class AccountViewHolder extends RecyclerView.ViewHolder{
        TextView acc1,acc2,acc3;
        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            acc1 = itemView.findViewById(R.id.acc1);
            acc2 = itemView.findViewById(R.id.acc2);
            acc3 = itemView.findViewById(R.id.acc3);
        }
    }
}
