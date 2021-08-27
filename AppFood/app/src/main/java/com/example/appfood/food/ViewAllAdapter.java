package com.example.appfood.food;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.DetailsActivity;
import com.example.appfood.FoodActivity;
import com.example.appfood.R;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.history.History;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewAllViewHolder> {

    List<Food> foodList;
    DBHelper dbHelper;

    public ViewAllAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public ViewAllViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewall_item, parent, false);
        return new ViewAllViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAllViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.imageView.setImageBitmap(Food.convertStringToBitmapFromAccess(holder.itemView.getContext(), food.getImage()));
        holder.textView.setText(food.getName());

        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.scale_animation);
        holder.itemView.startAnimation(animation);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History history = new History();
                dbHelper = new DBHelper(holder.imageView.getContext());
                history.setIdf(food.getId());
                history.setName(food.getName());
                history.setTime(food.getTime());
                history.setMaterial(food.getMaterial());
                history.setMaking(food.getMaking());
                history.setDescription(food.getDescription());
                history.setImage(food.getImage());
                history.setFavourite(food.getFavourite());
                if(!dbHelper.searchHistory(food.getId())) {
                    dbHelper.insertHistory(history);
                }
                Intent intent = new Intent(holder.imageView.getContext(), DetailsActivity.class);
                intent.putExtra("idFood", food.getId());
                intent.putExtra("nameFood", food.getName());
                intent.putExtra("timeFood", (int)(food.getTime()));
                intent.putExtra("materialFood", food.getMaterial());
                intent.putExtra("makingFood", food.getMaking());
                intent.putExtra("imgFood", food.getImage());
                intent.putExtra("desFood", food.getDescription());
                intent.putExtra("faFood", food.getFavourite());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class ViewAllViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewAllViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_viewall);
            textView = itemView.findViewById(R.id.txt_FoodName);
        }
    }
}
