package com.example.appfood;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.state.State;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.appfood.classs.Constant;
import com.example.appfood.classs.Method;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class SettingActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences, app_preferences;
    SharedPreferences.Editor editor;
    Button button;
    Method methods;
    ActionBar actionBar;
    int appTheme;
    int themeColor;
    int appColor;
    Constant constant;

    @Override
    protected void onCreate(Bundle savedInstanceState){
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
        setContentView(R.layout.activity_setting);


//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_setting);
//        toolbar.setTitle("Settings");
//        toolbar.setBackgroundColor(Constant.color);

        methods = new Method();
        button = (Button) findViewById(R.id.button_color);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();

        colorize();
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(this.getString(R.string.setting));


        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ColorChooserDialog dialog = new ColorChooserDialog(SettingActivity.this);
                dialog.setTitle("Select");
                dialog.setColorListener(new ColorListener() {
                    @Override
                    public void OnColorClick(View v, int color) {
                        colorize();
                        Constant.color = color;

                        methods.setColorTheme();
                        editor.putInt("color", color);
                        editor.putInt("theme",Constant.theme);
                        editor.commit();

                        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                dialog.show();
            }
        });
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

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void colorize(){
        ShapeDrawable d = new ShapeDrawable(new OvalShape());
        d.setBounds(58, 58, 58, 58);

        d.getPaint().setStyle(Paint.Style.FILL);
        d.getPaint().setColor(Constant.color);

        button.setBackground(d);
    }
}