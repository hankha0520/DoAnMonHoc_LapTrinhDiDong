package com.example.appfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.category.CategoryAdapter;
import com.example.appfood.classs.Constant;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.classs.Photo;
import com.example.appfood.food.FavouriteAdapter;
import com.example.appfood.food.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    RecyclerView categoyrecycler, favouriterecycler;
    CategoryAdapter categoryAdapter;
    FavouriteAdapter favouriteAdapter;
    DBHelper dbHelper;
    Button searchView;
    TextView viewall;
    ArrayList<Food> getFavourite;
    GifImageView imageEmpty;
    Dialog dialog;
    Button btnOK;

    ////Back
    long backTime;
    Toast toast;

    ////Slideshow
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    PhotoAdapter photoAdapter;
    List<Photo> mlistPhoto;
    Timer timer;

    ////Theme
    Constant constant;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

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
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        dbHelper.openDatabase();
        categoyrecycler = findViewById(R.id.recyclerViewMain);
        categoryAdapter = new CategoryAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        categoyrecycler.setLayoutManager(linearLayoutManager);
        categoryAdapter.setData(dbHelper.loadCategory(), MainActivity.this);
        categoyrecycler.setAdapter(categoryAdapter);

        //Favourite
        favouriterecycler = findViewById(R.id.recyclerViewFavourite);
        favouriteAdapter = new FavouriteAdapter(this);
        LinearLayoutManager horLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        favouriterecycler.setLayoutManager(horLayoutManager);
        getFavourite = new ArrayList<>();
        getFavourite = dbHelper.loadFavorite(1);//1 là yêu thích
        imageEmpty = findViewById(R.id.empty_list);
        if (getFavourite.size() > 0){
            imageEmpty.setVisibility(View.GONE);
        }
        else {
            imageEmpty.setVisibility(View.VISIBLE);
        }
        favouriteAdapter.setData(getFavourite);
        favouriterecycler.setAdapter(favouriteAdapter);

        //Search
        searchView = findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //Slide show
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circleindicator);
        mlistPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this, mlistPhoto);
        viewPager.setAdapter(photoAdapter);
        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        autoSlideImage();

        viewall = findViewById(R.id.btn_allfood);
        viewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
                startActivity(intent);
            }
        });


        dialog = new Dialog(this);
    }

    @Override
    public void onBackPressed() {
        if(backTime + 2000 > System.currentTimeMillis()){
            toast.cancel();
            super.onBackPressed();
            return;
        }
        else{
            toast = Toast.makeText(MainActivity.this, "Nhấn back lần nữa để thoát ứng dụng", Toast.LENGTH_SHORT);
            toast.show();
        }
        backTime = System.currentTimeMillis();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                Intent intent1 = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.setting:
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.information:
                openDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {
        dialog.setContentView(R.layout.layout_information_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        btnOK = dialog.findViewById(R.id.btn_OK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        categoyrecycler.setAdapter(categoryAdapter);
        if (dbHelper.loadFavorite(1).size() > 0){
            imageEmpty.setVisibility(View.GONE);
        }
        else {
            imageEmpty.setVisibility(View.VISIBLE);
        }
        favouriteAdapter.setData(dbHelper.loadFavorite(1));
        favouriterecycler.setAdapter(favouriteAdapter);
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.s1));
        list.add(new Photo(R.drawable.s2));
        list.add(new Photo(R.drawable.s3));
        list.add(new Photo(R.drawable.s4));
        list.add(new Photo(R.drawable.s5));
        return  list;
    }
    private void autoSlideImage()
    {
        if(mlistPhoto == null || mlistPhoto.isEmpty() || viewPager == null)
        {
            return;
        }
        if(timer == null)
        {
            timer = new Timer();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = viewPager.getCurrentItem();
                        int totalItem = mlistPhoto.size() - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null)
        {
            timer.cancel();
            timer = null;
        }
    }



}