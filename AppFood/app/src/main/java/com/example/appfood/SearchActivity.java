package com.example.appfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.appfood.classs.Constant;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.food.Food;
import com.example.appfood.food.FoodAdapter;
import com.example.appfood.history.History;

import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;
import pl.droidsonroids.gif.GifImageView;

public class SearchActivity extends AppCompatActivity {

    TagGroup mTagGroup;
    DBHelper dbHelper;
    SearchView searchView;
    FoodAdapter foodAdapter;
    ListView listView;
    ArrayList<Food> arrayList;
    TextView textView;
    ActionBar actionBar;
    GifImageView noresult;

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
        setContentView(R.layout.activity_search);
        dbHelper = new DBHelper(SearchActivity.this);
        textView = findViewById(R.id.txtkqTim);

        listView=findViewById(R.id.listViewSearch);
        arrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(SearchActivity.this, arrayList);
        listView.setAdapter(foodAdapter);

        searchView = findViewById(R.id.search_view);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
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
                startActivity(intent1);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                search(newText);
                return false;
            }
        });

        mTagGroup = findViewById(R.id.tag_group);
        mTagGroup.setTags(new String[]{"Lẩu", "Cháo","Sinh Tố","Xôi","Chè", "Cocktail", "Kem"});
        mTagGroup.setOnTagClickListener(new TagGroup.OnTagClickListener() {
            @Override
            public void onTagClick(String tag) {
                searchView.setQuery(tag,false);
                hideSoftKeyboard(searchView);
            }
        });
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getString(R.string.search));
        noresult = findViewById(R.id.gif_noresult);

    }

    private void search(String newText) {
        ArrayList<Food> tmp = new ArrayList<>();
        tmp = dbHelper.searchFood(newText);
        if(tmp.size() > 0){
            foodAdapter.clear();
            foodAdapter.addAll(tmp);
            foodAdapter.notifyDataSetChanged();
            noresult.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(tmp.size()));
        }

        if(newText.isEmpty()){
            noresult.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            textView.setText(String.valueOf(0));
        }
    }

    public void hideSoftKeyboard(View view){
        InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    @Override
    protected void onResume() {
        super.onResume();
    }
}