package com.example.appfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appfood.classs.Constant;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.food.Food;
import com.example.appfood.food.ViewAllAdapter;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class ViewAllActivity extends AppCompatActivity {

    TagGroup mTagGroup;
    DBHelper dbHelper;
    RecyclerView recyclerView;
    ViewAllAdapter viewAllAdapter;
    List<String> listname;
    LinearLayoutManager linearLayoutManager;
    ////Theme
    Constant constant;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    ActionBar actionBar;

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
        setContentView(R.layout.activity_view_all);

        dbHelper = new DBHelper(this);
        recyclerView = findViewById(R.id.recycler_viewall);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        viewAllAdapter = new ViewAllAdapter(getListFood());
        recyclerView.setAdapter(viewAllAdapter);
        listname = dbHelper.loadSubjectName();
        mTagGroup = findViewById(R.id.tag_group);
        mTagGroup.setTags(listname);
        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                switch (tag){
                    case "Lẩu":
                        scrollItem(0);
                        break;
                    case "Loại sợi":
                        scrollItem(9);
                        break;
                    case "Cháo":
                        scrollItem(18);
                        break;
                    case "Xào":
                        scrollItem(25);
                        break;
                    case "Chiên":
                        scrollItem(30);
                        break;
                    case "Kho":
                        scrollItem(35);
                        break;
                    case "Canh":
                        scrollItem(40);
                        break;
                    case "Kho chay":
                        scrollItem(44);
                        break;
                    case "Xào chay":
                        scrollItem(48);
                        break;
                    case "Sinh Tố":
                        scrollItem(52);
                        break;
                    case "Trà Sữa":
                        scrollItem(56);
                        break;
                    case "Nước Ép":
                        scrollItem(60);
                        break;
                    case "Bánh Ngọt":
                        scrollItem(64);
                        break;
                    case "Bánh Mặn":
                        scrollItem(67);
                        break;
                    case "Truyền Thống":
                        scrollItem(70);
                        break;
                    case "Xôi":
                        scrollItem(73);
                        break;
                    case "Chè":
                        scrollItem(76);
                        break;
                    case "Cocktail":
                        scrollItem(79);
                        break;
                    case "Kem":
                        scrollItem(82);
                        break;

                }
            }
        });

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getString(R.string.viewall));
    }
    private void scrollItem(int index){
        if (linearLayoutManager == null){
            return;
        }
        linearLayoutManager.scrollToPositionWithOffset(index, 0);
    }

    private List<Food> getListFood() {
        List<Food> foodList = new ArrayList<>();
        for (int i = 1; i < 20; i++){
            foodList.addAll(dbHelper.loadFoodBySubID(i));
        }
        return foodList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}