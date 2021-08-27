package com.example.appfood.history;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.DetailsActivity;
import com.example.appfood.FoodActivity;
import com.example.appfood.R;
import com.example.appfood.food.Food;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

    List<History> mHistory;
    Context mContext;
    History history;


    public HistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<History> mHistory){
        this.mHistory = mHistory;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_item,parent,false);
        return new HistoryAdapter.HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        history = mHistory.get(position);
        holder.circleImageView.setImageBitmap(Food.convertStringToBitmapFromAccess(holder.itemView.getContext(), history.getImage()));
        holder.textView.setText(history.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(holder.itemView.getContext(), DetailsActivity.class);
//                intent1.putExtra("idHistory", history.getId());
//                intent1.putExtra("nameHistory", history.getName());
//                intent1.putExtra("timeHistory", (int)(history.getTime()));
//                intent1.putExtra("materialHistory", history.getMaterial());
//                intent1.putExtra("makingHistory", history.getMaking());
//                intent1.putExtra("imgHistory", history.getImage());
//                intent1.putExtra("desHistory", history.getDescription());
//                intent1.putExtra("faHistory", (int)history.getFavourite());
//                holder.itemView.getContext().startActivity(intent1);
                Intent intent1 = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent1.putExtra("idFood", history.getId());
                intent1.putExtra("nameFood", history.getName());
                intent1.putExtra("timeFood", (int)(history.getTime()));
                intent1.putExtra("materialFood", history.getMaterial());
                intent1.putExtra("makingFood", history.getMaking());
                intent1.putExtra("imgFood", history.getImage());
                intent1.putExtra("desFood", history.getDescription());
                intent1.putExtra("faFood", history.getFavourite());
                holder.itemView.getContext().startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CircleImageView circleImageView;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.txtnameHis);
            circleImageView = itemView.findViewById(R.id.imgHis);

        }
    }
}
