package com.example.appfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appfood.classs.Constant;
import com.example.appfood.classs.DBHelper;
import com.example.appfood.history.History;
import com.example.appfood.history.HistoryAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView textView;
    CircleImageView circleImageView;
    DBHelper dbHelper;
    RecyclerView historyrecycler;
    HistoryAdapter historyAdapter;
    ArrayList<History> getHistory;
    Button btnDelete;
    ImageView emptyHistory;

    ActionBar actionBar;

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
        setContentView(R.layout.activity_history);

        btnDelete = findViewById(R.id.btn_Delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.delete();
                onResume();
                Toast.makeText(HistoryActivity.this, "Đã xóa lịch sử", Toast.LENGTH_SHORT).show();
            }
        });

        dbHelper = new DBHelper(this);
        historyrecycler = findViewById(R.id.recyclerViewHistory);
        historyAdapter = new HistoryAdapter(this);
        LinearLayoutManager horLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        historyrecycler.setLayoutManager(horLayoutManager);
        getHistory = new ArrayList<>();
        getHistory = dbHelper.loadHistory();
        emptyHistory = findViewById(R.id.empty_history_list);
        if(getHistory.size() > 0){
            emptyHistory.setVisibility(View.GONE);
        }
        else {
            emptyHistory.setVisibility(View.VISIBLE);
        }
        historyAdapter.setData(getHistory);
        historyrecycler.setAdapter(historyAdapter);

        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getString(R.string.history));

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
        if(dbHelper.loadHistory().size() > 0){
            emptyHistory.setVisibility(View.GONE);
        }
        else {
            emptyHistory.setVisibility(View.VISIBLE);
        }
        historyAdapter.setData(dbHelper.loadHistory());
        historyrecycler.setAdapter(historyAdapter);
    }
}