package com.example.appfood.subject;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.FoodActivity;
import com.example.appfood.MainActivity;
import com.example.appfood.R;
import com.example.appfood.food.Food;

import java.util.List;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.SubjectViewHolder> {
    List<Subject> mSubject;
    Activity activity;

    public void setData(List<Subject> mSubject, Activity activity1){
        this.mSubject = mSubject;
        this.activity = activity1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_card,parent,false);
        SubjectViewHolder subjectViewHolder= new SubjectViewHolder(view);

        return subjectViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        Subject subject = mSubject.get(position);
        holder.image.setImageBitmap(Subject.convertStringToBitmapFromAccess(holder.itemView.getContext(),subject.getImage()));
        holder.name.setText(subject.getName());
        holder.description.setText(subject.getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), FoodActivity.class);
                intent.putExtra("subject_id", subject.getId());
                intent.putExtra("subject_img", subject.getImage());
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(activity, v, "imgSubject_transition").toBundle();
                holder.itemView.getContext().startActivity(intent, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSubject.size();
    }


    public static class SubjectViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView name, description;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgSubject);
            name = itemView.findViewById(R.id.txtSubjectName);
            description = itemView.findViewById(R.id.txtSubjectDescription);
        }
    }
}
