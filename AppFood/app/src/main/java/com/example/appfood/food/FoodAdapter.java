package com.example.appfood.food;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.R;

import java.util.ArrayList;

public class FoodAdapter extends ArrayAdapter<Food> {

    public  FoodAdapter(Context context, ArrayList<Food> arrayList){
        super(context,0,arrayList);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_food_item, parent, false);
        Food food = getItem(position);

        TextView textView = convertView.findViewById(R.id.txtFood);
        TextView textView1 = convertView.findViewById(R.id.txttime);
        ImageView image = convertView.findViewById(R.id.imgFood);

        textView.setText(food.getName());
        textView1.setText(String.valueOf(food.getTime()));
        image.setImageBitmap(Food.convertStringToBitmapFromAccess(getContext(), food.getImage()));

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.scale_animation);
        convertView.startAnimation(animation);
        return convertView;
    }


}
