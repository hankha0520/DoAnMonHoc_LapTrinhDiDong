package com.example.appfood.food;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.DetailsActivity;
import com.example.appfood.FoodActivity;
import com.example.appfood.R;
import com.example.appfood.subject.Subject;
import com.example.appfood.subject.SubjectAdapter;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>{

    List<Food> mFood;
    Context mContext;
    Food food;

    public FavouriteAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Food> mFood){
        this.mFood = mFood;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favourite_item,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {
        food = mFood.get(position);
        holder.image.setImageBitmap(Food.convertStringToBitmapFromAccess(holder.itemView.getContext(), food.getImage()));
        holder.name.setText(food.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(holder.itemView.getContext(), DetailsActivity.class);
                intent1.putExtra("idFood", food.getId());
                intent1.putExtra("nameFood", food.getName());
                intent1.putExtra("timeFood", (int)(food.getTime()));
                intent1.putExtra("materialFood", food.getMaterial());
                intent1.putExtra("makingFood", food.getMaking());
                intent1.putExtra("imgFood", food.getImage());
                intent1.putExtra("desFood", food.getDescription());
                intent1.putExtra("faFood", (int)food.getFavourite());
                holder.itemView.getContext().startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFood.size();
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name;
        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgFavouriteFood);
            name = itemView.findViewById(R.id.FavouriteFood);
        }
    }
}
