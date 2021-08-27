package com.example.appfood.category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appfood.FoodActivity;
import com.example.appfood.MainActivity;
import com.example.appfood.R;
import com.example.appfood.classs.RecyclerTouchListener;
import com.example.appfood.food.Food;
import com.example.appfood.subject.Subject;
import com.example.appfood.subject.SubjectAdapter;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{

    Context mContext;
    List<Category> categoryList;
    RecyclerView.Adapter adapter;
    ArrayList<Subject> subjectArrayList;
    Category category;
    Subject subject;
    Activity activity;

    public void setData(List<Category> list, Activity acti){
        this.categoryList=list;
        this.activity=acti;
    }

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cate_item,parent,false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        category =categoryList.get(position);
        holder.catename.setText(category.getName());
        holder.cateimage.setImageBitmap(Category.convertStringToBitmapFromAccess(holder.itemView.getContext(),category.getImage()));
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false);
        holder.recyclerView.setLayoutManager(linearLayoutManager);
        SubjectAdapter subjectAdapter = new SubjectAdapter();
        subjectAdapter.setData(category.getSubjectList(), activity);
        holder.recyclerView.setAdapter(subjectAdapter);
//        holder.recyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, holder.recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
////                Intent intent = new Intent(view.getContext(), FoodActivity.class);
////                intent.putExtra("subject_id", position+1);
////                view.getContext().startActivity(intent);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        TextView catename;
        ImageView cateimage;
        RecyclerView recyclerView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cateimage=itemView.findViewById(R.id.imagecategory);
            catename=itemView.findViewById(R.id.txtCategory);
            recyclerView=itemView.findViewById(R.id.featured_recycler);
        }
    }

}
