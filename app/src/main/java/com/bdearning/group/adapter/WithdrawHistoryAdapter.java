package com.bdearning.group.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bdearning.group.model.WithdrawHistoryModel;

import java.util.ArrayList;

import bdearning.kormoappp.R;


public class WithdrawHistoryAdapter extends RecyclerView.Adapter<WithdrawHistoryAdapter.PostsHolder> {

    private Context context;
    private ArrayList<WithdrawHistoryModel> list;


    public WithdrawHistoryAdapter(Context context, ArrayList<WithdrawHistoryModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public PostsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.widthdraw_history,parent,false);
        return new PostsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsHolder holder, int position) {



            WithdrawHistoryModel model = list.get(position);
            holder.amount.setText(model.getAmount());
            holder.methodName.setText(model.getMethodName());


            if (model.getStatus().equals("1"))
            {
                holder.status.setText("Pending");
            }
            else
            {
                holder.status.setText("Paid");
            }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class PostsHolder extends RecyclerView.ViewHolder{

        private TextView amount,methodName,status;


        public PostsHolder(@NonNull View itemView) {
            super(itemView);

            amount = itemView.findViewById(R.id.et_amount);
            methodName = itemView.findViewById(R.id.et_methodName);
            status = itemView.findViewById(R.id.et_status);

        }
    }


}
