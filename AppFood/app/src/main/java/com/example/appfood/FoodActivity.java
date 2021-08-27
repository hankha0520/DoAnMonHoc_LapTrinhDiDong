package com.example.appfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.example.appfood.classs.Constant;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.food.Food;
import com.example.appfood.food.FoodAdapter;
import com.example.appfood.history.History;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<Food> arrayList;
    FoodAdapter foodAdapter;
    DBHelper dbHelper;
    ImageView imageView;
    ActionBar actionBar;


    Constant constant;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;
    int subid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        constant.color = appColor;

        if (themeColor == 0){
            setTheme(Constant.theme);
        }else if (appTheme == 0){
            setTheme(Constant.theme);
        }else{
            setTheme(appTheme);
        }
        setContentView(R.layout.activity_food);

        dbHelper = new DBHelper(FoodActivity.this);
        listView= findViewById(R.id.listViewFood);
        imageView = findViewById(R.id.imgSubjectFood);
        Intent intent = getIntent();
        subid = intent.getIntExtra("subject_id", 0);
        String img = intent.getStringExtra("subject_img");
        imageView.setImageBitmap(convertStringToBitmapFromAccess(FoodActivity.this, img));
        arrayList = dbHelper.loadFoodBySubID(subid);
        foodAdapter = new FoodAdapter(FoodActivity.this, arrayList);
        listView.setAdapter(foodAdapter);

        //ImageView image = findViewById(R.id.imgFood);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                History history = new History();
                history.setIdf(arrayList.get(position).getId());
                history.setName(arrayList.get(position).getName());
                history.setTime(arrayList.get(position).getTime());
                history.setMaterial(arrayList.get(position).getMaterial());
                history.setMaking(arrayList.get(position).getMaking());
                history.setDescription(arrayList.get(position).getDescription());
                history.setImage(arrayList.get(position).getImage());
                history.setFavourite(arrayList.get(position).getFavourite());
                if(!dbHelper.searchHistory(arrayList.get(position).getId())) {
                    dbHelper.insertHistory(history);
                }
                Intent intent1 = new Intent(getApplication(), DetailsActivity.class);
                intent1.putExtra("idFood", arrayList.get(position).getId());
                intent1.putExtra("nameFood", arrayList.get(position).getName());
                intent1.putExtra("timeFood", (int)(arrayList.get(position).getTime()));
                intent1.putExtra("materialFood", arrayList.get(position).getMaterial());
                intent1.putExtra("makingFood", arrayList.get(position).getMaking());
                intent1.putExtra("imgFood", arrayList.get(position).getImage());
                intent1.putExtra("desFood", arrayList.get(position).getDescription());
                intent1.putExtra("faFood", arrayList.get(position).getFavourite());
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(FoodActivity.this, view, "imgfood_transition").toBundle();
                startActivity(intent1, bundle);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList = dbHelper.loadFoodBySubID(subid);
        foodAdapter = new FoodAdapter(FoodActivity.this, arrayList);
        listView.setAdapter(foodAdapter);

    }

    public static Bitmap convertStringToBitmapFromAccess(Context context, String filename){
        AssetManager assetManager = context.getAssets();
        try {
            InputStream is = assetManager.open(filename);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}